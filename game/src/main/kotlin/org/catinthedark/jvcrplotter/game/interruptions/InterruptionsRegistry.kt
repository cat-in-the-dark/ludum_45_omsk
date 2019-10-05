package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.plotter.PlotState
import org.slf4j.LoggerFactory

class InterruptionsRegistry(
    private val state: State,
    private val plotState: PlotState
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val intRegistry = mapOf(
        INT_UP to UpPlotterInt(state),
        INT_DOWN to DownPlotterInt(state),
        MOVE_PLOTTER to MovePlotterInt(state)
    )

    fun apply(intCode: Int): Boolean {
        val i = intRegistry[intCode] ?: throw Exception("Unknown interruption $intCode")
        logger.info(i.name)
        val res = i.apply()
        if (res) {
            state.intCode = -1
        }

        return res
    }
}
