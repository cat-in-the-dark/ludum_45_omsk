package org.catinthedark.itsadeal.lib

import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

interface Deffer {
    fun register(time: Float, func: () -> Unit): Long

    fun unregister(index: Long)

    fun update(time: Float)
}

class NoDeffer : Deffer {
    override fun unregister(index: Long) {
    }

    override fun update(time: Float) {
    }

    override fun register(time: Float, func: () -> Unit): Long {
        func()
        return 0
    }
}

class DefferImpl : Deffer {
    private val funcs: ConcurrentHashMap<Long, Func> = ConcurrentHashMap()
    private var index: Long = 0L

    private val log = LoggerFactory.getLogger(Deffer::class.java)

    override fun register(time: Float, func: () -> Unit): Long {
        funcs[index++] = Func(func, time)
        return index
    }

    override fun unregister(index: Long) {
        funcs.remove(index)
    }

    override fun update(time: Float) {
        funcs.forEach {
            it.value.time -= time

            if (it.value.time <= 0) {
                try {
                    it.value.func()
                } catch (e: Exception) {
                    log.error("Can't call deffer func: ${e.message}", e)
                }
                funcs.remove(it.key)
            }
        }
    }

    data class Func(
        val func: () -> Unit,
        var time: Float
    )
}
