package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.*
import org.catinthedark.jvcrplotter.game.plotter.createPlot
import org.catinthedark.jvcrplotter.game.ui.Button
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class TaskScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val backBtn = Button(800,620,900,650, onClick = {
        IOC.put("state", IOC.get("previousState") ?: States.WORKSPACE_SCREEN)
    })
    private val font by lazy { am.font(Assets.Names.FONT_BIG_GREEN) }
    private lateinit var tex: Texture

    override fun onActivate() {
        val taskId: Int = IOC.atOrFail("currentTaskId")
        val task = Tasks.tasks[taskId]
        tex = createPlot(task, background = Color.DARK_GRAY)
    }

    override fun onUpdate() {
        backBtn.update()

        hud.batch.managed {
            it.draw(tex, 320f, 620f-352f, 352f, 352f)
            font.draw(it, "ТЕХНИЧЕСКОЕ ЗАДАНИЕ", 320f, 650f)
            font.draw(it, "НАЗАД", 800f, 650f)
        }
    }

    override fun onExit() {

    }
}
