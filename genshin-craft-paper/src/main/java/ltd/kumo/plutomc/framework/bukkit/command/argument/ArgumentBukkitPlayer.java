package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import net.minecraft.commands.arguments.EntityArgument;
import org.bukkit.entity.Player;

public class ArgumentBukkitPlayer implements BukkitArgument<Player> {

    @Override
    public Player parse(CommandContext context, String name) {
        try {
            return EntityArgument.getPlayer(((BukkitCommandContext) context).brigadier(), name).getBukkitEntity();
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgumentType<?> brigadier() {
        return EntityArgument.player();
    }

}
