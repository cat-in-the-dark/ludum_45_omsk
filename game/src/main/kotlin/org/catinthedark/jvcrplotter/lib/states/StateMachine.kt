package org.catinthedark.itsadeal.lib.states

import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import org.slf4j.LoggerFactory

class StateMachine {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val states: MutableMap<String, IState> = hashMapOf()
    private val mixins: MutableMap<String, MutableList<() -> Unit>> = hashMapOf()
    private var currentState: String = ""

    fun putMixin(key: String, mixin: () -> Unit) {
        mixins.getOrPut(key, { mutableListOf() }).add(mixin)
    }

    fun putMixins(vararg keys: String, mixin: () -> Unit) {
        keys.forEach { putMixin(it, mixin) }
    }

    fun put(key: String, state: IState) {
        states[key] = state
    }

    fun putAll(vararg pairs: Pair<String, IState>) {
        pairs.forEach { put(it.first, it.second) }
    }

    fun onUpdate() {
        val state = IOC.atOr("state", "")
        if (state != currentState) {
            logger.info("Transition from $currentState to $state")
            states[currentState]?.onExit()
            states[state]?.onActivate()
            currentState = state
        }

        states[currentState]?.onUpdate() ?: logger.info("Unknown '$state'")
        mixins[currentState]?.forEach { it() }
    }
}
