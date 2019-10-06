package org.catinthedark.jvcrplotter.game.interruptions

import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.asm.X
import org.catinthedark.jvcrplotter.game.asm.Y
import org.catinthedark.jvcrplotter.game.plotter.PlotState
import org.slf4j.LoggerFactory
import kotlin.math.sign

class MovePlotterInt(
    private val state: State,
    private val plotState: PlotState
) : Interruption {
    private val logger = LoggerFactory.getLogger(javaClass)
    override val name = "move_plotter"
    override fun apply(): Boolean {
        val targetX = state.get(X)
        val targetY = state.get(Y)

        logger.info("MOVE toX=$targetX toY=$targetY cX=${plotState.vram.currentX} cY=${plotState.vram.currentY}")

        val dx = targetX - plotState.vram.currentX
        val dy = targetY - plotState.vram.currentY

//        logger.info("dx=$dx dy=$dy X=$targetX Y=$targetY cX=${plotState.vram.currentX} cY=${plotState.vram.currentY}")
        if (dx == 0 && dy == 0) {
            return true
        }

        val x = plotState.vram.currentX + dx.sign
        val y = plotState.vram.currentY + dy.sign

        if (plotState.isPointerUp) {
            plotState.vram.set(x, y) // just move
        } else {
            plotState.vram.set(x, y, plotState.pencilColor)
        }

        return false
    }
}
