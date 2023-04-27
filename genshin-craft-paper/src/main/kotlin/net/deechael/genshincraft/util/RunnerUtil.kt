package net.deechael.genshincraft.util

import net.deechael.genshincraft.GenshinCraft
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.scheduler.BukkitRunnable

object RunnerUtil {

    fun run(runnable: BukkitRunnable, delay: Long) {
        runnable.runTaskLater(GenshinCraft.instance(), delay * 20L)
    }

    fun run(runnable: BukkitRunnable, async: Boolean) {
        if (async)
            runnable.runTaskAsynchronously(GenshinCraft.instance())
        else
            runnable.runTask(GenshinCraft.instance())
    }

    fun call(event: Event, async: Boolean) {
        run(object : BukkitRunnable() {
            override fun run() {
                Bukkit.getPluginManager().callEvent(event)
            }
        }, async)
    }

}