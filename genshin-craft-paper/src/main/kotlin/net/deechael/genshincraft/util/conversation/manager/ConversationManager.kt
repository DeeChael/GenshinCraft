package net.deechael.genshincraft.util.conversation.manager

import net.deechael.conversation.api.Conversation
import net.deechael.conversation.api.Node
import net.deechael.conversation.event.ConversationEndEvent
import net.deechael.conversation.event.SwitchNodeEvent
import net.deechael.conversation.impl.event.ChoiceEventImpl
import net.deechael.genshincraft.util.PlayerUtil
import net.deechael.genshincraft.util.RunnerUtil
import net.deechael.genshincraft.util.StringUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

object ConversationManager : Listener {


    private val QUEUE: MutableMap<String, Node> = HashMap()
    private val PLAYER: MutableMap<UUID, ConversationData> = HashMap()

    @EventHandler
    fun event(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        var command = event.message
        if (command.startsWith("/")) command = command.substring(1)
        if (!command.startsWith("conversation_button_handler ")) return
        event.isCancelled = true
        if (!PLAYER.containsKey(player.uniqueId)) return
        command = command.trim { it <= ' ' }
        if (StringUtil.count(command, " ") < 2) return
        val split = command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val queueId = split[1]
        if (!QUEUE.containsKey(queueId)) return
        val data = PLAYER[player.uniqueId]
        if (queueId != data!!.queueId) return
        if (!StringUtil.isInteger(split[2])) return
        val buttonIndex = split[2].toInt()
        val node = QUEUE[queueId]
        val nodes = node!!.sub()
        if (buttonIndex < 0 || buttonIndex >= nodes.size) return
        val nextNode = nodes[buttonIndex]
        val button = nextNode.button()
        if (button == null) {
            clearPlayerData(player, ConversationEndEvent.Reason.BAD_DATA)
            return
        }
        val executor = button.executor()
        executor?.accept(ChoiceEventImpl(data.conversation, node, button, player))
        nextNode(player, data.conversation, nextNode)
    }

    @EventHandler
    fun event(event: PlayerQuitEvent) {
        clearPlayerData(event.player, ConversationEndEvent.Reason.QUIT)
    }

    fun clearPlayerData(player: Player, reason: ConversationEndEvent.Reason?) {
        if (PLAYER.containsKey(player.uniqueId)) {
            val node = QUEUE.remove(PLAYER[player.uniqueId]!!.queueId)
            val data = PLAYER.remove(player.uniqueId)
            val event = ConversationEndEvent(data!!.conversation, node, player, reason)
            RunnerUtil.call(event, true)
        }
    }

    fun nextNode(player: Player, conversation: Conversation?, node: Node) {
        if (PLAYER.containsKey(player.uniqueId)) {
            QUEUE.remove(PLAYER[player.uniqueId]!!.queueId)
        }
        val queueId = StringUtil.random(16, QUEUE.keys)
        if (!PLAYER.containsKey(player.uniqueId)) PLAYER[player.uniqueId] = ConversationData.of(conversation)
        PLAYER[player.uniqueId]!!.queueId = queueId
        QUEUE[queueId] = node
        val message = Component.text("    ").appendNewline()
            .append(Component.text("    ")).appendNewline()
            .append(Component.text("    ")).append(conversation!!.name()).appendNewline()
            .append(Component.text("    ")).appendNewline()
            .append(Component.text("    ")).append(node.text())
            .append(Component.text("    ")).appendNewline()
        var runnable: BukkitRunnable = object : BukkitRunnable() {
            override fun run() {
                clearPlayerData(player, ConversationEndEvent.Reason.COMPLETE)
            }
        }
        val nodes = node.sub()
        var pass = false
        if (nodes.size == 1) {
            val next = nodes[0]
            if (next.button() == null) {
                runnable = object : BukkitRunnable() {
                    override fun run() {
                        nextNode(player, conversation, next)
                    }
                }
                pass = true
            }
        }
        if (!pass) if (nodes.size >= 1) {
            var buttonBase = Component.text("    ").appendNewline()
            for (i in nodes.indices) {
                val buttonNode = nodes[i]
                if (buttonNode.button() == null) continue
                val button = buttonNode.button()!!
                val buttonComponent = Component.text().append(
                    button.name()
                )
                if (button.hover() != null) buttonComponent.hoverEvent(button.hover())
                buttonComponent.clickEvent(ClickEvent.runCommand("/conversation_button_handler $queueId $i"))
                buttonBase = buttonBase.append(Component.text("    ")).append(buttonComponent.build()).appendNewline()
            }
            val finalButtonBase = buttonBase
            runnable = object : BukkitRunnable() {
                override fun run() {
                    player.sendMessage(finalButtonBase)
                }
            }
        }
        val sound = node.sound()
        val event = SwitchNodeEvent(conversation, node, player)
        RunnerUtil.call(event, true)
        PlayerUtil.clearScreen(player)
        if (sound != null)
            player.playSound(sound)
        player.sendMessage(message)
        RunnerUtil.run(runnable, node.waiting().toLong())
    }


    private class ConversationData {
        var conversation: Conversation? = null
        var queueId: String? = null

        companion object {
            fun of(conversation: Conversation?): ConversationData {
                val data = ConversationData()
                data.conversation = conversation
                return data
            }

            fun of(conversation: Conversation, queueId: String): ConversationData {
                val data = ConversationData()
                data.conversation = conversation
                data.queueId = queueId
                return data
            }
        }
    }

}