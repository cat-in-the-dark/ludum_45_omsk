package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.NinePatch
import org.catinthedark.jvcrplotter.game.Const
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.lib.managed

class EditorRender(
    private val batch: Batch,
    private val font: BitmapFont,
    private val frameNinePatch: NinePatch,
    private val errorNinePatch: NinePatch
) {
    fun render(
        editor: Editor,
        error: Boolean,
        errorMessage: String?,
        highliteLine: Boolean
    ) {
        batch.managed { b ->
            val errorX = 100f
            val errorY = 120f
            val layout = GlyphLayout(font, "    ")
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
                    font.draw(b, symbol, xPos, yPos)
                    xPos += symbolWidth
                }
                yPos -= lineHeight
            }
            val pos = editor.getCursorPosition()

            val frame = if (error) {
                errorNinePatch
            } else {
                frameNinePatch
            }

            val frameWidth = if (highliteLine) {
                symbolWidth * 3 + frameOffsetX * 2
            } else {
                layout.width
            }
            frame.draw(
                b,
                initX + symbolWidth * pos.first - frameOffsetX,
                initY - lineHeight * (pos.second + 1) + frameOffsetY,
                frameWidth,
                layout.height + frameOffsetY * 2
            )

            if (error) {
                font.draw(b, errorMessage, errorX, errorY)
            }
        }
    }
}
