package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.ops.MulOp
import org.junit.Assert
import org.junit.Test

class MulOpTest {
    @Test
    fun ShouldMulLiteral() {
        val state = State()
        state.registers[X] = 3

        val mul = MulOp(
            ValueRegister(X),
            ValueLiteral(2)
        )
        mul.apply(state)
        Assert.assertEquals(state.registers[X], 6)
    }

    @Test
    fun ShouldMulRegisters() {
        val state = State()
        state.registers[X] = 3
        state.registers[Y] = 4

        val mul = MulOp(
            ValueRegister(X),
            ValueRegister(Y)
        )
        mul.apply(state)
        Assert.assertEquals(state.registers[X], 12)
    }
}
