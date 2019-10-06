package org.catinthedark.jvcrplotter.game.asm

interface IInterruptionRegistry {
    fun apply(intCode: Int): Boolean
}
