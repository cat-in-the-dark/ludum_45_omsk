package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.ops.MovOp
import org.junit.Assert.assertEquals
import org.junit.Test

class MovOpTest {
    @Test
    fun ShouldPutDataToRegister() {
        val state = State()
        val mov = MovOp(
            ValueRegister(X),
            ValueLiteral(42)
        )
        mov.apply(state)
        assertEquals(state.registers[X], 42)
    }

    @Test
    fun ShouldPutRegisterToRegister() {
        val state = State(
            registers = mutableListOf(
                0, 42
            )
        )
        val mov = MovOp(
            ValueRegister(X),
            ValueRegister(Y)
        )
        mov.apply(state)
        assertEquals(state.registers[X], 42)
    }
}
