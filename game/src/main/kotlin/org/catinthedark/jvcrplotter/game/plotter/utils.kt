package org.catinthedark.jvcrplotter.game.plotter

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import org.catinthedark.jvcrplotter.game.Const
import org.catinthedark.jvcrplotter.game.toAbgr

fun createPlot(vram: PlotVRAM, background: Color = Color.BLACK): Texture {
    val pixmap = Pixmap(
        Const.Plotter.WIDTH, Const.Plotter.HEIGHT, Pixmap.Format.RGBA8888
    )
    pixmap.setColor(background)
    pixmap.fill()
    for (x in 0 until vram.width) {
        for (y in 0 until vram.height) {
            pixmap.drawPixel(x, y, vram.get(x, y).toAbgr())
        }
    }

    val tex = Texture(pixmap)
    pixmap.dispose()
    return tex
}

fun createPlot(raw: List<List<Int>>, background: Color = Color.BLACK): Texture {
    val vram = PlotVRAMFromRaw(raw, Const.Plotter.COLOR)
    return createPlot(vram, background)
}
