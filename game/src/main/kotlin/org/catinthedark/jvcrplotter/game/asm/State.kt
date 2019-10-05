package org.catinthedark.jvcrplotter.game.asm

data class State(
    val registers: MutableList<Int> = mutableListOf(0, 0),
    var programCounter: Int = 0 // address of the current instruction
) {
    fun isValidRegisterAddress(address: Int) {
        if (address !in 0..registers.size) {
            throw  Exception("Wrong register address $address")
        }
    }
}
