package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import ltd.kumo.plutomc.framework.bukkit.command.argument.*;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.arguments.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.command.VanillaCommandWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

import java.lang.reflect.Field;
import java.util.*;

public class BukkitCommandManager implements Listener {

    private final static Field VANILLA_COMMAND_WRAPPER_DISPATCHER;

    static {
        try {
            VANILLA_COMMAND_WRAPPER_DISPATCHER = VanillaCommandWrapper.class.getDeclaredField("dispatcher");
            VANILLA_COMMAND_WRAPPER_DISPATCHER.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private final CommandDispatcher<CommandSender> dispatcher = new CommandDispatcher<>();
    private final Map<Class<?>, BukkitArgument<?>> argumentImplementers = new HashMap<>();
    private final CommandMap commandMap;

    private final Map<String, Set<CMD>> registered = new HashMap<>();

    public BukkitCommandManager() {

        // Register default command types
        ArgumentBukkitInteger argumentBukkitInteger = new ArgumentBukkitInteger();
        this.argumentImplementers.put(ArgumentInteger.class, argumentBukkitInteger);
        this.argumentImplementers.put(ArgumentBukkitInteger.class, argumentBukkitInteger);

        ArgumentBukkitLong argumentBukkitLong = new ArgumentBukkitLong();
        this.argumentImplementers.put(ArgumentLong.class, argumentBukkitLong);
        this.argumentImplementers.put(ArgumentBukkitLong.class, argumentBukkitLong);

        ArgumentBukkitFloat argumentBukkitFloat = new ArgumentBukkitFloat();
        this.argumentImplementers.put(ArgumentFloat.class, argumentBukkitFloat);
        this.argumentImplementers.put(ArgumentBukkitFloat.class, argumentBukkitFloat);

        ArgumentBukkitDouble argumentBukkitDouble = new ArgumentBukkitDouble();
        this.argumentImplementers.put(ArgumentDouble.class, argumentBukkitDouble);
        this.argumentImplementers.put(ArgumentBukkitDouble.class, argumentBukkitDouble);

        ArgumentBukkitBoolean argumentBukkitBoolean = new ArgumentBukkitBoolean();
        this.argumentImplementers.put(ArgumentBoolean.class, argumentBukkitBoolean);
        this.argumentImplementers.put(ArgumentBukkitBoolean.class, argumentBukkitBoolean);

        ArgumentBukkitString argumentBukkitString = new ArgumentBukkitString();
        this.argumentImplementers.put(ArgumentString.class, argumentBukkitString);
        this.argumentImplementers.put(ArgumentBukkitString.class, argumentBukkitString);

        ArgumentBukkitMessage argumentBukkitMessage = new ArgumentBukkitMessage();
        this.argumentImplementers.put(ArgumentMessage.class, argumentBukkitMessage);
        this.argumentImplementers.put(ArgumentBukkitMessage.class, argumentBukkitMessage);

        this.argumentImplementers.put(ArgumentBukkitPlayer.class, new ArgumentBukkitPlayer());
        this.argumentImplementers.put(ArgumentBukkitPlayers.class, new ArgumentBukkitPlayers());
        this.argumentImplementers.put(ArgumentBukkitWorld.class, new ArgumentBukkitWorld());
        this.argumentImplementers.put(ArgumentBukkitVector.class, new ArgumentBukkitVector());
        this.argumentImplementers.put(ArgumentBukkitOfflinePlayers.class, new ArgumentBukkitOfflinePlayers());

        this.commandMap = Bukkit.getServer().getCommandMap();
    }

    public BukkitCommand createCommand(String name) {
        return new BukkitCommand(name);
    }

    public CommandDispatcher<CommandSender> dispatcher() {
        return dispatcher;
    }

    public void register(String prefix, BukkitCommand command) {
        Command vcw = new VanillaCommandWrapper(null, command.toBrigadier().build());
        this.commandMap.register(command.name(), prefix, vcw);
        for (String alias : command.getAliases())
            this.commandMap.register(alias, prefix, vcw);
        if (!registered.containsKey(prefix))
            registered.put(prefix, new HashSet<>());
        this.registered.get(prefix).add(new CMD(prefix, vcw, command));
    }

    public <E extends Argument<?>> BukkitArgument<?> argument(Class<E> clazz) {
        return this.argumentImplementers.get(clazz);
    }

    @EventHandler
    public void serverLoaded(ServerLoadEvent event) {
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        Commands bukkitCommandDispatcher = minecraftServer.getCommands();
        CommandDispatcher<CommandSourceStack> brigadierCommandDispatcher = bukkitCommandDispatcher.getDispatcher();
        Map<String, Command> knownCommands = this.commandMap.getKnownCommands();
        for (String prefix : registered.keySet())
            for (CMD commandRecord : registered.get(prefix)) {
                CommandNode<CommandSourceStack> commandNode = commandRecord.bukkitCommand.toBrigadier().build();
                List<String> aliases = new ArrayList<>();
                aliases.add(commandRecord.bukkitCommand.name());
                aliases.add(prefix + ":" + commandRecord.bukkitCommand.name());
                for (String alias : commandRecord.bukkitCommand.getAliases()) {
                    aliases.add(alias);
                    aliases.add(prefix + ":" + alias);
                }
                for (String alias : aliases) {
                    Command command = knownCommands.get(alias);
                    if (command == null)
                        continue;
                    if (!(command instanceof VanillaCommandWrapper))
                        continue;
                    if (!Objects.equals(command, commandRecord.command()))
                        continue;
                    command.setDescription("A Pluto-Framework provided command");
                    brigadierCommandDispatcher.getRoot().addChild(copy(alias, commandNode));
                    this.updateField(command, bukkitCommandDispatcher);
                }
            }
    }

    private void updateField(Command command, Commands bukkitCommandDispatcher) {
        try {
            VANILLA_COMMAND_WRAPPER_DISPATCHER.set(command, bukkitCommandDispatcher);
        } catch (IllegalAccessException ignored) {
        }
    }

    private CommandNode<CommandSourceStack> copy(String name, CommandNode<CommandSourceStack> redirector) {
        return LiteralArgumentBuilder.<CommandSourceStack>literal(name)
                .redirect(redirector)
                .build();
    }

    private record CMD(String prefix, Command command, BukkitCommand bukkitCommand) {
    }

}
