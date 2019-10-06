package org.catinthedark.jvcrplotter.game

import com.badlogic.gdx.graphics.Color

public fun Color.toAbgr(): Int {
    return (255 * a).toInt() or ((255 * b).toInt() shl 8) or ((255 * g).toInt() shl 16) or ((255 * r).toInt() shl 24)
}
