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
import org.catinthedark.jvcrplotter.game.ui.*
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
    private lateinit var editor: Editor
    private var showHelp: Boolean = false
    private val cursorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.CURSOR_FRAME), 9, 9, 9, 9) }
    private val errorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.ERROR_FRAME), 9, 9, 9, 9) }
    private val buttonPanel = ButtonPanel { instruction: String, update: Boolean ->
        when {
            update -> editor.appendNumberUnderCursor(instruction[0])
            instruction == "\n" -> {
                editor.setSymbolUnderCursor("")
                val pos = editor.getCursorPosition()
                if (pos.second == editor.getRows().size - 1) {
                    editor.insertNewLine()
                }

                editor.setCursorPosition(0, pos.second + 1)
            }
            else -> {
                editor.setSymbolUnderCursor(instruction)
                editor.moveCursorRight()
            }
        }
    }
    private val helpButton = CompositeButton(665, 80, Assets.Names.BUTTON, "?", {
        showHelp = !showHelp
    })
    private val backBtn = Button(1185, 580, 1255, 655, onClick = {
        val prevState = IOC.get("previousState")
        if (prevState != null) {
            IOC.put("state", prevState)
        }
    })

    private val editorRender: EditorRender by lazy {
        EditorRender(
            hud.batch, am.font(FONT_BIG_GREEN),
            cursorFrame,
            errorFrame
        )
    }

    private var compileError = false
    private var errorMessage: String? = ""

    private val compileButton = CompositeNinePatchButton(
        910, 60, 220, 50, Assets.Names.BUTTON_RED,
        Const.UI.ninePatchButtonRect,
        "ПУСК", {
            try {
                IOC.put("instructions", editor.toInstructions())
                IOC.put("state", States.PLOTTING_SCREEN)
            } catch (e: Exception) {
                compileError = true
                errorMessage = e.message
            }
        }
    )

    override fun onActivate() {
        editor = IOC.atOrFail("editor")
        IOC.put("previousState", States.WORKSPACE_SCREEN)
        logger.info("game state activated")
        Gdx.input.inputProcessor = FusedInputAdapter(inputProcessor) {
            compileError = false
            editor.onKeyTyped(it)
        }
    }

    class FusedInputAdapter(
        private val inputProcessor: InputProcessor,
        private val onKeyTyped: (character: Char) -> Unit = {}
    ) : InputAdapter() {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            return inputProcessor.touchDown(screenX, screenY, pointer, button)
        }

        override fun keyTyped(character: Char): Boolean {
            onKeyTyped(character)
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

        backBtn.update()
        compileButton.update()
        compileButton.draw(hud.batch)
        helpButton.update()
        helpButton.draw(hud.batch)

        when {
            Gdx.input.isKeyJustPressed(Input.Keys.UP) -> editor.moveCursorUp()
            Gdx.input.isKeyJustPressed(Input.Keys.DOWN) -> editor.moveCursorDown()
            Gdx.input.isKeyJustPressed(Input.Keys.LEFT) -> editor.moveCursorLeft()
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) -> editor.moveCursorRight()
            Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) -> editor.clearSymbolUnderCursor()
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER) -> editor.insertNewLine()
            Gdx.input.isKeyJustPressed(Input.Keys.FORWARD_DEL) -> editor.deleteLine()
        }

        if (showHelp) {
            renderHelp()
        } else {
            renderEditorText(editor)
        }
    }

    private fun renderHelp() {
        val initX = 100f
        val initY = Const.Screen.HEIGHT.toFloat() - 75f
        hud.batch.managed {
            am.font(FONT_BIG_GREEN).draw(it, quickHelpText, initX, initY)
        }
    }

    private fun renderEditorText(editor: Editor) {
        editorRender.render(
            editor,
            compileError,
            errorMessage,
            false
        )
    }

    override fun onExit() {
        Gdx.input.inputProcessor = inputProcessor
    }
}
