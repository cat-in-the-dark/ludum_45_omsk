package org.catinthedark.jvcrplotter.game.states

import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.states.IState

class StartNewGameState : IState {
    override fun onActivate() {
        val editor = Editor(
            mutableListOf(
                mutableListOf("MOV", "X", "1"),
                mutableListOf("MOV", "Y", "1"),
                mutableListOf("INT", "42", ""),
                mutableListOf("INT", "44", ""),
                mutableListOf("INT", "43", ""),
                mutableListOf("MOV", "X", "30"),
                mutableListOf("INT", "44", ""),
                mutableListOf("MOV", "Y", "30"),
                mutableListOf("INT", "44", ""),
                mutableListOf("MOV", "X", "1"),
                mutableListOf("INT", "44", ""),
                mutableListOf("MOV", "Y", "1"),
                mutableListOf("INT", "44", "")
            )
        )
        IOC.put("editor", editor)
        IOC.put("state", States.WORKSPACE_SCREEN)
        IOC.put("currentTaskId", 0)
    }

    override fun onUpdate() {
    }

    override fun onExit() {
    }
}
