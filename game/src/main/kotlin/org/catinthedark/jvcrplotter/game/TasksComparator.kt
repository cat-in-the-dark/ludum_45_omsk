package org.catinthedark.jvcrplotter.game

import org.catinthedark.jvcrplotter.game.plotter.PlotVRAM

fun PlotVRAM.check(matrix: List<List<Int>>): Boolean {
    for (i in 0 until width) {
        for (j in 0 until height) {
            val color = getC(i, j)
            val c = if (color.r > 0 || color.b > 0 || color.g > 0) {
                1
            } else {
                0
            }
            if (matrix[i][j] != c) {
                return false
            }
        }
    }
    return true
}
