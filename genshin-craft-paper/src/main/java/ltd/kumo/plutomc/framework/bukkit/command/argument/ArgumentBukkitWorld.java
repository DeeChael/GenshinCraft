package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import net.minecraft.commands.arguments.DimensionArgument;
import org.bukkit.World;

public class ArgumentBukkitWorld implements BukkitArgument<World> {

    @Override
    public ArgumentType<?> brigadier() {
        return DimensionArgument.dimension();
    }

    @Override
    public World parse(CommandContext context, String name) {
        try {
            return DimensionArgument.getDimension(((BukkitCommandContext) context).brigadier(), name).getWorld();
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
