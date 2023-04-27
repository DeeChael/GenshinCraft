import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentString
import net.deechael.genshincraft.command.*
import net.kyori.adventure.text.Component

class CommandTest {

    fun example() {
        val command = Literal("test") {

            // Sub commands
            Literal("subcmd") {

                // Properties
                Executor { sender, context ->
                    sender.sendMessage(Component.text("subcmd here!"))
                }

            }

            Argument("name", ArgumentString::class.java) {

                // Properties
                PlayerExecutor { player, context ->
                    player.sendMessage(Component.text("Your name is ")
                        .append(Component.text(context.argument(ArgumentString::class.java, "name"))))
                }

            }

            // Properties
            Requirement { sender ->
                sender.hasPermission("genshincraft.command.test")
            }

            TextSuggestion(
                "hello, world!",
                "genshincraft!"
            )

            IntSuggestion(
                2,
                422,
                611
            )

        }
    }

}