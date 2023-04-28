package net.deechael.genshincraft.conversation

import net.deechael.conversation.api.Button
import net.deechael.conversation.api.Node
import net.deechael.conversation.event.ConversationEvent
import net.deechael.genshincraft.util.conversation.ConversationAPI
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.function.Consumer

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
annotation class ConversationBody {

}

class EnhancedConversation {

    var name: Component? = null
    var text: Component? = null
    val nodes: MutableList<EnhancedNode> = mutableListOf()
    var waiting: Int = 0
    var sound: Sound? = null

}

class EnhancedNode {

    var text: Component? = null
    val nodes: MutableList<EnhancedNode> = mutableListOf()
    var waiting: Int = 0
    var sound: Sound? = null
    var button: EnhancedButton? = null

}

class EnhancedButton {

    var name: Component? = null
    var hover: Component? = null
    var executor: Consumer<ConversationEvent>? = null

}

fun Player.playConversation(conversation: EnhancedConversation) {
    val rawConversation = ConversationAPI.newConversation()
        .name(conversation.name!!)
        .text(conversation.text!!)
        .waiting(conversation.waiting)
        .sound(conversation.sound)
        .node(conversation.nodes.stream().map(EnhancedNode::toNormal).toList())
        .build()
    rawConversation.start(this)
}

private fun EnhancedNode.toNormal(): Node {
    return ConversationAPI.newNode()
        .text(this.text!!)
        .waiting(this.waiting)
        .sound(this.sound)
        .button(
            if (this.button == null)
                null
            else
                Button.of()
                    .name(this.button!!.name!!)
                    .hover(this.button!!.hover)
                    .executor(this.button!!.executor)
                    .build()
        )
        .sub(this.nodes.stream().map(EnhancedNode::toNormal).toList())
        .build()
}

@ConversationBody
fun Conversation(body: @ConversationBody EnhancedConversation.() -> Unit): EnhancedConversation {
    val builder = EnhancedConversation()
    builder.apply(body)
    return builder
}

@ConversationBody
fun EnhancedConversation.Node(body: @ConversationBody EnhancedNode.() -> Unit): EnhancedNode {
    val builder = EnhancedNode()
    builder.apply(body)
    this.nodes.add(builder)
    return builder
}


@ConversationBody
fun EnhancedNode.Node(body: @ConversationBody EnhancedNode.() -> Unit): EnhancedNode {
    val builder = EnhancedNode()
    builder.apply(body)
    this.nodes.add(builder)
    return builder
}

@ConversationBody
fun EnhancedNode.Button(body: @ConversationBody EnhancedButton.() -> Unit): EnhancedButton {
    val builder = EnhancedButton()
    builder.apply(body)
    this.button = builder
    return builder
}

@ConversationBody
fun EnhancedConversation.Sound(sound: Sound) {
    this.sound = sound
}

@ConversationBody
fun EnhancedConversation.Name(text: String) {
    this.name = Component.text(text)
}

@ConversationBody
fun EnhancedConversation.Name(text: Component) {
    this.name = text
}

@ConversationBody
fun EnhancedConversation.Text(text: String) {
    this.text = Component.text(text)
}

@ConversationBody
fun EnhancedConversation.Text(text: Component) {
    this.text = text
}

@ConversationBody
fun EnhancedConversation.Waiting(waiting: Int) {
    this.waiting = waiting
}

@ConversationBody
fun EnhancedNode.Sound(sound: Sound) {
    this.sound = sound
}

@ConversationBody
fun EnhancedNode.Text(text: String) {
    this.text = Component.text(text)
}

@ConversationBody
fun EnhancedNode.Text(text: Component) {
    this.text = text
}

@ConversationBody
fun EnhancedNode.Waiting(waiting: Int) {
    this.waiting = waiting
}

@ConversationBody
fun EnhancedButton.Name(name: String) {
    this.name = Component.text(name)
}

@ConversationBody
fun EnhancedButton.Name(name: Component) {
    this.name = name
}

@ConversationBody
fun EnhancedButton.Hover(hover: String) {
    this.hover = Component.text(hover)
}

@ConversationBody
fun EnhancedButton.Hover(hover: Component) {
    this.hover = hover
}

@ConversationBody
fun EnhancedButton.Executor(executor: (ConversationEvent) -> Unit) {
    this.executor = Consumer { event ->
        executor(event)
    }
}