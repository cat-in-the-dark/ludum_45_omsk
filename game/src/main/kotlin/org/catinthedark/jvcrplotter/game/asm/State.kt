package org.catinthedark.jvcrplotter.game.asm

import org.catinthedark.jvcrplotter.game.asm.exceptions.EOFException

data class State(
    val registers: MutableMap<Int, Int> = hashMapOf(
        X to 0,
        Y to 0,
        A to 0,
        B to 0,
        FLAG to 0
    ),
    var programCounter: Int = 0, // address of the current instruction
    val instructions: List<Operation> = listOf(),
    var intCode: Int = 0
) {
    fun get(address: Int): Int {
        return registers[address] ?: throw  Exception("Wrong register address $address")
    }

    fun set(address: Int, value: Int) {
        if (registers[address] == null) {
            throw  Exception("Wrong register address $address")
        }

        registers[address] = value
    }

    fun checkProgramCounter() {
        if (programCounter < 0) {
            throw EOFException("programCounter = $programCounter")
        }
        if (programCounter >= instructions.size) {
            throw EOFException("programCounter = $programCounter")
        }
    }

    fun repr() = "op=${instructions.getOrNull(programCounter)?.name ?: "EOF"} line=${programCounter} X=${get(X)} Y=${get(Y)} A=${get(A)} B=${get(B)} FLAG=${get(FLAG)} INT=$intCode"
}
