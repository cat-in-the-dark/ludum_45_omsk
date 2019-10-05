package org.catinthedark.itsadeal.lib

class TimeCache<out T>(
    private val func: () -> T,
    val syncTime: Long // in ms
) {
    private var lastSync: Long = 0
    private var cache: T? = null

    fun get(): T? {
        val now = System.currentTimeMillis()
        if (now - lastSync >= syncTime) {
            lastSync = now
            cache = func()
        }
        return cache
    }
}
