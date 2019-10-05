package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.texture
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class TitleScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    override fun onActivate() {
    }

    override fun onUpdate() {
        hud.batch.managed { b ->
            b.draw(am.texture(Assets.Names.TITLE), 0f, 0f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            IOC.put("state", States.START_NEW_GAME_STATE)
        }
    }

    override fun onExit() {
    }
}
