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
    private val textureName: String,
    private val label: String,
    private val onClick: () -> Unit = {},
    private val onHover: () -> Unit = {}
) {
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val texture: Texture by lazy { am.at<Texture>(textureName) }
    private val button: Button by lazy { Button(x, y, x + texture.width, y + texture.height, onClick, onHover) }
    private val fontName = Assets.Names.FONT_MEDIUM_MONOSPACE_BLACK
    private val textLayout: GlyphLayout by lazy { GlyphLayout(am.at<BitmapFont>(fontName), label) }

    fun update() {
        button.update()
    }

    fun draw(batch: Batch) {
        val textX = x + (texture.width - textLayout.width) / 2
        val textY = y + texture.height - (texture.height - textLayout.height) / 2
        batch.managed {
            it.draw(texture, x.toFloat(), y.toFloat())
            am.at<BitmapFont>(fontName).draw(it, label, textX, textY)
        }
    }
}
