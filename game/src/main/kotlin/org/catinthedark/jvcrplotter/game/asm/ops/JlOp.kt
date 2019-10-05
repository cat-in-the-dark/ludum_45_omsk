package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.FLAG
import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.asm.Value

class JlOp(
    private val op2: Value
) : Operation {
    override fun apply(state: State) {
        if (state.registers[FLAG] < 0) {
            state.programCounter += op2.get(state)
        }
    }
}
