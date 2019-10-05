package org.catinthedark.jvcrplotter.game.asm

class ValueRegister(
    private val registerName: Int
) : MutableValue {
    override fun set(state: State, value: Int) {
        state.isValidRegisterAddress(registerName)

        state.registers[registerName] = value
    }

    override fun get(state: State): Int {
        state.isValidRegisterAddress(registerName)
        return state.registers[registerName]
    }
}
