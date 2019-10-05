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
    private val vram: List<MutableList<Color>> = List(width) { MutableList(height) { BLACK_COLOR } }

    fun draw(x: Int, y: Int, c: Color) {
        vram[x.coerceIn(0, width - 1)][y.coerceIn(0, height - 1)] = c
    }

    fun get(x: Int, y: Int): Color {
        return vram[x.coerceIn(0, width - 1)][y.coerceIn(0, height - 1)]
    }
}
