package net.deechael.genshincraft.listeners

import net.deechael.genshincraft.GenshinCraft
import net.deechael.genshincraft.util.audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.PlayerInventory

object PlayerInventoryListener : Listener {

    /* This is used to make custom gui
     *
     * | Player Inventory
     * | Shop | Time Changer | Archive | Change characters | Friends | Multiplayer | Tutorials | Achievements |    |
     * | Events | Battle Pass | Gacha | Adventurer Handbook | Backpack | Characters | Map |    |    |
     * |    |    |    |    |    |    |    |    |    |
     */

    @EventHandler
    fun event(event: InventoryClickEvent) {
        if (event.whoClicked !is Player)
            return
        val player = event.whoClicked as Player
        if (event.inventory !is PlayerInventory)
            return

        if (event.rawSlot == 9) {
            shop(event, player)
        } else if (event.rawSlot == 10) {

        }

        event.isCancelled = true
    }

    fun shop(event: InventoryClickEvent, player: Player) {
        val menu = GenshinCraft.createMenu()
        val renderer = menu.add(player.audience())
            .title(
                Component.text("| Shop |")
                    .decorate(TextDecoration.BOLD)
                    .color(NamedTextColor.GOLD)
            )
            .line(6)

        renderer.render()
    }

}