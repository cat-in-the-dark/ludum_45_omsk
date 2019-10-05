package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State

class UpPlotterInt(
    private val state: State
) : Interruption {
    override val name = "up_plotter"
    override fun apply(): Boolean {
        return true
    }
}
