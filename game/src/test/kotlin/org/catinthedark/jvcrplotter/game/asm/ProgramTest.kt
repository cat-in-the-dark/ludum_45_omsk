package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.ops.*
import org.junit.Assert
import org.junit.Test

class ProgramTest {
    @Test
    fun ShoudlWork() {
        val state = State(
            instructions = listOf(
                MovOp(ValueRegister(X), ValueLiteral(2)), // mov x 2
                MovOp(ValueRegister(Y), ValueLiteral(3)), // mov y 3
                MulOp(ValueRegister(X), ValueRegister(Y)),     // mul x y; 6
                MulOp(ValueRegister(Y), ValueRegister(Y)),     // mul y 3; 9
                AddOp(ValueRegister(X), ValueRegister(Y))      // add x y; 15
            )
        )
        val interpreter = Interpreter(state)
        while (interpreter.step()) {
            state.print()
        }

        Assert.assertEquals(15, state.registers[X])
        Assert.assertEquals(9, state.registers[Y])
    }

    @Test
    fun ShoudlWork2() {
        val state = State(
            instructions = listOf(
                MovOp(ValueRegister(X), ValueLiteral(2)),
                MovOp(ValueRegister(Y), ValueLiteral(3)),
                JmpOp(ValueLiteral(1)),
                MovOp(ValueRegister(X), ValueLiteral(100)),
                MovOp(ValueRegister(Y), ValueLiteral(42))
            )
        )
        val interpreter = Interpreter(state)
        while (interpreter.step()) {
            state.print()
        }

        Assert.assertEquals(2, state.registers[X])
        Assert.assertEquals(42, state.registers[Y])
    }

    @Test
    fun ShoudlWork3() {
        val state = State(
            instructions = listOf(
                MovOp(ValueRegister(X), ValueLiteral(5)),
                MovOp(ValueRegister(Y), ValueLiteral(1)),

                MovOp(ValueRegister(A), ValueLiteral(2)),

                MulOp(ValueRegister(Y), ValueRegister(A)),
                AddOp(ValueRegister(X), ValueLiteral(-1)),
                CmpOp(ValueRegister(X), ValueLiteral(0)),
                JgOp(ValueLiteral(-4))
            )
        )
        val interpreter = Interpreter(state)
        while (interpreter.step()) {
            state.print()
        }

        Assert.assertEquals(32, state.registers[Y])
        Assert.assertEquals(0, state.registers[X])
    }
}
