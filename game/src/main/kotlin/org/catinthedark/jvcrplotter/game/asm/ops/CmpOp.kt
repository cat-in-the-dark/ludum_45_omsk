package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.FLAG
import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.asm.Value

class CmpOp(
    private val op1: Value,
    private val op2: Value
) : Operation {
    override fun apply(state: State) {
        state.registers[FLAG] = op1.get(state).compareTo(op2.get(state))
    }
}
