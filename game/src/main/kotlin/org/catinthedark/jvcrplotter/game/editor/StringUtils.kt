package org.catinthedark.jvcrplotter.game.editor

object StringUtils {
    fun String.insertPeriodically(
        insert: String, period: Int
    ): String {
        val builder = StringBuilder(
            this.length + insert.length * (this.length / period) + 1
        )

        var index = 0
        var prefix = ""
        while (index < this.length) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix)
            prefix = insert
            builder.append(
                this.substring(
                    index, Math.min(index + period, this.length)
                )
            )
            index += period
        }
        return builder.toString()
    }
}
