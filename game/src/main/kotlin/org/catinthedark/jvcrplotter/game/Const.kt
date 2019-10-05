package org.catinthedark.itsadeal.game

import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import kotlin.math.max

object Const {
    object Screen {
        const val WIDTH = 256
        const val HEIGHT = 144
        const val ZOOM = 1f

        const val WIDTH_BIG = 1024
        const val HEIGHT_BIG = 576
        const val ZOOM_BIG = 1f
    }

    object Projection {
        const val ratio = Screen.WIDTH_BIG.toFloat() / Screen.WIDTH.toFloat()
        fun toHud(pos: Float) = pos * ratio
        fun Float.tohud() = this * ratio
    }

    object Balance {

    }
}
