package org.catinthedark.jvcrplotter.game.editor

import org.catinthedark.jvcrplotter.game.asm.*
import org.catinthedark.jvcrplotter.game.asm.exceptions.InvalidInstructionException
import org.catinthedark.jvcrplotter.game.asm.ops.*
import org.slf4j.LoggerFactory

class Editor(private val widthInBlocks: Int) {
    constructor(contents: MutableList<MutableList<String>>) : this(3) {
        this.contents = contents
    }

    private val logger = LoggerFactory.getLogger(javaClass)
    private var contents: MutableList<MutableList<String>> = mutableListOf(mutableListOf("", "", ""))
    private var cursorX = 0
    private var cursorY = 0

    fun moveCursorUp() {
        val oldY = cursorY
        if (cursorY > 0) {
            cursorY -= 1
        }
        logger.info("move up $oldY -> $cursorY")
    }

    fun moveCursorDown() {
        val oldY = cursorY
        if (cursorY < contents.size - 1) {
            cursorY += 1
        }

        logger.info("move down $oldY -> $cursorY")
    }

    fun moveCursorLeft() {
        val oldX = cursorX
        if (cursorX > 0) {
            cursorX -= 1
        }

        logger.info("move left $oldX -> $cursorX")
    }

    fun moveCursorRight() {
        val oldX = cursorX
        if (cursorX < widthInBlocks - 1) {
            cursorX += 1
        }

        logger.info("move right $oldX -> $cursorX")
    }

    /**
     * return cursor position
     * first = X (column), second = Y (row)
     */
    fun getCursorPosition(): Pair<Int, Int> {
//        logger.info("Get position: $cursorX:$cursorY")
        return Pair(cursorX, cursorY)
    }

    fun clearSymbolUnderCursor() {
        logger.info("clear symbol at $cursorX:$cursorY (was ${contents[cursorY][cursorX]})")
        setSymbolUnderCursor("")
    }

    fun insertNewLine() {
        logger.info("add line at $cursorY")
        if (cursorY == contents.size - 1) {
            contents.add(mutableListOf("", "", ""))
        } else {
            contents.add(cursorY + 1, mutableListOf("", "", ""))
        }

        cursorY += 1
        cursorX = 0

        logger.info("total lines: ${contents.size}")
    }

    fun getSymbolUnderCursor(): String {
        val res = contents[cursorY][cursorX]
        logger.info("get under $cursorX:$cursorY: $res")
        return res
    }

    fun setSymbolUnderCursor(s: String) {
        logger.info("set symbol at $cursorX:$cursorY to $s (was ${contents[cursorY][cursorX]})")
        contents[cursorY][cursorX] = s
    }

    fun getRows(): List<List<String>> {
//        logger.info("get contents")
        return contents
    }

    fun deleteLine() {
        if (contents.size > 1) {
            contents.removeAt(cursorY)
            logger.info("delete ${cursorY}th line")
            if (cursorY >= contents.size) {
                cursorY = contents.size - 1
            }
        }
    }

    fun toInstructions(): List<Operation> {
        filterEmptyLines()
        val operations = mutableListOf<Operation>()
        for (y in 0 until contents.size) {
            val val1 = toValue(1, y)
            val val2 = toValue(2, y)
            val op = toOperation(0, y, val1, val2)
            operations += op
        }

        return operations
    }

    private fun filterEmptyLines() {
        contents = contents.filter { line ->
            line.any { it.isNotEmpty() }
        }.toMutableList()
    }

    fun setCursorPosition(x: Int, y: Int) {
        if (x in 0 until widthInBlocks) {
            cursorX = x
        }
        if (y in 0 until contents.size) {
            cursorY = y
        }
    }

    private fun toOperation(x: Int, y: Int, val1: Value?, val2: Value?): Operation {
        cursorX = x
        cursorY = y
        return when (val opcode = getSymbolUnderCursor()) {
            "ADD" -> AddOp(toMutableOrFail(val1), getOrFail(val2))
            "CMP" -> CmpOp(getOrFail(val1), getOrFail(val2))
            "INT" -> {
                checkNull(val2)
                return IntOp(getOrFail(val1))
            }
            "JE" -> {
                checkNull(val2)
                return JeOp(getOrFail(val1))
            }
            "JG" -> {
                checkNull(val2)
                return JgOp(getOrFail(val1))
            }
            "JL" -> {
                checkNull(val2)
                return JlOp(getOrFail(val1))
            }
            "JMP" -> {
                checkNull(val2)
                return JmpOp(getOrFail(val1))
            }
            "MOV" -> MovOp(toMutableOrFail(val1), getOrFail(val2))
            "MUL" -> MulOp(toMutableOrFail(val1), getOrFail(val2))
            else -> throw InvalidInstructionException("ЕГГОГ\nUnsupporteд iпstruction!")
        }
    }

    private fun checkNull(operand: Value?) {
        if (operand != null) {
            throw InvalidInstructionException("ЕГГОГ\nЯedundant operand!")
        }
    }

    private fun getOrFail(operand: Value?): Value {
        return operand ?: throw InvalidInstructionException("ЕГГОГ\ninvaliд operand!")
    }

    private fun toMutableOrFail(operand: Value?): MutableValue {
        if (operand == null) {
            throw InvalidInstructionException("ЕГГОГ\ninvaliд operand!")
        }
        if (operand is MutableValue) {
            return operand
        } else {
            throw InvalidInstructionException("ЕГГОГ\ninvaliд operand type!")
        }
    }

    private fun toValue(x: Int, y: Int): Value? {
        cursorX = x
        cursorY = y

        return when (val valueStr = getSymbolUnderCursor()) {
            "X" -> ValueRegister(X)
            "Y" -> ValueRegister(Y)
            "A" -> ValueRegister(A)
            "B" -> ValueRegister(B)
            "" -> null
            else -> {
                try {
                    ValueLiteral(valueStr.toInt())
                } catch (e: NumberFormatException) {
                    throw InvalidInstructionException("ЕГГОГ\nInvaliд address!")
                }
            }
        }
    }

    fun appendNumberUnderCursor(instruction: Char) {
        val currentVal = contents[cursorY][cursorX]
        if (currentVal.isEmpty()) {
            contents[cursorY][cursorX] = instruction.toString()
        } else {
            try {
                if (currentVal != "-") {
                    currentVal.toInt()
                }

                when (instruction) {
                    in '0'..'9' -> {
                        if (currentVal.length <= 3) {
                            contents[cursorY][cursorX] = currentVal + instruction
                        }
                    }
                    else -> contents[cursorY][cursorX] = instruction.toString()
                }
            } catch (e: Exception) {
                contents[cursorY][cursorX] = instruction.toString()
            }
        }
    }
}
