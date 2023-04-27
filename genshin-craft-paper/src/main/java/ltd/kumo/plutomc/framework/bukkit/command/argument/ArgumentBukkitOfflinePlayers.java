package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import net.minecraft.commands.arguments.GameProfileArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collection;
import java.util.List;

public class ArgumentBukkitOfflinePlayers implements BukkitArgument<List<OfflinePlayer>> {

    @Override
    public ArgumentType<?> brigadier() {
        return GameProfileArgument.gameProfile();
    }

    @Override
    public List<OfflinePlayer> parse(CommandContext context, String name) {
        try {
            Collection<GameProfile> gameProfiles = GameProfileArgument.getGameProfiles(((BukkitCommandContext) context).brigadier(), name);

            return gameProfiles.stream()
                    .map(GameProfile::getId)
                    .map(Bukkit::getOfflinePlayer)
                    .toList();
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
