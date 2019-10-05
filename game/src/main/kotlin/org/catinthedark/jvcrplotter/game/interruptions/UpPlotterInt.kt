package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.plotter.PlotState

class UpPlotterInt(
    private val state: State,
    private val plotState: PlotState
) : Interruption {
    override val name = "up_plotter"
    override fun apply(): Boolean {
        plotState.isPointerUp = true
        return true
    }
}
