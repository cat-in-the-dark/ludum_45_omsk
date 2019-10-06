package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.plotter.PlotState

class DownPlotterInt(
    private val state: State,
    private val plotState: PlotState
) : Interruption {
    override val name = "down_plotter"
    override fun apply(): Boolean {
        plotState.isPointerUp = false

        plotState.vram.set(plotState.vram.currentX, plotState.vram.currentY, plotState.pencilColor)
        
        return true
    }
}
