package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.ops.AddOp
import org.catinthedark.jvcrplotter.game.asm.ops.MulOp
import org.junit.Assert
import org.junit.Test

class AddOpTest {
    @Test
    fun ShouldAddLiteral() {
        val state = State()
        state.registers[X] = 3

        AddOp(
            ValueRegister(X),
            ValueLiteral(2)
        ).apply(state)

        Assert.assertEquals(state.registers[X], 5)
    }

    @Test
    fun ShouldAddRegisters() {
        val state = State()
        state.registers[X] = 3
        state.registers[Y] = 4

        AddOp(
            ValueRegister(X),
            ValueRegister(Y)
        ).apply(state)

        Assert.assertEquals(state.registers[X], 7)
    }
}
