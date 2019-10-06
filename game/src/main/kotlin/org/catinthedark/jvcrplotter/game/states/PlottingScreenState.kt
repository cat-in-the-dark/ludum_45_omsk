package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.*
import org.catinthedark.jvcrplotter.game.asm.Interpreter
import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.game.interruptions.buildInterruptionsRegistry
import org.catinthedark.jvcrplotter.game.plotter.PlotState
import org.catinthedark.jvcrplotter.game.plotter.PlotVRAM
import org.catinthedark.jvcrplotter.game.ui.EditorRender
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class PlottingScreenState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private var time: Float = 0f
    private lateinit var interpreter: Interpreter

    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val instructions: List<Operation> by lazy { IOC.atOrFail<List<Operation>>("instructions") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private lateinit var state: State
    private lateinit var plotState: PlotState
    private var running: Boolean = true
    private var runError: Boolean = false
    private var errorMessage: String? = ""
    private val editor: Editor by lazy { IOC.atOrFail<Editor>("editor") }
    private val cursorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.LINE_FRAME), 6, 6, 6, 6) }
    private val errorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.ERROR_FRAME), 6, 6, 6, 6) }
    private val editorRender: EditorRender by lazy {
        EditorRender(
            hud.batch, am.font(Assets.Names.FONT_BIG_GREEN),
            cursorFrame,
            errorFrame
        )
    }

    override fun onActivate() {
        logger.info("onActivate")
        time = 0f
        running = true

        state = State(
            instructions = instructions
        )
        plotState = PlotState(
            PlotVRAM(width = Const.Plotter.WIDTH, height = Const.Plotter.HEIGHT)
        )
        interpreter = Interpreter(
            buildInterruptionsRegistry(
                state = state,
                plotState = plotState
            )
        )
    }

    private fun createPlot(): Texture {
        val pixmap = Pixmap(
            Const.Plotter.WIDTH, Const.Plotter.HEIGHT, Pixmap.Format.RGBA8888
        )
        for (x in 0..plotState.vram.width) {
            for (y in 0..plotState.vram.height) {
                pixmap.drawPixel(x, y, plotState.vram.get(x, y))
            }
        }

        val tex = Texture(pixmap)
        pixmap.dispose()
        return tex
    }

    private fun draw() {
        if (running) {
            try {
                running = interpreter.step(state)
                editor.setCursorPosition(0, state.programCounter)
            } catch (e: Exception) {
                running = false
                logger.error("FAIL", e)
                // TODO: Show error for the user???
            }
        }
    }

    override fun onUpdate() {
        time += Gdx.graphics.deltaTime

        hud.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.MONIK), 0f, 0f)
            it.draw(createPlot(), 10f, 10f, 256f, 256f)
        }

        renderEditorText(editor)

        draw()
    }


    private fun renderEditorText(editor: Editor) {
        editorRender.render(
            editor,
            runError,
            errorMessage,
            true
        )
    }

    override fun onExit() {

    }
}
