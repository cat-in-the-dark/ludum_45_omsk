package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.*

/**
 * SYNTAX:
 * `mov TO, FROM`
 * TO - is a register name
 * FROM - is a register name or numeric literal
 *
 * Examples:
 * `mov X, 1` // put literal 1 into register ax
 * `mov X, Y` // puts value of the register bx into register ax
 * `mov 1, Y` // ERROR
 */
class MovOp(
    private val op1: MutableValue,
    private val op2: Value
) : Operation {
    override fun apply(state: State) {
        op1.set(state, op2.get(state))
    }
}
