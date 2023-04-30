package net.deechael.genshincraft

import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandManager
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitVector
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentString
import net.deechael.genshincraft.command.*
import net.deechael.genshincraft.conversation.*
import net.deechael.genshincraft.listeners.DropItemListener
import net.deechael.genshincraft.listeners.PickupItemListener
import net.deechael.genshincraft.listeners.PlayerInventoryListener
import net.deechael.genshincraft.pathtracker.Tracker
import net.deechael.genshincraft.util.AudienceManager
import net.deechael.genshincraft.util.conversation.manager.ConversationManager
import net.deechael.genshincraft.util.plugin.GenshinPlugin
import net.deechael.sudo.Menu
import net.deechael.sudo.impl.MenuImpl
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.particle.utils.ReflectionUtils

class GenshinCraft : GenshinPlugin() {

    companion object {

        @JvmStatic
        val commandManager = BukkitCommandManager()

        @JvmStatic
        fun instance() = getPlugin(GenshinCraft::class.java)

        fun createMenu(): Menu {
            return MenuImpl()
        }

    }

    override fun onEnable() {
        slF4JLogger.info("==================================")
        slF4JLogger.info("=    WELCOME TO GENSHIN CRAFT!   =")
        slF4JLogger.info("=      DEVELOPED BY DEECHAEL     =")
        slF4JLogger.info("==================================")

        // Main functions
        listener(PickupItemListener)
        listener(DropItemListener)
        listener(PlayerInventoryListener)

        // Util functions
        ReflectionUtils.setPlugin(this)
        listener(ConversationManager)
        listener(AudienceManager)

        listener(commandManager)

        test()
    }

    fun test() {
        val testCmd =
            Literal("test") {
                Literal("sub") {
                    Executor { sender, _ ->
                        sender.sendMessage(Component.text("this is sub command!"))
                    }
                }
                Literal("path") {
                    Argument("from", ArgumentBukkitVector::class.java) {
                        Argument("to", ArgumentBukkitVector::class.java) {
                            PlayerExecutor { player, context ->
                                // /test path 85.10 64.13 -0.11 ~ ~ ~
                                try {
                                    val from = context.argument(ArgumentBukkitVector::class.java, "from")
                                    val to = context.argument(ArgumentBukkitVector::class.java, "to")
                                    val tracker = Tracker(player.world, from, to)
                                    println(tracker.paths)
                                    tracker.show(player)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                }
                Literal("conversation") {
                    PlayerExecutor { player, _ ->
                        try {
                            val conversation = Conversation {
                                Name(
                                    Component.text("Old Man")
                                        .color(NamedTextColor.GOLD)
                                )
                                Text("Hello, young man!")
                                Waiting(2)

                                Node {
                                    Text("There is a long time that nobody comes here")
                                    Waiting(2)

                                    Node {
                                        Text("So what can I do for ya?")

                                        Node {
                                            Text("Okay, here you are!")
                                            Button {
                                                Name(
                                                    Component.text("[An apple please]")
                                                        .color(NamedTextColor.RED)
                                                        .decorate(TextDecoration.BOLD)
                                                )
                                                Executor { event ->
                                                    event.player.inventory.addItem(ItemStack(Material.APPLE))
                                                }
                                            }
                                        }

                                        Node {
                                            Text("Well, good bye!")
                                            Button {
                                                Name(
                                                    Component.text("[No, thanks]")
                                                        .color(NamedTextColor.GRAY)
                                                        .decorate(TextDecoration.ITALIC)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            player.playConversation(conversation)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                Argument("name", ArgumentString::class.java) {
                    Executor { sender, context ->
                        sender.sendMessage(
                            Component.text(
                                "your input is ${
                                    context.argument(
                                        ArgumentString::class.java,
                                        "name"
                                    )
                                }"
                            )
                        )
                    }

                    TextSuggestion(
                        "deechael",
                        "example"
                    )
                }
                Executor { sender, _ ->
                    sender.sendMessage(Component.text("hello world!"))
                }
            }
        registerCommand(testCmd)
    }

    fun registerCommand(command: Command) {
        commandManager.register("genshincraft", command.original())
    }

}