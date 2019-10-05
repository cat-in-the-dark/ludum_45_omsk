package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.ops.JmpOp
import org.catinthedark.jvcrplotter.game.asm.ops.MovOp
import org.junit.Assert.assertEquals
import org.junit.Test

class JmpOpTest {
    @Test
    fun ShouldJump() {
        val state = State()
        val instructions = listOf(
            MovOp(ValueRegister(X), ValueLiteral(10)),
            JmpOp(ValueRegister(X))
        )

        instructions.forEach { it.apply(state) }
        assertEquals(10, state.programCounter)
    }
}
