package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import org.catinthedark.jvcrplotter.game.Assets
import org.catinthedark.jvcrplotter.game.asm.*
import org.catinthedark.jvcrplotter.game.asm.ops.IntOp
import org.catinthedark.jvcrplotter.game.asm.ops.MovOp
import org.catinthedark.jvcrplotter.game.interruptions.INT_DOWN
import org.catinthedark.jvcrplotter.game.interruptions.INT_UP
import org.catinthedark.jvcrplotter.game.interruptions.InterruptionsRegistry
import org.catinthedark.jvcrplotter.game.interruptions.MOVE_PLOTTER
import org.catinthedark.jvcrplotter.game.plotter.PlotState
import org.catinthedark.jvcrplotter.game.plotter.PlotVRAM
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class PlottingScreenState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    private var time: Float = 0f
    private val interpreter = Interpreter()

    private val am: AssetManager by lazy { Assets.load() }
    private lateinit var state: State
    private lateinit var intRegistry: InterruptionsRegistry
    private var running: Boolean = true

    override fun onActivate() {
        logger.info("onActivate")
        time = 0f
        running = true

        state = State(
            instructions = listOf(
                MovOp(ValueRegister(X), ValueLiteral(1)),
                MovOp(ValueRegister(Y), ValueLiteral(1)),
                IntOp(ValueLiteral(INT_UP)),
                IntOp(ValueLiteral(MOVE_PLOTTER)),

                MovOp(ValueRegister(X), ValueLiteral(4)),
                IntOp(ValueLiteral(INT_DOWN)),
                IntOp(ValueLiteral(MOVE_PLOTTER)),

                MovOp(ValueRegister(Y), ValueLiteral(4)),
                IntOp(ValueLiteral(INT_DOWN)),
                IntOp(ValueLiteral(MOVE_PLOTTER)),

                MovOp(ValueRegister(X), ValueLiteral(0)),
                IntOp(ValueLiteral(INT_DOWN)),
                IntOp(ValueLiteral(MOVE_PLOTTER)),

                MovOp(ValueRegister(Y), ValueLiteral(0)),
                IntOp(ValueLiteral(INT_DOWN)),
                IntOp(ValueLiteral(MOVE_PLOTTER))
            )
        )
        intRegistry = InterruptionsRegistry(
            state = state,
            plotState = PlotState(
                PlotVRAM(width = 32, height = 32)
            )
        )
    }

    override fun onUpdate() {
        time += Gdx.graphics.deltaTime

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

    override fun onExit() {

    }
}
