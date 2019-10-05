package org.catinthedark.itsadeal.lib

import com.badlogic.gdx.Gdx

private fun delta() = Gdx.graphics.deltaTime

interface ITimeBarrier {
    operator fun invoke(func: () -> Unit)
    fun reset()
}


class OnceBarrier(
    private val after: Float
) : ITimeBarrier {
    private var time = 0f
    private var called = false

    override fun invoke(func: () -> Unit) {
        time += delta()

        if (time >= after && !called) {
            called = true
            func()
        }
    }

    override fun reset() {
        time = 0f
        called = false
    }
}

class AfterBarrier(
    private val after: Float
) : ITimeBarrier {
    private var time = 0f

    override fun invoke(func: () -> Unit) {
        time += delta()
        if (time >= after) func()
    }

    override fun reset() {
        time = 0f
    }
}

class RepeatBarrier(
    private val after: Float,
    private val interval: Float = after
) : ITimeBarrier {
    private var time = 0f
    private var called = false

    override fun invoke(func: () -> Unit) {
        time += delta()
        if (called) {
            if (time >= interval) {
                time = 0f
                func()
            }
        } else {
            if (time >= after) {
                time = 0f
                func()
                called = true
            }
        }

    }

    override fun reset() {
        time = 0f
        called = false
    }
}
