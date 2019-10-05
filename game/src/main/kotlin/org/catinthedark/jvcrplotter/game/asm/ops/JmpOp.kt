package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.asm.Value

class JmpOp(
    private val op2: Value
): Operation {
    override val name = "jmp"
    override fun apply(state: State) {
        state.programCounter += op2.get(state)
    }
}
