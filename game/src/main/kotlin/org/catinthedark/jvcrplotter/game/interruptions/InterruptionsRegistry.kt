package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.plotter.PlotState

fun buildInterruptionsRegistry(state: State, plotState: PlotState) = mapOf(
    INT_UP to UpPlotterInt(state, plotState),
    INT_DOWN to DownPlotterInt(state, plotState),
    MOVE_PLOTTER to MovePlotterInt(state, plotState)
)
