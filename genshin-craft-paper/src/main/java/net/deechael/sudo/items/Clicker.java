package net.deechael.sudo.items;

import net.deechael.sudo.Scene;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface Clicker {

    void click(int currentSlot, Scene scene, ItemStack cursor);

}
