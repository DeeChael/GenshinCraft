package net.deechael.sudo.items;

import net.deechael.sudo.pageable.PageableScene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PageableImage<T> {

    @NotNull
    ItemStack render(int currentPage, int currentSlot, PageableScene<T> scene);

}
