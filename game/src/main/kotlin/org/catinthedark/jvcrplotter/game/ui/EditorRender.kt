package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.NinePatch
import org.catinthedark.jvcrplotter.game.Const
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.lib.managed
import kotlin.math.max
import kotlin.math.min

class EditorRender(
    private val batch: Batch,
    private val font: BitmapFont,
    private val frameNinePatch: NinePatch,
    private val errorNinePatch: NinePatch
) {
    private var offset: Int = 0
    private val errorX = 100f
    private val errorY = 120f
    private val layout = GlyphLayout(font, "    ")
    private val initX = 100f
    private val initY = Const.Screen.HEIGHT.toFloat() - 80f
    private val lineHeight = 35f
    private val symbolWidth = 70f

    private val frameOffsetX = 8f
    private val frameOffsetY = 6f

    fun render(
        editor: Editor,
        error: Boolean,
        errorMessage: String?,
        highlightLine: Boolean
    ) {
        batch.managed { b ->
            var yPos = initY

            val pos = editor.getCursorPosition()
            offset = max(0, pos.second - Const.UI.maxLinesOnScreen)
            val lastItem = min(editor.getRows().size, Const.UI.maxLinesOnScreen + offset + 1)

            editor.getRows().subList(offset, lastItem).forEach { row ->
                var xPos = initX
                row.forEach { symbol ->
                    font.draw(b, symbol, xPos, yPos)
                    xPos += symbolWidth
                }
                yPos -= lineHeight
            }

            val frame = if (error) {
                errorNinePatch
            } else {
                frameNinePatch
            }

            val frameWidth = if (highlightLine) {
                symbolWidth * 3 + frameOffsetX * 2
            } else {
                layout.width
            }
            frame.draw(
                b,
                initX + symbolWidth * pos.first - frameOffsetX,
                initY - lineHeight * (pos.second + 1 - offset) + frameOffsetY,
                frameWidth,
                layout.height + frameOffsetY * 2
            )

            if (error) {
                font.draw(b, errorMessage, errorX, errorY)
            }
        }
    }
}
