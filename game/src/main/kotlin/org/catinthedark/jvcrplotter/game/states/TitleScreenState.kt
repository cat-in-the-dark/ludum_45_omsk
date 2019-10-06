package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.at
import org.catinthedark.jvcrplotter.game.texture
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState

class TitleScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private var currentTime = 0f

    override fun onActivate() {
        currentTime = 0f
    }

    override fun onUpdate() {
        hud.batch.managed { b ->
            b.draw(am.texture(Assets.Names.TITLE), 0f, 0f)

            currentTime += Gdx.graphics.deltaTime

            if ((currentTime * 2).toInt() % 2 == 0) {
                am.at<BitmapFont>(Assets.Names.FONT_BIG).draw(
                    b, "Нажмите пробел", 630f, 220f
                )
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            IOC.put("state", States.TUTORIAL_STATE)
        }
    }

    override fun onExit() {
    }
}
