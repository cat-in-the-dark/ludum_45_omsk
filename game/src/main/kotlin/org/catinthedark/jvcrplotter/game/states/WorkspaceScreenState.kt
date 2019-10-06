package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.texture
import org.catinthedark.jvcrplotter.game.ui.Button
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class WorkspaceScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val editorBtn = Button(450,175,630,315, onClick = {
        IOC.put("state", States.CODE_EDITOR_SCREEN)
    })
    private val manPageBtm = Button(50,170, 160, 250, onClick = {
        IOC.put("state", States.MAN_PAGE_SCREEN)
    })
    private val taskBtm = Button(1140,310, 1250, 420, onClick = {
        IOC.put("state", States.TASK_SCREEN)
    })

    override fun onActivate() {
        IOC.put("previousState", States.WORKSPACE_SCREEN)
    }

    override fun onUpdate() {
        hud.batch.managed { b ->
            b.draw(am.texture(Assets.Names.WORKSPACE), 0f, 0f)
//            editorBtn.draw(b, Color.GOLD)
//            manPageBtm.draw(b, Color.GOLD)
//            taskBtm.draw(b, Color.GOLD)
        }

        editorBtn.update()
        manPageBtm.update()
        taskBtm.update()
    }

    override fun onExit() {

    }
}
