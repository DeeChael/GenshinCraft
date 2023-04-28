package net.deechael.genshincraft.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

object DropItemListener: Listener {

    @EventHandler
    fun event(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }

}