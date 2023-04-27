package net.deechael.genshincraft.util.plugin

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

open class GenshinPlugin(): JavaPlugin() {

    fun listener(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, this)
    }

}