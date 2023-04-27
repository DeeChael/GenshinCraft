package net.deechael.genshincraft.listeners

import net.deechael.genshincraft.util.listener.EventListener
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView

object MinimapListener: EventListener<PlayerMoveEvent>() {

    override fun event(event: PlayerMoveEvent) {
        val mapItemStack = ItemStack(Material.FILLED_MAP)
        val mapItemMeta = mapItemStack.itemMeta as MapMeta
        val mapView = mapItemMeta.mapView!!
        mapView.scale = MapView.Scale.FARTHEST

    }

}