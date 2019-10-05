package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.MutableValue
import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.asm.Value

/**
 * ACTION: OP1 = OP1 + OP2
 * SYNTAX:
 * `add OP1, OP2`
 * OP1 - is a register name
 * OP2 - is a register name or numeric literal
 *
 * Examples:
 * `add X, 2` // X = X + 2
 * `add X, Y` // X = X + Y
 * `add 2, 4` // ERROR
 */
class AddOp(
    private val op1: MutableValue,
    private val op2: Value
): Operation {
    override fun apply(state: State) {
        op1.set(state, op1.get(state) + op2.get(state))
    }
}
