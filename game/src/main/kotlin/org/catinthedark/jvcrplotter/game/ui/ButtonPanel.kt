package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.graphics.g2d.Batch
import org.catinthedark.jvcrplotter.game.Assets

class ButtonPanel(private val onPressed: (instruction: String, update: Boolean) -> Unit = { s: String, b: Boolean -> }) {
    private val movButton = CompositeButton(640, 220, Assets.Names.BUTTON, "MOV", {
        onPressed("MOV", false)
    })
    private val addButton = CompositeButton(730, 220, Assets.Names.BUTTON, "ADD", {
        onPressed("ADD", false)
    })
    private val mulButton = CompositeButton(820, 220, Assets.Names.BUTTON, "MUL", {
        onPressed("MUL", false)
    })
    private val cmpButton = CompositeButton(640, 160, Assets.Names.BUTTON, "CMP", {
        onPressed("CMP", false)
    })
    private val jgButton = CompositeButton(730, 160, Assets.Names.BUTTON, "J >", {
        onPressed("JG", false)
    })
    private val jeButton = CompositeButton(820, 160, Assets.Names.BUTTON, "J =", {
        onPressed("JE", false)
    })
    private val jlButton = CompositeButton(910, 160, Assets.Names.BUTTON, "J <", {
        onPressed("JL", false)
    })
    private val jmpButton = CompositeButton(1000, 160, Assets.Names.BUTTON, "JMP", {
        onPressed("JMP", false)
    })

    private val oneButton = CompositeButton(640, 220, Assets.Names.BUTTON, "1", {
        onPressed("1", true)
    })
    private val twoButton = CompositeButton(730, 220, Assets.Names.BUTTON, "2", {
        onPressed("2", true)
    })
    private val threeButton = CompositeButton(820, 220, Assets.Names.BUTTON, "3", {
        onPressed("3", true)
    })
    private val minusButton = CompositeButton(910, 220, Assets.Names.BUTTON, "-", {
        onPressed("-", true)
    })
    private val fourButton = CompositeButton(640, 160, Assets.Names.BUTTON, "4", {
        onPressed("4", true)
    })
    private val fiveButton = CompositeButton(730, 160, Assets.Names.BUTTON, "5", {
        onPressed("5", true)
    })
    private val sixButton = CompositeButton(820, 160, Assets.Names.BUTTON, "6", {
        onPressed("6", true)
    })
    private val zeroButton = CompositeButton(910, 160, Assets.Names.BUTTON, "0", {
        onPressed("0", true)
    })
    private val sevenButton = CompositeButton(640, 100, Assets.Names.BUTTON, "7", {
        onPressed("7", true)
    })
    private val eightButton = CompositeButton(730, 100, Assets.Names.BUTTON, "8", {
        onPressed("8", true)
    })
    private val nineButton = CompositeButton(820, 100, Assets.Names.BUTTON, "9", {
        onPressed("9", true)
    })
    private val xButton = CompositeButton(1000, 220, Assets.Names.BUTTON, "X", {
        onPressed("X", false)
    })
    private val yButton = CompositeButton(1000, 160, Assets.Names.BUTTON, "Y", {
        onPressed("Y", false)
    })
    private val aButton = CompositeButton(1000, 100, Assets.Names.BUTTON, "A", {
        onPressed("A", false)
    })
    private val unsetPlotButton = IconButton(640, 100, Assets.Names.BUTTON, Assets.Names.ICON_UNSET_PLOT, {
        onPressed("INT", false)
        onPressed("42", false)
        onPressed("\n", false)
    })
    private val setPlotButton = IconButton(730, 100, Assets.Names.BUTTON, Assets.Names.ICON_SET_PLOT, {
        onPressed("INT", false)
        onPressed("43", false)
        onPressed("\n", false)
    })
    private val movePlotButton = IconButton(820, 100, Assets.Names.BUTTON, Assets.Names.ICON_MOVE, {
        onPressed("INT", false)
        onPressed("44", false)
        onPressed("\n", false)
    })

    private val instructionButtons = listOf(
        mulButton,
        addButton,
        cmpButton,
        jlButton,
        jeButton,
        jgButton,
        movButton,
        jmpButton,
        unsetPlotButton,
        setPlotButton,
        movePlotButton
    )
    private val operandsButtons = listOf(
        xButton,
        yButton,
        aButton,
        zeroButton,
        oneButton,
        twoButton,
        threeButton,
        fourButton,
        fiveButton,
        sixButton,
        sevenButton,
        eightButton,
        nineButton,
        minusButton
    )

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
            0 -> instructionButtons.forEach {
                it.draw(batch)
            }
            else -> operandsButtons.forEach {
                it.draw(batch)
            }
        }
    }
}
