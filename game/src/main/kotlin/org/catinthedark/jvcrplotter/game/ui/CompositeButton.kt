package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.at
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed

class CompositeButton(
    val x: Int,
    val y: Int,
    private val activeTextureName: String,
    private val inactiveTextureName: String,
    private val label: String,
    private val onClick: () -> Unit = {},
    private val onHover: () -> Unit = {}
) {
    private val inactiveTexture: Texture by lazy { am.at<Texture>(inactiveTextureName) }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val activeTexture: Texture by lazy { am.at<Texture>(activeTextureName) }
    private val button: Button by lazy {
        Button(
            x,
            y,
            x + activeTexture.width,
            y + activeTexture.height,
            onClick,
            onHover
        )
    }
    private val fontName = Assets.Names.FONT_MEDIUM_MONOSPACE_BLACK
    private val textLayout: GlyphLayout by lazy { GlyphLayout(am.at<BitmapFont>(fontName), label) }

    fun update() {
        button.update()
    }

    fun drawInactive(batch: Batch) {
        batch.managed {
            it.draw(inactiveTexture, x.toFloat(), y.toFloat())
            drawText(it)
        }
    }

    fun drawActive(batch: Batch) {
        batch.managed {
            it.draw(activeTexture, x.toFloat(), y.toFloat())
            drawText(it)
        }
    }

    fun drawText(batch: Batch) {
        val textX = x + (activeTexture.width - textLayout.width) / 2
        val textY = y + activeTexture.height - (activeTexture.height - textLayout.height) / 2
        am.at<BitmapFont>(fontName).draw(batch, label, textX, textY)
    }
}
