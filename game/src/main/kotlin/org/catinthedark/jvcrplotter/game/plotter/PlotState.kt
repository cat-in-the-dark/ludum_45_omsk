package org.catinthedark.jvcrplotter.game.plotter

data class Color(
    val R: Int,
    val G: Int,
    val B: Int,
    val A: Int = 255
)

val BLACK_COLOR = Color(0, 0, 0, 255)

data class PlotState(
    val vram: PlotVRAM,
    var isPointerUp: Boolean = true,
    var color: Color = BLACK_COLOR
)

data class PlotVRAM(
    val width: Int,
    val height: Int
) {
    var currentX: Int = 0
        private set
    var currentY: Int = 0
        private set
    private val vram: List<MutableList<Color>> = List(width) { MutableList(height) { BLACK_COLOR } }

    fun set(x: Int, y: Int, c: Color) {
        currentX = x.coerceIn(0, width - 1)
        currentY = y.coerceIn(0, height - 1)
        vram[currentX][currentY] = c
    }

    fun set(x: Int, y: Int) {
        currentX = x.coerceIn(0, width - 1)
        currentY = y.coerceIn(0, height - 1)
    }

    fun get(x: Int, y: Int): Color {
        return vram[x.coerceIn(0, width - 1)][y.coerceIn(0, height - 1)]
    }
}
