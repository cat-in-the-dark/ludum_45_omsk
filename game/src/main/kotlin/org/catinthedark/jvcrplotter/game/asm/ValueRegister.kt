package org.catinthedark.jvcrplotter.game.asm

class ValueRegister(
    private val registerName: Int
) : MutableValue {
    override fun set(state: State, value: Int) {
        state.set(registerName, value)
    }

    override fun get(state: State): Int {
        return state.get(registerName)
    }
}
