package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.graphics.g2d.Batch

interface IButton {
    fun draw(batch: Batch)
    fun update()
}
