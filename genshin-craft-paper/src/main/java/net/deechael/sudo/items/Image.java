package net.deechael.sudo.items;

import net.deechael.sudo.Scene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Image {

    @Nullable
    ItemStack render(int currentSlot, Scene scene);

}
