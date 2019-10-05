package org.catinthedark.jvcrplotter.lib.states

interface IState {
    fun onActivate()
    fun onUpdate()
    fun onExit()
}
