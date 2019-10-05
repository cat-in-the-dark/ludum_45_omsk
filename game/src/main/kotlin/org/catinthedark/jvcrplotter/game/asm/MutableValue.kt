package org.catinthedark.jvcrplotter.game.asm

interface MutableValue : Value {
    fun set(state: State, value: Int)
}
