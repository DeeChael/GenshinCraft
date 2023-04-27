package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitDouble;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitFloat;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitInteger;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitLong;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.Suggestion;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentDouble;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentFloat;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentInteger;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentLong;
import ltd.kumo.plutomc.framework.shared.command.executors.Executor;
import ltd.kumo.plutomc.framework.shared.command.executors.PlayerExecutor;
import net.deechael.genshincraft.GenshinCraft;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BukkitCommand implements Command {

    private final String name;
    private final boolean argument;
    private final ArgumentType<?> brigadierType;
    private final List<BukkitCommand> children = new ArrayList<>();
    private Executor executor;
    private PlayerExecutor executorPlayer;
    private Consumer<Suggestion> suggestion;
    private Predicate<CommandSender> requirement;
    private final List<String> aliases = new ArrayList<>();

    public BukkitCommand(String name) {
        this.name = name;
        this.argument = false;
        this.brigadierType = null;
    }

    public BukkitCommand(String name, ArgumentType<?> brigadierType) {
        this.name = name;
        this.brigadierType = brigadierType;
        this.argument = brigadierType != null;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public BukkitCommand suggests(Consumer<Suggestion> provider) {
        this.suggestion = provider;
        return this;
    }

    @Override
    public BukkitCommand requires(Predicate<CommandSender> requirement) {
        this.requirement = requirement;
        return this;
    }

    @Override
    public BukkitCommand aliases(String... aliases) {
        this.aliases.addAll(List.of(aliases));
        return this;
    }

    @Override
    public BukkitCommand executes(Executor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public BukkitCommand executesPlayer(PlayerExecutor executor) {
        this.executorPlayer = executor;
        return this;
    }

    @Override
    public BukkitCommand then(String name) {
        BukkitCommand command = new BukkitCommand(name);
        this.children.add(command);
        return command;
    }

    public BukkitCommand then(BukkitCommand another) {
        this.children.add(another);
        return another;
    }

    @Override
    public <E extends Argument<?>> BukkitCommand then(String name, Class<E> type) {
        BukkitArgument<?> argument = GenshinCraft.getCommandManager().argument(type);
        if (argument == null)
            throw new RuntimeException("Cannot find the argument type");
        BukkitCommand command = new BukkitCommand(name, argument.brigadier());
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenInteger(String name, int min, int max) {
        ArgumentBukkitInteger argument = (ArgumentBukkitInteger) GenshinCraft.getCommandManager().argument(ArgumentInteger.class);
        BukkitCommand command = new BukkitCommand(name, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenLong(String name, long min, long max) {
        ArgumentBukkitLong argument = (ArgumentBukkitLong) GenshinCraft.getCommandManager().argument(ArgumentLong.class);
        BukkitCommand command = new BukkitCommand(name, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenFloat(String name, float min, float max) {
        ArgumentBukkitFloat argument = (ArgumentBukkitFloat) GenshinCraft.getCommandManager().argument(ArgumentFloat.class);
        BukkitCommand command = new BukkitCommand(name, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenDouble(String name, double min, double max) {
        ArgumentBukkitDouble argument = (ArgumentBukkitDouble) GenshinCraft.getCommandManager().argument(ArgumentDouble.class);
        BukkitCommand command = new BukkitCommand(name, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand clone(String name) {
        BukkitCommand newCommand = new BukkitCommand(name, this.brigadierType);
        newCommand.children.addAll(this.children);
        newCommand.executor = this.executor;
        newCommand.executorPlayer = this.executorPlayer;
        newCommand.suggestion = this.suggestion;
        newCommand.requirement = this.requirement;
        return newCommand;
    }

    public List<String> getAliases() {
        return aliases;
    }

    @SuppressWarnings("unchecked")
    public ArgumentBuilder<CommandSourceStack, ?> toBrigadier() {
        ArgumentBuilder<CommandSourceStack, ?> builder = this.argument ? RequiredArgumentBuilder.argument(this.name, this.brigadierType) : LiteralArgumentBuilder.literal(this.name);
        for (BukkitCommand command : this.children)
            builder.then(command.toBrigadier());
        if (this.requirement != null)
            builder.requires(req -> {
                CommandSender sender = req.getBukkitSender();
                return this.requirement.test(sender);
            });
        if (this.suggestion != null && this.argument)
            ((RequiredArgumentBuilder<CommandSourceStack, ?>) builder).suggests(((commandContext, suggestionsBuilder) -> {
                BukkitSuggestion bukkitSuggestion = new BukkitSuggestion();
                suggestion.accept(bukkitSuggestion);
                bukkitSuggestion.getStringSuggestions().forEach(suggestionsBuilder::suggest);
                bukkitSuggestion.getIntSuggestions().forEach(suggestionsBuilder::suggest);
                return suggestionsBuilder.buildFuture();
            }));
        if (executorPlayer != null || executor != null)
            builder.executes(commandContext -> {
                BukkitCommandContext context = new BukkitCommandContext(commandContext);
                CommandSender sender = commandContext.getSource().getBukkitSender();
                if (sender instanceof Player player) {
                    if (executorPlayer != null) {
                        this.executorPlayer.executes(player, context);
                        return 1;
                    } else if (executor != null) {
                        this.executor.executes(player, context);
                        return 1;
                    }
                } else {
                    if (executor != null) {
                        this.executor.executes(sender, context);
                        return 1;
                    } else if (executorPlayer != null) {
                        context.error("你必须是一个玩家");
                    }
                }
                return 0;
            });
        return builder;
    }

}
