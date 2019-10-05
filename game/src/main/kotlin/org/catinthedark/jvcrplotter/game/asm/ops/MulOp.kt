package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.*

/**
 * ACTION: OP1 = OP1 * OP2
 * SYNTAX:
 * `mul OP1, OP2`
 * OP1 - is a register name
 * OP2 - is a register name or numeric literal
 *
 * Examples:
 * `mul X, 2` // X = X * 2
 * `mul X, Y` // X = X * Y
 * `mul 2, 4` // ERROR
 */
class MulOp(
    private val op1: MutableValue,
    private val op2: Value
): Operation {
    override val name = "mul"
    override fun apply(state: State) {
        op1.set(state, op1.get(state) * op2.get(state))
    }
}
