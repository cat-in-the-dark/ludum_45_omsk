package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State

class DownPlotterInt(
    private val state: State
) : Interruption {
    override val name = "down_plotter"
    override fun apply(): Boolean {

        return true
    }
}
