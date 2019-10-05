package org.catinthedark.jvcrplotter.game.states

import org.catinthedark.jvcrplotter.game.States
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.states.IState

class StartNewGameState : IState {
    override fun onActivate() {
        IOC.put("editor", Editor(3))
        IOC.put("state", States.CODE_EDITOR_SCREEN)
    }

    override fun onUpdate() {
    }

    override fun onExit() {
    }
}
