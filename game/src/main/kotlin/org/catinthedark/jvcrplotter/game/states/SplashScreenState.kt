package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class SplashScreenState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { Assets.load() }
    private var time = 0f

    override fun onActivate() {
        time = 0f
    }

    override fun onUpdate() {
        time += Gdx.graphics.deltaTime
        if (am.update()) {
            IOC.put("state", States.TITLE_SCREEN)
            IOC.put("assetManager", am)
            logger.info("Assets loaded in $time")
        } else {
            logger.info("Loading assets...${am.progress}")
        }

        if (am.isLoaded(Assets.Names.LOGO, Texture::class.java)) {
            hud.batch.managed {
                it.draw(am.get(Assets.Names.LOGO, Texture::class.java), 0f, 0f)
            }
        }
    }

    override fun onExit() {
    }
}
