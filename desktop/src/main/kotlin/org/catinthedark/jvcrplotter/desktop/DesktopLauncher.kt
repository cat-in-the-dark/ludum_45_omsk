package org.catinthedark.jvcrplotter.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.catinthedark.jvcrplotter.game.ItsaDealGame

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true")
        LwjglApplication(ItsaDealGame(), LwjglApplicationConfiguration().apply {
            title = "JVCR Plotter"
            width = 1024
            height = 576
        })
    }
}
