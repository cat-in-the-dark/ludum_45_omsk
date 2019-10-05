package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.asm.Value

class IntOp(
    private val op1: Value
) : Operation {
    override val name = "int"
    override fun apply(state: State) {
        state.intCode = op1.get(state)
    }
}
