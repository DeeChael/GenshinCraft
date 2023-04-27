package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;
import org.bukkit.util.Vector;

public class ArgumentBukkitVector implements BukkitArgument<Vector> {

    @Override
    public Vector parse(CommandContext context, String name) {
        Vec3 vec3 = Vec3Argument.getVec3(((BukkitCommandContext) context).brigadier(), name);
        double x = vec3.x;
        double y = vec3.y;
        double z = vec3.z;
        return new Vector(x, y, z);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return Vec3Argument.vec3();
    }

}
