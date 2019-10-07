package org.catinthedark.jvcrplotter.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle

object Const {
    object Screen {
        const val WIDTH = 1280
        const val HEIGHT = 720
        const val ZOOM = 1f
    }

    object Plotter {
        const val WIDTH = 32
        const val HEIGHT = 32
        val COLOR = Color(0x00d410ff)
    }

    object Balance {

    }

    object UI {
        val ninePatchButtonRect = Rectangle(20f, 15f, 20f, 15f)
        const val buttonHeight = 39
        const val buttonWidth = 70
        const val maxLinesOnScreen = 14
    }
}
