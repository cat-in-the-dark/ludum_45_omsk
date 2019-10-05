package org.catinthedark.itsadeal.lib

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KProperty

object IOC {
    private val container: ConcurrentHashMap<String, Any?> = ConcurrentHashMap()

    fun put(name: String, obj: Any?) {
        container[name] = obj
    }

    fun get(name: String): Any? {
        return container[name]
    }

    inline operator fun <reified T> getValue(thisRef: Any?, property: KProperty<*>) =
        IOC.atOrFail<T>(property.name)

    inline operator fun <reified T> setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        put(property.name, value)
}

inline fun <reified T> IOC.at(name: String): T? {
    val obj = get(name)
    if (obj is T?) {
        return obj
    } else {
        throw ClassCastException("Object for $name is not a ${T::class.java.name}")
    }
}

inline fun <reified T> IOC.atOrFail(name: String): T {
    val obj = get(name) ?: throw NullPointerException("$name is null")
    if (obj is T) {
        return obj
    } else {
        throw ClassCastException("Object for $name is not a ${T::class.java.name}")
    }
}


inline fun <reified T> IOC.atOr(name: String, default: T): T {
    val obj = get(name)
    if (obj is T?) {
        return obj ?: default
    } else {
        throw ClassCastException("Object for $name is not a ${T::class.java.name}")
    }
}

inline fun <reified T> IOC.update(name: String, func: (T?) -> Any?) {
    put(name, func(at(name)))
}

inline fun <reified T> IOC.updateOrSkip(name: String, func: (T) -> Any?) {
    val value: T = at(name) ?: return
    put(name, func(value))
}

inline fun <reified T> IOC.updateOrFail(name: String, func: (T) -> Any?) {
    put(name, func(atOrFail(name)))
}

