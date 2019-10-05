package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.exceptions.EOFException

class Interpreter {
    fun step(state: State): Boolean {
        return try {
            state.checkProgramCounter()
            state.instructions[state.programCounter].apply(state)
            state.programCounter += 1
            true
        } catch (e: EOFException) {
            false
        }
    }
}
