package org.catinthedark.jvcrplotter.game.interruptions

interface Interruption {
    val name: String
    fun apply(): Boolean
}
