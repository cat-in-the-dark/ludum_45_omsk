package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.*
import org.catinthedark.jvcrplotter.game.Assets.Names.FONT_BIG_GREEN
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.game.ui.Button
import org.catinthedark.jvcrplotter.game.ui.ButtonPanel
import org.catinthedark.jvcrplotter.game.ui.EditorRender
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class CodeEditorState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val inputProcessor = Gdx.input.inputProcessor
    private val editor: Editor by lazy { IOC.atOrFail<Editor>("editor") }
    private val cursorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.CURSOR_FRAME), 6, 6, 6, 6) }
    private val errorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.ERROR_FRAME), 6, 6, 6, 6) }
    private val buttonPanel = ButtonPanel {
        editor.setSymbolUnderCursor(it)
        editor.moveCursorRight()
    }

    private var compileError = false
    private var errorMessage: String? = ""

    private val compileButton = Button(1190, 585, 1260, 660, {
        try {
            IOC.put("instructions", editor.toInstructions())
            IOC.put("state", States.PLOTTING_SCREEN)
        } catch (e: Exception) {
            compileError = true
            errorMessage = e.message
        }
    })

    override fun onActivate() {
        logger.info("game state activated")
        Gdx.input.inputProcessor = EditorInputAdapter(editor, inputProcessor) {
            compileError = false
        }
    }

    class EditorInputAdapter(
        private val editor: Editor,
        private val inputProcessor: InputProcessor,
        private val onKeyTyped: (character: Char) -> Unit = {}
    ) : InputAdapter() {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            return inputProcessor.touchDown(screenX, screenY, pointer, button)
        }

        override fun keyTyped(character: Char): Boolean {
            onKeyTyped(character)
            if (character in '0'..'9' || (character == '-' && editor.getSymbolUnderCursor().isEmpty())) {
                editor.setSymbolUnderCursor(editor.getSymbolUnderCursor() + character)
            }
            return inputProcessor.keyTyped(character)
        }
    }

    override fun onUpdate() {
        hud.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.MONIK), 0f, 0f)
        }

        val editorXPos = editor.getCursorPosition().first
        buttonPanel.updateButtons(editorXPos)
        buttonPanel.drawButtons(hud.batch, editorXPos)

        compileButton.update()

        when {
            Gdx.input.isKeyJustPressed(Input.Keys.UP) -> editor.moveCursorUp()
            Gdx.input.isKeyJustPressed(Input.Keys.DOWN) -> editor.moveCursorDown()
            Gdx.input.isKeyJustPressed(Input.Keys.LEFT) -> editor.moveCursorLeft()
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) -> editor.moveCursorRight()
            Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) -> editor.clearSymbolUnderCursor()
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER) -> editor.insertNewLine()
            Gdx.input.isKeyJustPressed(Input.Keys.FORWARD_DEL) -> editor.deleteLine()
        }

        renderEditorText(editor)
    }

    private fun renderEditorText(editor: Editor) {
        EditorRender().render(
            editor,
            hud.batch,
            am.font(FONT_BIG_GREEN),
            cursorFrame,
            errorFrame,
            compileError,
            errorMessage
        )
    }

    override fun onExit() {
        Gdx.input.inputProcessor = inputProcessor
    }
}
