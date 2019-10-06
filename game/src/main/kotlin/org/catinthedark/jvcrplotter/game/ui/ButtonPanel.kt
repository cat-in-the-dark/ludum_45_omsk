package org.catinthedark.jvcrplotter.game.ui

import com.badlogic.gdx.graphics.g2d.Batch
import org.catinthedark.jvcrplotter.game.Assets

class ButtonPanel(private val onPressed: (instruction: String, update: Boolean) -> Unit = { s: String, b: Boolean -> }) {
    private val startX = 665
    private val startY = 235
    private val deltaX = 90
    private val deltaY = 50

    private val movButton = CompositeButton(toX(0), toY(0), Assets.Names.BUTTON, "MOV", {
        onPressed("MOV", false)
    })
    private val addButton = CompositeButton(toX(1), toY(0), Assets.Names.BUTTON, "ADD", {
        onPressed("ADD", false)
    })
    private val mulButton = CompositeButton(toX(2), toY(0), Assets.Names.BUTTON, "MUL", {
        onPressed("MUL", false)
    })
    private val cmpButton = CompositeButton(toX(0), toY(1), Assets.Names.BUTTON, "CMP", {
        onPressed("CMP", false)
    })
    private val jgButton = CompositeButton(toX(1), toY(1), Assets.Names.BUTTON, "J >", {
        onPressed("JG", false)
    })
    private val jeButton = CompositeButton(toX(2), toY(1), Assets.Names.BUTTON, "J =", {
        onPressed("JE", false)
    })
    private val jlButton = CompositeButton(toX(3), toY(1), Assets.Names.BUTTON, "J <", {
        onPressed("JL", false)
    })
    private val jmpButton = CompositeButton(toX(4), toY(1), Assets.Names.BUTTON, "JMP", {
        onPressed("JMP", false)
    })

    private val oneButton = CompositeButton(toX(0), toY(0), Assets.Names.BUTTON, "1", {
        onPressed("1", true)
    })
    private val twoButton = CompositeButton(toX(1), toY(0), Assets.Names.BUTTON, "2", {
        onPressed("2", true)
    })
    private val threeButton = CompositeButton(toX(2), toY(0), Assets.Names.BUTTON, "3", {
        onPressed("3", true)
    })
    private val minusButton = CompositeButton(toX(3), toY(0), Assets.Names.BUTTON, "-", {
        onPressed("-", true)
    })
    private val fourButton = CompositeButton(toX(0), toY(1), Assets.Names.BUTTON, "4", {
        onPressed("4", true)
    })
    private val fiveButton = CompositeButton(toX(1), toY(1), Assets.Names.BUTTON, "5", {
        onPressed("5", true)
    })
    private val sixButton = CompositeButton(toX(2), toY(1), Assets.Names.BUTTON, "6", {
        onPressed("6", true)
    })
    private val zeroButton = CompositeButton(toX(3), toY(1), Assets.Names.BUTTON, "0", {
        onPressed("0", true)
    })
    private val sevenButton = CompositeButton(toX(0), toY(2), Assets.Names.BUTTON, "7", {
        onPressed("7", true)
    })
    private val eightButton = CompositeButton(toX(1), toY(2), Assets.Names.BUTTON, "8", {
        onPressed("8", true)
    })
    private val nineButton = CompositeButton(toX(2), toY(2), Assets.Names.BUTTON, "9", {
        onPressed("9", true)
    })
    private val xButton = CompositeButton(toX(4), toY(0), Assets.Names.BUTTON, "X", {
        onPressed("X", false)
    })
    private val yButton = CompositeButton(toX(4), toY(1), Assets.Names.BUTTON, "Y", {
        onPressed("Y", false)
    })
    private val aButton = CompositeButton(toX(4), toY(2), Assets.Names.BUTTON, "A", {
        onPressed("A", false)
    })
    private val unsetPlotButton = IconButton(toX(0), toY(2), Assets.Names.BUTTON, Assets.Names.ICON_UNSET_PLOT, {
        onPressed("INT", false)
        onPressed("42", false)
        onPressed("\n", false)
    })
    private val setPlotButton = IconButton(toX(1), toY(2), Assets.Names.BUTTON, Assets.Names.ICON_SET_PLOT, {
        onPressed("INT", false)
        onPressed("43", false)
        onPressed("\n", false)
    })
    private val movePlotButton = IconButton(toX(2), toY(2), Assets.Names.BUTTON, Assets.Names.ICON_MOVE, {
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

    private fun toX(index: Int): Int {
        return startX + index * deltaX
    }

    private fun toY(index: Int): Int {
        return startY - index * deltaY
    }

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
