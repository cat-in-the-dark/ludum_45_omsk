package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.exceptions.EOFException
import org.slf4j.LoggerFactory

class Interpreter {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun step(state: State): Boolean {
        return try {
            state.checkProgramCounter()
            logger.info(state.repr())
            state.instructions[state.programCounter].apply(state)
            state.programCounter += 1
            true
        } catch (e: EOFException) {
            false
        }
    }
}
