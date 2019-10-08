package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.texture
import org.catinthedark.jvcrplotter.game.ui.HelperDialog
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class WinScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private var instructionsCount: Int = 0
    private lateinit var congratsDialog: HelperDialog


    override fun onActivate() {
        instructionsCount = IOC.atOrFail("instructionsCount")
        congratsDialog = HelperDialog(
            400, 250, 530, 300,
            "Congratulations, comrade!\n" +
                "You've solved all the tasks\n" +
                "in [RED]$instructionsCount[] instructions!\n" +
                "Can you do better?"
        )
        IOC.put("showTutorial", false)
    }

    override fun onUpdate() {
        hud.batch.managed { b ->
            b.draw(am.texture(Assets.Names.WORKSPACE), 0f, 0f)
        }
        congratsDialog.updateAndDraw(hud.batch)
        if (congratsDialog.isClosed) {
            IOC.put("state", States.START_NEW_GAME_STATE)
            congratsDialog.reset()
        }
    }

    override fun onExit() {
    }
}
