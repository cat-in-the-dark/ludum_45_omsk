package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets.Names.FONT_BIG
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.game.font
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class CodeEditorState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val editor: Editor by lazy { IOC.atOrFail<Editor>("editor") }
    private val am: AssetManager by lazy {IOC.atOrFail<AssetManager>("assetManager")}

    override fun onActivate() {
        logger.info("game state activated")
        Gdx.input.inputProcessor = EditorInputAdapter(editor)
    }

    class EditorInputAdapter(private val editor: Editor) : InputAdapter() {
        override fun keyTyped(character: Char): Boolean {
            if (character in '0'..'9') {
                editor.setSymbolUnderCursor(editor.getSymbolUnderCursor() + character)
            }
            return true
        }
    }

    override fun onUpdate() {
        hud.batch.managed {
            am.font(FONT_BIG).draw(it, "lol", 0f, 0f)
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

        val pos = editor.getCursorPosition()
        if (pos.first == 0) {

        }

        drawEditor(editor)
    }

    private fun drawEditor(editor: Editor) {
        hud.batch.managed {b ->
            editor.getRows().forEach {
                am.font(FONT_BIG).draw(b, it.joinToString(" "), 0f, 0f)
            }
        }
    }

    override fun onExit() {
    }
}
