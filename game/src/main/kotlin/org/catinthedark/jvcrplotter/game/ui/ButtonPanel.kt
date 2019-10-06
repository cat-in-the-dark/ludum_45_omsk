package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.graphics.g2d.Batch
import org.catinthedark.jvcrplotter.game.Assets

class ButtonPanel(private val onPressed: (instruction: String) -> Unit = {}) {
    private val mulButton = CompositeButton(640, 220, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "MUL", {
        onPressed("MUL")
    })
    private val addButton = CompositeButton(730, 220, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "ADD", {
        onPressed("ADD")
    })
    private val cmpButton = CompositeButton(820, 220, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "CMP", {
        onPressed("CMP")
    })
    private val intButton = CompositeButton(910, 220, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "INT", {
        onPressed("INT")
    })
    private val jeButton = CompositeButton(1000, 220, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "JE", {
        onPressed("JE")
    })
    private val jgButton = CompositeButton(640, 160, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "JG", {
        onPressed("JG")
    })
    private val xButton = CompositeButton(730, 160, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "X", {
        onPressed("X")
    })
    private val yButton = CompositeButton(820, 160, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "Y", {
        onPressed("Y")
    })
    private val movButton = CompositeButton(910, 160, Assets.Names.BUTTON, Assets.Names.BUTTON_INACTIVE, "MOV", {
        onPressed("MOV")
    })

    private val instructionButtons = listOf(mulButton, addButton, cmpButton, intButton, jeButton, jgButton, movButton)
    private val operandsButtons = listOf(xButton, yButton)

    fun updateButtons(editorXPos: Int) {
        when (editorXPos) {
            0 -> instructionButtons.forEach {
                it.update()
            }
            else -> operandsButtons.forEach {
                it.update()
            }
        }
    }

    fun drawButtons(batch: Batch, editorXPos: Int) {
        when (editorXPos) {
            0 -> {
                instructionButtons.forEach {
                    it.drawActive(batch)
                }
                operandsButtons.forEach {
                    it.drawInactive(batch)
                }
            }
            else -> {
                instructionButtons.forEach {
                    it.drawInactive(batch)
                }
                operandsButtons.forEach {
                    it.drawActive(batch)
                }
            }
        }
    }
}
