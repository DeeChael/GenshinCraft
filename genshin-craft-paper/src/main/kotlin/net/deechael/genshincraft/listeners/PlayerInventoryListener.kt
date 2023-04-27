package net.deechael.genshincraft.listeners

import net.deechael.genshincraft.GenshinCraft
import net.deechael.genshincraft.util.audience
import net.deechael.genshincraft.util.listener.EventListener
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.PlayerInventory

object PlayerInventoryListener: EventListener<InventoryClickEvent>() {

    /* This is used to make custom gui
     *
     * | Player Inventory
     * | Shop | Time Changer | Archive | Change characters | Friends | Multiplayer | Tutorials | Achievements |    |
     * | Events | Battle Pass | Gacha | Adventurer Handbook | Backpack | Characters | Map |    |    |
     * |    |    |    |    |    |    |    |    |    |
     */

    override fun event(event: InventoryClickEvent) {
        val player = filter { event.whoClicked as Player }
        filter(event.inventory is PlayerInventory)

        filter(event.rawSlot == 9) {
            shop(event, player)
        }



        event.isCancelled = true
    }

    fun shop(event: InventoryClickEvent, player: Player) {
        val menu = GenshinCraft.createMenu()
        val renderer = menu.add(player.audience())
            .title(Component.text("| Shop |")
                .decorate(TextDecoration.BOLD)
                .color(NamedTextColor.GOLD))
            .line(6)

    }

}