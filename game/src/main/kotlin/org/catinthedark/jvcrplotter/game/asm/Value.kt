package org.catinthedark.jvcrplotter.game.asm

interface Value {
    fun get(state: State): Int
}
