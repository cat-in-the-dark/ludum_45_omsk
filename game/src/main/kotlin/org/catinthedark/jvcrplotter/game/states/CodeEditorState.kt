package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.*
import org.catinthedark.jvcrplotter.game.Assets.Names.FONT_BIG_GREEN
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.game.ui.Button
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
    private val cursorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.CURSOR_FRAME), 6, 6, 6, 6) }

    private val compileButton = Button(1190, 585, 1260, 660, {
        IOC.put("instructions", editor.toInstructions())
        IOC.put("state", States.PLOTTING_SCREEN)
    })

    private val buttons = listOf(
        CompositeButton(640, 220, Assets.Names.BUTTON, "MUL", {
            editor.setSymbolUnderCursor("MUL")
            editor.moveCursorRight()
        }),
        CompositeButton(730, 220, Assets.Names.BUTTON, "ADD", {
            editor.setSymbolUnderCursor("ADD")
            editor.moveCursorRight()
        }),
        CompositeButton(820, 220, Assets.Names.BUTTON, "CMP", {
            editor.setSymbolUnderCursor("CMP")
            editor.moveCursorRight()
        }),
        CompositeButton(910, 220, Assets.Names.BUTTON, "INT", {
            editor.setSymbolUnderCursor("INT")
            editor.moveCursorRight()
        }),
        CompositeButton(1000, 220, Assets.Names.BUTTON, "JE", {
            editor.setSymbolUnderCursor("JE")
            editor.moveCursorRight()
        }),
        CompositeButton(640, 160, Assets.Names.BUTTON, "JG", {
            editor.setSymbolUnderCursor("JG")
            editor.moveCursorRight()
        }),
        CompositeButton(730, 160, Assets.Names.BUTTON, "X", {
            editor.setSymbolUnderCursor("X")
            editor.moveCursorRight()
        }),
        CompositeButton(820, 160, Assets.Names.BUTTON, "Y", {
            editor.setSymbolUnderCursor("Y")
            editor.moveCursorRight()
        }),
        CompositeButton(910, 160, Assets.Names.BUTTON, "MOV", {
            editor.setSymbolUnderCursor("MOV")
            editor.moveCursorRight()
        })
    )

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

        compileButton.update()

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
            val layout = GlyphLayout(am.at<BitmapFont>(FONT_BIG_GREEN), "    ")
            val initX = 100f
            val initY = Const.Screen.HEIGHT.toFloat() - 80f
            val lineHeight = 35f
            val symbolWidth = 70f

            val frameOffsetX = 8f
            val frameOffsetY = 6f
            var yPos = initY
            editor.getRows().forEach { row ->
                var xPos = initX
                row.forEach { symbol ->
                    am.font(FONT_BIG_GREEN).draw(b, symbol, xPos, yPos)
                    xPos += symbolWidth
                }
                yPos -= lineHeight
            }
            val pos = editor.getCursorPosition()

            cursorFrame.draw(
                b,
                initX + symbolWidth * pos.first - frameOffsetX,
                initY - lineHeight * (pos.second + 1) + frameOffsetY,
                layout.width,
                layout.height + frameOffsetY * 2
            )
        }
    }

    override fun onExit() {
        Gdx.input.inputProcessor = inputProcessor
    }
}
