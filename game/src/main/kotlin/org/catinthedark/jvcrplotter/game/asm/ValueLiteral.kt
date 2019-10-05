package org.catinthedark.jvcrplotter.game.asm

class ValueLiteral(
    private val data: Int
) : Value {
    override fun get(state: State): Int {
        return data
    }
}
