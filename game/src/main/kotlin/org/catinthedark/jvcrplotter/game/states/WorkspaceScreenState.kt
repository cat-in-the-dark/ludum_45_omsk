package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.ui.Button
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class WorkspaceScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val editorBtn = Button(0,0,64,64, onClick = {
        IOC.put("previousState", States.WORKSPACE_SCREEN)
        IOC.put("state", States.CODE_EDITOR_SCREEN)
    })
    private val manPageBtm = Button(70,0, 70+64, 64, onClick = {
        IOC.put("previousState", States.WORKSPACE_SCREEN)
        IOC.put("state", States.MAN_PAGE_SCREEN)
    })
    private val taskBtm = Button(70+70,0, 70+70+64, 64, onClick = {
        IOC.put("previousState", States.WORKSPACE_SCREEN)
        IOC.put("state", States.TASK_SCREEN)
    })

    override fun onActivate() {

    }

    override fun onUpdate() {
        hud.batch.managed { b ->
            editorBtn.draw(b)
            manPageBtm.draw(b)
            taskBtm.draw(b)
        }

        editorBtn.update()
        manPageBtm.update()
        taskBtm.update()
    }

    override fun onExit() {

    }
}
