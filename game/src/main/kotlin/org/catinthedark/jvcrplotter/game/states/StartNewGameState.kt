package org.catinthedark.jvcrplotter.game.states

import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.at
import org.catinthedark.jvcrplotter.lib.states.IState

class StartNewGameState : IState {
    override fun onActivate() {
        val tutorialShown = IOC.at<Boolean>("tutorialShown") ?: false
        if (!tutorialShown) {
            IOC.put("tutorialShown", true)
            IOC.put("state", States.TUTORIAL_STATE)
            val editor = Editor(
                mutableListOf(
                    mutableListOf("INT", "42", ""),
                    mutableListOf("MOV", "X", "0"),
                    mutableListOf("MOV", "Y", "0"),
                    mutableListOf("INT", "44", ""),
                    mutableListOf("INT", "43", ""),
                    mutableListOf("MOV", "X", "30"),
                    mutableListOf("MOV", "Y", "30"),
                    mutableListOf("INT", "44", "")
                )
            )
            IOC.put("editor", editor)
            IOC.put("currentTaskId", 0)
        }
    }

    override fun onUpdate() {
    }

    override fun onExit() {
    }
}
