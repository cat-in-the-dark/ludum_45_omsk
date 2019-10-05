package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.Const
import org.catinthedark.jvcrplotter.game.asm.Interpreter
import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.interruptions.InterruptionsRegistry
import org.catinthedark.jvcrplotter.game.plotter.PlotState
import org.catinthedark.jvcrplotter.game.plotter.PlotVRAM
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.atOrFail
import org.catinthedark.jvcrplotter.lib.managed
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class PlottingScreenState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    private var time: Float = 0f
    private val interpreter = Interpreter()

    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val instructions: List<Operation> by lazy { IOC.atOrFail<List<Operation>>("instructions") }
    private val am: AssetManager by lazy { Assets.load() }
    private lateinit var state: State
    private lateinit var plotState: PlotState
    private lateinit var intRegistry: InterruptionsRegistry
    private var running: Boolean = true

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
        intRegistry = InterruptionsRegistry(
            state = state,
            plotState = plotState
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
                if (state.intCode > 0) {
                    intRegistry.apply(state.intCode)
                } else {
                    running = interpreter.step(state)
                }
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
            it.draw(createPlot(), 10f, 10f, 256f, 256f)
        }

        draw()
        draw()
    }

    override fun onExit() {

    }
}
