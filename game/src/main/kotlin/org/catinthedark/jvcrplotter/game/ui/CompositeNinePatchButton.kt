package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.math.Rectangle
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.at
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed

class CompositeNinePatchButton(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    private val textureName: String,
    private val patchRect: Rectangle,
    private val label: String,
    private val onClick: () -> Unit = {},
    private val onHover: () -> Unit = {}
) : IButton {
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val texture: Texture by lazy { am.at<Texture>(textureName) }
    private val ninePatch: NinePatch by lazy {
        NinePatch(
            texture,
            patchRect.x.toInt(),
            patchRect.width.toInt(),
            patchRect.y.toInt(),
            patchRect.height.toInt()
        )
    }

    private val fontName = Assets.Names.FONT_BIG
    private val textLayout: GlyphLayout by lazy { GlyphLayout(am.at<BitmapFont>(fontName), label) }

    private val button: Button by lazy {
        Button(
            x,
            y,
            x + width,
            y + height,
            onClick,
            onHover
        )
    }

    override fun draw(batch: Batch) {
        batch.managed {
            val textX = x + (width - textLayout.width) / 2f
            val textY = y + height - (height - textLayout.height) / 2f
            ninePatch.draw(it, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
            am.at<BitmapFont>(fontName).draw(it, label, textX, textY)
        }
    }

    override fun update() {
        button.update()
    }
}
