package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import net.deechael.genshincraft.GenshinCraft;
import net.minecraft.commands.CommandSourceStack;

public class BukkitCommandContext implements CommandContext {

    private final static DynamicCommandExceptionType errorThrower = new DynamicCommandExceptionType(message -> new LiteralMessage("" + message));

    private final com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier;

    public BukkitCommandContext(com.mojang.brigadier.context.CommandContext<CommandSourceStack> context) {
        this.brigadier = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Argument<E>, E> E argument(Class<T> type, String name) {
        BukkitArgument<E> argument = (BukkitArgument<E>) GenshinCraft.getCommandManager().argument(type);
        return argument.parse(this, name);
    }

    @Override
    public void error(String message) throws CommandSyntaxException {
        throw errorThrower.create(message);
    }

    public com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier() {
        return brigadier;
    }

}
