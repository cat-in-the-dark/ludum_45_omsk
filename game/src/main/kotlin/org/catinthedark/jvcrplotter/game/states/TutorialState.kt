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

class TutorialState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val tutorials =
        listOf(
            Assets.Names.TUTORIAL1,
            Assets.Names.TUTORIAL2,
            Assets.Names.TUTORIAL3,
            Assets.Names.TUTORIAL3_5,
            Assets.Names.TUTORIAL4,
            Assets.Names.TUTORIAL5
        )
    private var tutIdx = 0

    private var currentTime = 0f

    override fun onActivate() {
        tutIdx = 0
    }

    override fun onUpdate() {
        hud.batch.managed { b ->
            b.draw(am.texture(tutorials[tutIdx]), 0f, 0f)

            currentTime += Gdx.graphics.deltaTime

            if ((currentTime * 2).toInt() % 2 == 0) {
                am.at<BitmapFont>(Assets.Names.FONT_BIG).draw(
                    b, "Нажмите пробел", 800f, 70f
                )
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            tutIdx += 1
            if (tutIdx == tutorials.size) {
                IOC.put("state", States.WORKSPACE_SCREEN)
            }
        }
    }

    override fun onExit() {
    }
}
