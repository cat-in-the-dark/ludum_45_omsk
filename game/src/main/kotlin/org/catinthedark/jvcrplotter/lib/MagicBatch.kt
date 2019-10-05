package org.catinthedark.itsadeal.lib

import com.badlogic.gdx.graphics.g2d.Batch

fun Batch.managed(block: (batch: Batch) -> Unit) {
    begin()
    block(this)
    end()
}
