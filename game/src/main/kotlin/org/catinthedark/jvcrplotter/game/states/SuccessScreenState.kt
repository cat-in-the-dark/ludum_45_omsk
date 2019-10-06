package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets.Names.FONT_BIG_GREEN
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.Tasks
import org.catinthedark.jvcrplotter.game.font
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class SuccessScreenState: IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val font: BitmapFont by lazy { am.font(FONT_BIG_GREEN) }

    override fun onActivate() {

    }

    override fun onUpdate() {
        hud.batch.managed {
            font.draw(it, "SUCCESS. PRESS SPACE", 10f, 200f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            val nextTaskId = (IOC.atOrFail<Int>("currentTaskId") + 1) % Tasks.tasks.size
            IOC.put("currentTaskId", nextTaskId)
            IOC.put("state", States.WORKSPACE_SCREEN)
        }
    }

    override fun onExit() {

    }
}
