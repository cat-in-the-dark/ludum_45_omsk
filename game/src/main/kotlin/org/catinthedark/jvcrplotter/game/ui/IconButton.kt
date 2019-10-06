package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import org.catinthedark.jvcrplotter.game.at
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed

class IconButton(
    val x: Int,
    val y: Int,
    private val textureName: String,
    private val iconTextureName: String,
    private val onClick: () -> Unit = {},
    private val onHover: () -> Unit = {}
) : IButton {
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val texture: Texture by lazy { am.at<Texture>(textureName) }
    private val iconTexture: Texture by lazy { am.at<Texture>(iconTextureName) }
    private val button: Button by lazy {
        Button(
            x,
            y,
            x + texture.width,
            y + texture.height,
            onClick,
            onHover
        )
    }

    override fun draw(batch: Batch) {
        batch.managed {
            it.draw(texture, x.toFloat(), y.toFloat())
            it.draw(iconTexture, x.toFloat(), y.toFloat())
        }
    }

    override fun update() {
        button.update()
    }
}
