package org.catinthedark.jvcrplotter.game.asm.ops

import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.PeripheralPort
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.asm.Value

class IntOp(
    private val op1: Value,
    private val port: PeripheralPort
) : Operation {
    override fun apply(state: State) {
        port.intNum = op1.get(state)
    }
}
