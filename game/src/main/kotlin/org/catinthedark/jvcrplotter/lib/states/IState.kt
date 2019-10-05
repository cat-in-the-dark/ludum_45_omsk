package org.catinthedark.itsadeal.lib.states

interface IState {
    fun onActivate()
    fun onUpdate()
    fun onExit()
}
