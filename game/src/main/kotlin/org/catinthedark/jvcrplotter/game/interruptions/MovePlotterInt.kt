package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State

class MovePlotterInt(
    private val state: State
) : Interruption {
    override val name = "move_plotter"
    override fun apply(): Boolean {
        return true
    }
}
