package ltd.kumo.plutomc.framework.shared.command.executors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface PlayerExecutor {

    void executes(Player player, CommandContext context) throws CommandSyntaxException;

}
