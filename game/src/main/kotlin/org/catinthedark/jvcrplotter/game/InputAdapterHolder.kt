package org.catinthedark.jvcrplotter.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import org.slf4j.LoggerFactory
import kotlin.math.roundToInt

class InputAdapterHolder(
    private val stage: Stage
) : InputAdapter() {
    private val logger = LoggerFactory.getLogger(javaClass)
    var isMouseClicked = false
    var mouseX: Int = -1
    var mouseY: Int = -1

    fun update() {
        isMouseClicked = false
        updateMousePos()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer == Input.Buttons.LEFT) {
            isMouseClicked = true
            updateMousePos()
            logger.info("x=$mouseX, y=$mouseY")
        }

        return true
    }

    private fun updateMousePos(pointer: Int = 0) {
        val vec = Vector3(
            Gdx.input.getX(pointer).toFloat(),
            Gdx.input.getY(pointer).toFloat(),
            0f
        )
        stage.viewport.unproject(vec)
        mouseX = vec.x.roundToInt()
        mouseY = vec.y.roundToInt()
    }
}
