package net.deechael.genshincraft.util.conversation

import net.deechael.conversation.builder.ButtonBuilder
import net.deechael.conversation.builder.ConversationBuilder
import net.deechael.conversation.builder.NodeBuilder
import net.deechael.conversation.event.ConversationEndEvent
import net.deechael.conversation.impl.builder.ButtonBuilderImpl
import net.deechael.conversation.impl.builder.ConversationBuilderImpl
import net.deechael.conversation.impl.builder.NodeBuilderImpl
import net.deechael.genshincraft.util.conversation.manager.ConversationManager
import org.bukkit.entity.Player

object ConversationAPI {

    fun newConversation(): ConversationBuilder {
        return ConversationBuilderImpl()
    }

    fun newNode(): NodeBuilder {
        return NodeBuilderImpl()
    }

    fun newButton(): ButtonBuilder {
        return ButtonBuilderImpl()
    }

    fun interrupt(player: Player) {
        ConversationManager.clearPlayerData(player, ConversationEndEvent.Reason.PLUGIN)
    }

}