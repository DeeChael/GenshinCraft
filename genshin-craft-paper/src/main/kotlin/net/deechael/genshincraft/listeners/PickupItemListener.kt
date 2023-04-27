package net.deechael.genshincraft.listeners

import net.deechael.genshincraft.util.listener.EventListener
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent

object PickupItemListener: EventListener<EntityPickupItemEvent>() {

    override fun event(event: EntityPickupItemEvent) {
        val player = filter { return@filter event.entity as Player }
        filter(player.gameMode == GameMode.ADVENTURE)

        val stack = event.item.itemStack.clone()

        event.isCancelled = true
        event.item.remove()

        // TODO: make the stack into player's virtual inventory
    }

}