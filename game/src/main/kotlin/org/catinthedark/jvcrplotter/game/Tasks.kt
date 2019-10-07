package org.catinthedark.jvcrplotter.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

private fun toMatrix(l: List<Int>, w: Int, h: Int): List<List<Int>> {
    val m = List(w) { MutableList(h) { 0 } }
    for (i in 0 until w) {
        for (j in 0 until h) {
            m[i][j] = l[i + j * w]
        }
    }

    return m
}

object Tasks {
    val taskTextures = listOf(
        Assets.Names.TASK_1,
        Assets.Names.TASK_2,
        Assets.Names.TASK_3,
        Assets.Names.TASK_4,
        Assets.Names.TASK_5,
        Assets.Names.TASK_6,
        Assets.Names.TASK_7,
        Assets.Names.TASK_8
    )

    fun createTask(am: AssetManager, taskId: Int): List<List<Int>> {
        val taskTexture = taskTextures[taskId % taskTextures.size]
        val taskTextureData = am.at<Texture>(taskTexture).textureData
        if (!taskTextureData.isPrepared) {
            taskTextureData.prepare()
        }

        val taskPixmap = taskTextureData.consumePixmap()
        assert(taskPixmap.width == Const.Plotter.WIDTH)
        assert(taskPixmap.height == Const.Plotter.HEIGHT)

        val pixels = mutableListOf<Int>()

        for (y in 0 until taskPixmap.height) {
            for (x in 0 until taskPixmap.width) {
                // RGBA8888 color of the pixel
                pixels += if (taskPixmap.getPixel(x, y) and 0xFFFFFF00.toInt() == 0) {
                    1
                } else {
                    0
                }
            }
        }

        taskPixmap.dispose()

        return toMatrix(pixels, Const.Plotter.WIDTH, Const.Plotter.HEIGHT)
    }
}
