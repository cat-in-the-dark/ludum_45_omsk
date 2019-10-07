package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.graphics.g2d.Batch

abstract class IDialog : IButton {
    abstract override fun draw(batch: Batch)

    abstract override fun update()

    fun updateAndDraw(batch: Batch) {
        draw(batch)
        update()
    }
}
