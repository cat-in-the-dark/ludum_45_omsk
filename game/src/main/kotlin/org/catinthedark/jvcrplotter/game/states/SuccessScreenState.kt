package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.Tasks
import org.catinthedark.jvcrplotter.game.texture
import org.catinthedark.jvcrplotter.game.ui.HelperDialog
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class SuccessScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private lateinit var congratsDialog: HelperDialog
    private var solutionInstructions: Int = 0

    override fun onActivate() {
        solutionInstructions = IOC.atOrFail("solutionInstructions")
        congratsDialog = HelperDialog(
            400, 250, 530, 300,
            "Congratulations, comrade!\n" +
                "You've solved this task\n" +
                "in [RED]$solutionInstructions[] instructions!"
        )
    }

    override fun onUpdate() {
        hud.batch.managed {
            it.draw(am.texture(Assets.Names.WORKSPACE), 0f, 0f)
        }

        congratsDialog.updateAndDraw(hud.batch)
        if (congratsDialog.isClosed) {
            val currentTaskId = IOC.atOrFail<Int>("currentTaskId")
            val nextTaskId = (currentTaskId + 1) % Tasks.taskTextures.size
            if (nextTaskId < currentTaskId) {
                IOC.put("state", States.WIN_SCREEN)
            } else {
                IOC.put("currentTaskId", nextTaskId)
                IOC.put("state", States.WORKSPACE_SCREEN)
            }
            congratsDialog.reset()
        }
    }

    override fun onExit() {

    }
}
