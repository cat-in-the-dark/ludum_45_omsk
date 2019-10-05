package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.Assets.Names.FONT_BIG
import org.catinthedark.jvcrplotter.game.Const
import org.catinthedark.jvcrplotter.game.at
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.game.font
import org.catinthedark.jvcrplotter.game.ui.CompositeButton
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class CodeEditorState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val editor: Editor by lazy { IOC.atOrFail<Editor>("editor") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val inputProcessor = Gdx.input.inputProcessor

    private val mulButton = CompositeButton(640, 180, Assets.Names.BUTTON, "MUL", {
        editor.setSymbolUnderCursor("MUL")
        editor.moveCursorRight()
    })

    private val addButton = CompositeButton(730, 180, Assets.Names.BUTTON, "ADD", {
        editor.setSymbolUnderCursor("ADD")
        editor.moveCursorRight()
    })

    private val buttons = listOf(mulButton, addButton)

    override fun onActivate() {
        logger.info("game state activated")
        Gdx.input.inputProcessor = EditorInputAdapter(editor, inputProcessor)
    }

    class EditorInputAdapter(
        private val editor: Editor,
        private val inputProcessor: InputProcessor
    ) : InputAdapter() {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            return inputProcessor.touchDown(screenX, screenY, pointer, button)
        }

        override fun keyTyped(character: Char): Boolean {
            if (character in '0'..'9') {
                editor.setSymbolUnderCursor(editor.getSymbolUnderCursor() + character)
            }
            return inputProcessor.keyTyped(character)
        }
    }

    override fun onUpdate() {
        hud.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.MONIK), 0f, 0f)
        }

        buttons.forEach {
            it.update()
            it.draw(hud.batch)
        }

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
        hud.batch.managed { b ->
            val initX = 80f
            val initY = Const.Screen.HEIGHT.toFloat() - 80f
            var yPos = initY
            val lineHeight = 30f
            editor.getRows().forEach {
                am.font(FONT_BIG).draw(b, it.joinToString(" "), initX, yPos)
                yPos -= lineHeight
            }
        }
    }

    override fun onExit() {
        Gdx.input.inputProcessor = inputProcessor
    }
}
