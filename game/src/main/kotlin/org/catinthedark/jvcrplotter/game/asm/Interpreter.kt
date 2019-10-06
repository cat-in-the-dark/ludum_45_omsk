package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.exceptions.EOFException
import org.catinthedark.jvcrplotter.game.interruptions.Interruption
import org.slf4j.LoggerFactory

class Interpreter(
    private val intRegistry: Map<Int, Interruption> = emptyMap()
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun step(state: State): Boolean {
        return try {
            state.checkProgramCounter()
            logger.info(state.repr())
            if (state.intCode > 0) {
                handleInt(state)
            } else {
                handleOp(state)
            }
            true
        } catch (e: EOFException) {
            logger.info("EOF")
            false
        }
    }

    private fun handleOp(state: State) {
        state.instructions[state.programCounter].apply(state)
        if (state.intCode <= 0) {
            state.programCounter += 1
        }
    }

    private fun handleInt(state: State) {
        val i = intRegistry[state.intCode] ?: throw Exception("Unknown interruption ${state.intCode}")
        logger.info("handle int ${i.name}")
        val res = i.apply()
        if (res) {
            state.intCode = -1
            state.programCounter += 1
        }
    }
}
