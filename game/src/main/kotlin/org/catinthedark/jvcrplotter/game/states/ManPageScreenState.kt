package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.ui.Button
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.states.IState

class ManPageScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val backBtn = Button(0,0,64,64, onClick = {
        IOC.put("state", IOC.get("previousState") ?: States.WORKSPACE_SCREEN)
        IOC.put("previousState", null)
    })

    override fun onActivate() {

    }

    override fun onUpdate() {
        backBtn.update()
    }

    override fun onExit() {

    }
}
