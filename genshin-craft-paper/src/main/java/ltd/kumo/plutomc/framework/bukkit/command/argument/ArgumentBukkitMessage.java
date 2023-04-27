package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentMessage;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;

public class ArgumentBukkitMessage extends ArgumentMessage implements BukkitArgument<String> {

    @Override
    public ArgumentType<?> brigadier() {
        return MessageArgument.message();
    }

    @Override
    public String parse(CommandContext context, String name) {
        try {
            Component component = MessageArgument.getMessage(((BukkitCommandContext) context).brigadier(), name);
            return component.getString();
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
