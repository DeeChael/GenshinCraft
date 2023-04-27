package net.deechael.genshincraft.util.listener

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.lang.RuntimeException

abstract class EventListener<T: Event>: Listener {
    
    @EventHandler
    fun receive(event: T) {
        try {
            this.event(event)
        } catch (_: FilterBreak) {
        }
    }

    abstract fun event(event: T)
    
    protected fun filter(boolean: Boolean) {
        if (!boolean)
            throw FilterBreak()
    }
    
    protected fun <E> filter(func: () -> E): E {
        try {
            return func()
        } catch (_: Exception) {
            throw FilterBreak()
        }
    }

    protected fun filter(boolean: Boolean, func: () -> Unit) {
        if (boolean)
            func()
    }
    
}

private class FilterBreak: RuntimeException() {
    
}