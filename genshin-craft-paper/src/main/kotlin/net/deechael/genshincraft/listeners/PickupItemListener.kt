package net.deechael.genshincraft.listeners

import net.deechael.genshincraft.util.listener.EventListener
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent

object PickupItemListener: Listener {

    @EventHandler
    fun event(event: EntityPickupItemEvent) {
        if (event.entity is Player)
            return
        val player = event.entity as Player
        if (player.gameMode != GameMode.ADVENTURE)
            return

        val stack = event.item.itemStack.clone()

        event.isCancelled = true
        event.item.remove()

        // TODO: make the stack into player's virtual inventory
    }

}