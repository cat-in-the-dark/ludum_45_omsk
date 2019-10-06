package org.catinthedark.jvcrplotter.game.plotter

import com.badlogic.gdx.graphics.Color

data class PlotState(
    val vram: PlotVRAM,
    var isPointerUp: Boolean = true,
    var pencilColor: Color = Color.BLUE
)

data class PlotVRAM(
    val width: Int,
    val height: Int
) {
    var currentX: Int = 0
        private set
    var currentY: Int = 0
        private set
    private val vram: List<MutableList<Color>> = List(width) {
        MutableList(height) {
            Color(0f, 0f, 0f, 0f)
        }
    }

    fun set(x: Int, y: Int, c: Color) {
        currentX = x.coerceIn(0, width - 1)
        currentY = y.coerceIn(0, height - 1)
        vram[currentX][currentY] = c
    }

    fun set(x: Int, y: Int) {
        currentX = x.coerceIn(0, width - 1)
        currentY = y.coerceIn(0, height - 1)
    }

    fun getC(x: Int, y: Int): Color {
        return vram[x.coerceIn(0, width - 1)][y.coerceIn(0, height - 1)]
    }

    fun get(x: Int, y: Int): Color {
        return getC(x, y)
    }
}

fun PlotVRAMFromRaw(raw: List<List<Int>>, color: Color): PlotVRAM {
    val vram = PlotVRAM(raw.size, raw[0].size)
    for (i in raw.indices) {
        for (j in raw[0].indices) {
            if (raw[i][j] != 0) {
                vram.set(i, j, color)
            }
        }
    }
    return vram
}
