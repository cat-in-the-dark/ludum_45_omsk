package org.catinthedark.jvcrplotter.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.catinthedark.jvcrplotter.game.states.*
import org.catinthedark.jvcrplotter.lib.Deffer
import org.catinthedark.jvcrplotter.lib.DefferImpl
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.states.StateMachine

class JVCRPlotter : Game() {
    private val stage: Stage by lazy {
        Stage(
            FitViewport(
                Const.Screen.WIDTH / Const.Screen.ZOOM,
                Const.Screen.HEIGHT / Const.Screen.ZOOM,
                OrthographicCamera()
            ), SpriteBatch()
        )
    }
    private val hud: Stage by lazy {
        Stage(
            FitViewport(
                Const.Screen.WIDTH / Const.Screen.ZOOM,
                Const.Screen.HEIGHT / Const.Screen.ZOOM,
                OrthographicCamera()
            ), SpriteBatch()
        )
    }
    private val sm: StateMachine by lazy {
        StateMachine().apply {
            putAll(
                States.SPLASH_SCREEN to SplashScreenState(),
                States.TITLE_SCREEN to TitleScreenState(),
                States.CODE_EDITOR_SCREEN to CodeEditorState(),
                States.PLOTTING_SCREEN to PlottingScreenState(),
                States.START_NEW_GAME_STATE to StartNewGameState(),
                States.MAN_PAGE_SCREEN to ManPageScreenState(),
                States.TUTORIAL_STATE to TutorialState(),
                States.TASK_SCREEN to TaskScreenState(),
                States.WORKSPACE_SCREEN to WorkspaceScreenState(),
                States.SUCCESS_SCREEN to SuccessScreenState()
            )
            putMixins(
                States.SPLASH_SCREEN,
                States.TITLE_SCREEN,
                States.CODE_EDITOR_SCREEN,
                States.PLOTTING_SCREEN,
                States.START_NEW_GAME_STATE,
                States.MAN_PAGE_SCREEN,
                States.TASK_SCREEN,
                States.WORKSPACE_SCREEN,
                States.SUCCESS_SCREEN
            ) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    IOC.put("state", States.TITLE_SCREEN)
                }
                IOC.atOrFail<InputAdapterHolder>("inputs").update()
            }
            putMixins(
                States.CODE_EDITOR_SCREEN,
                States.PLOTTING_SCREEN,
                States.START_NEW_GAME_STATE,
                States.TUTORIAL_STATE,
                States.MAN_PAGE_SCREEN,
                States.TASK_SCREEN,
                States.WORKSPACE_SCREEN
            ) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
                    val prevState = IOC.get("previousState")
                    if (prevState != null) {
                        IOC.put("state", prevState)
                    }
                }
            }
        }
    }

    override fun create() {
        IOC.put("deffer", DefferImpl())
        IOC.put("stage", stage)
        IOC.put("hud", hud)
        IOC.put("state", States.SPLASH_SCREEN)
        val inputs = InputAdapterHolder(stage)
        Gdx.input.inputProcessor = inputs
        IOC.put("inputs", inputs)
    }

    override fun render() {
        val deffer: Deffer by IOC

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        stage.viewport.apply()
        stage.act(Gdx.graphics.deltaTime)
        stage.batch.projectionMatrix = stage.viewport.camera.combined

        hud.viewport.apply()
        hud.act(Gdx.graphics.deltaTime)
        hud.batch.projectionMatrix = hud.viewport.camera.combined

        deffer.update(Gdx.graphics.deltaTime)
        sm.onUpdate()
        stage.draw()
        hud.draw()

        super.render()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height)
        hud.viewport.update(width, height)
    }
}
