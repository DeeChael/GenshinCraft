package net.deechael.genshincraft.util

import net.deechael.sudo.Audience
import net.deechael.sudo.impl.AudienceImpl
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerUtil {

    private val SCREEN_CLEANER = Component.text("    ").appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()
        .append(Component.text("    ")).appendNewline()

    fun clearScreen(player: Player) {
        player.sendMessage(SCREEN_CLEANER)
    }

}

private val audienceMap: MutableMap<Player, Audience> = mutableMapOf()

fun Player.audience(): Audience {
    return audienceMap.getOrPut(this) { AudienceImpl(this) }
}

object AudienceManager : Listener {

    @EventHandler
    fun quit(quitEvent: PlayerQuitEvent) {
        audienceMap.remove(quitEvent.player)
    }

}