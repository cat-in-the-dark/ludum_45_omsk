package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.ops.AddOp
import org.catinthedark.jvcrplotter.game.asm.ops.MovOp
import org.catinthedark.jvcrplotter.game.asm.ops.MulOp
import org.junit.Assert
import org.junit.Test

class ProgramTest {
    @Test
    fun ShoudlWork() {
        val state = State()
        val instructions = listOf(
            MovOp(ValueRegister(X), ValueLiteral(2)), // mov x 2
            MovOp(ValueRegister(Y), ValueLiteral(3)), // mov y 3
            MulOp(ValueRegister(X), ValueRegister(Y)),     // mul x y; 6
            MulOp(ValueRegister(Y), ValueRegister(Y)),     // mul y 3; 9
            AddOp(ValueRegister(X), ValueRegister(Y))      // add x y; 15
        )
        instructions.forEach { it.apply(state) }
        Assert.assertEquals(15, state.registers[X])
        Assert.assertEquals(9, state.registers[Y])
    }
}
