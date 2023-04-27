package net.deechael.genshincraft.listeners

import net.deechael.genshincraft.util.listener.EventListener
import org.bukkit.event.player.PlayerDropItemEvent

object DropItemListener: EventListener<PlayerDropItemEvent>() {

    override fun event(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }

}