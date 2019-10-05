package org.catinthedark.jvcrplotter.game.ui

import org.catinthedark.jvcrplotter.game.InputAdapterHolder
import org.catinthedark.jvcrplotter.lib.IOC
import org.catinthedark.jvcrplotter.lib.at

class Button(
    val xMin: Int,
    val yMin: Int,
    val xMax: Int,
    val yMax: Int,
    private val onClick: () -> Unit = {},
    private val onHover: () -> Unit = {}
) {
    var isClicked: Boolean = false
        private set


    fun isHover(x: Int, y: Int): Boolean {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax
    }

    fun update() {
        isClicked = false
        val inputs: InputAdapterHolder = IOC.at("inputs") ?: return
        if (isHover(inputs.mouseX, inputs.mouseY)) {
            onHover()
            if (inputs.isMouseClicked) {
                onClick()
                isClicked = true
            }
        }
    }
}
