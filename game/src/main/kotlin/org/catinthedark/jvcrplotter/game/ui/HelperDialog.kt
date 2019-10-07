package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.math.Rectangle
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.Const
import org.catinthedark.jvcrplotter.game.font
import org.catinthedark.jvcrplotter.game.texture
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed


object UI {
    const val buttonOffsetY = 15
    const val buttonWidth = 90
    const val buttonHeight = 40
    const val textOffsetX = 20f
    const val textOffsetY = 20f
    val patchRect = Rectangle(20f, 20f, 20f, 20f)
    val frameTextureName = Assets.Names.HELP_FRAME
    val fontName = Assets.Names.FONT_BIG
}

class HelperDialog(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    private val text: String,
    private val closesAt: String? = null,
    private val initiallyClosed: Boolean = false
) : IDialog() {
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private var shouldClose: Boolean = false
    var isClosed = initiallyClosed
        private set
    private val framePatch: NinePatch by lazy {
        NinePatch(
            am.texture(UI.frameTextureName),
            UI.patchRect.x.toInt(),
            UI.patchRect.width.toInt(),
            UI.patchRect.y.toInt(),
            UI.patchRect.height.toInt()
        )
    }
    private val okButton = CompositeNinePatchButton(
        x + (width - UI.buttonWidth) / 2,
        y + UI.buttonOffsetY,
        UI.buttonWidth,
        UI.buttonHeight,
        Assets.Names.BUTTON_RED,
        Const.UI.ninePatchButtonRect,
        "ДА",
        {
            shouldClose = true
        }
    )

    fun reset() {
        shouldClose = false
        isClosed = false
    }

    override fun draw(batch: Batch) {
        if (isClosed) return
        batch.managed {
            framePatch.draw(it, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())

            val font = am.font(UI.fontName)
            font.data.markupEnabled = true
            font.draw(it, text, x + UI.textOffsetX, y + height - UI.textOffsetY)
        }
        okButton.draw(batch)
    }

    override fun update() {
        if (isClosed) {
            return
        }
        closesAt?.let {
            val state: String by IOC
            if (state == it) {
                shouldClose = true
            }
        }

        // set osClosed to true on the second update, so dialog chain checking each other's isClosed state won't close
        // all at once
        if (shouldClose) {
            isClosed = true
        }

        okButton.update()
    }
}
