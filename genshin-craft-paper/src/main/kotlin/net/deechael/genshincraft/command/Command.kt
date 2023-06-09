package net.deechael.genshincraft.command

import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommand
import ltd.kumo.plutomc.framework.shared.command.Argument
import ltd.kumo.plutomc.framework.shared.command.CommandContext
import net.deechael.genshincraft.GenshinCraft
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Command {

    private val command: BukkitCommand

    internal constructor(name: String) {
        this.command = GenshinCraft.commandManager.createCommand(name)
    }

    internal constructor(command: BukkitCommand) {
        this.command = command
    }

    fun original(): BukkitCommand {
        return this.command
    }
}

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
annotation class CommandBody {

}

@CommandBody
fun Literal(name: String, body: @CommandBody Command.() -> Unit): Command {
    val command = Command(name)
    command.apply(body)
    return command
}

@CommandBody
fun Command.Literal(name: String, body: @CommandBody Command.() -> Unit): Command {
    val command = Command(name)
    command.apply(body)
    this.original().then(command.original())
    return command
}

@CommandBody
fun Command.Argument(name: String, type: Class<out Argument<*>>, body: @CommandBody Command.() -> Unit): Command {
    val command = Command(this.original().then(name, type))
    command.apply(body)
    this.original().then(command.original())
    return command
}

@CommandBody
fun Command.Executor(body: @CommandBody (CommandSender, CommandContext) -> Unit) {
    this.original().apply {
        this.executes(body)
    }
}

@CommandBody
fun Command.PlayerExecutor(body: @CommandBody (Player, CommandContext) -> Unit) {
    this.original().apply {
        this.executesPlayer(body)
    }
}

@CommandBody
fun Command.Requirement(body: @CommandBody Command.(CommandSender) -> Boolean) {
    this.original().apply {
        this.requires {
            body(it)
        }
    }
}

@CommandBody
fun Command.TextSuggestion(vararg suggestions: String) {
    this.original().apply {
        this.suggests { suggestion ->
            suggestions.forEach {
                suggestion.suggests(it)
            }
        }
    }
}

@CommandBody
fun Command.IntSuggestion(vararg suggestions: Int) {
    this.original().apply {
        this.suggests { suggestion ->
            suggestions.forEach {
                suggestion.suggests(it)
            }
        }
    }
}
