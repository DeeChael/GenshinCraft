package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ArgumentBukkitPlayers implements BukkitArgument<List<Player>> {

    @Override
    public List<Player> parse(CommandContext context, String name) {
        try {
            return EntityArgument.getPlayers(((BukkitCommandContext) context).brigadier(), name).stream().map(ServerPlayer::getBukkitEntity).collect(Collectors.toList());
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgumentType<?> brigadier() {
        return EntityArgument.players();
    }

}
