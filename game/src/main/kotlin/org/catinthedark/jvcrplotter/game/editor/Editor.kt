package org.catinthedark.jvcrplotter.game.editor

import org.slf4j.LoggerFactory

class Editor(private val widthInBlocks: Int) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val contents: MutableList<MutableList<String>> = mutableListOf(mutableListOf("", "", ""))
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
            contents.add(cursorY, mutableListOf("", "", ""))
        }

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
}
