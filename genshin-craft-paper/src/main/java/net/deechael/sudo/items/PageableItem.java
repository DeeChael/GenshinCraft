package net.deechael.sudo.items;

import net.deechael.sudo.pageable.PageableScene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PageableItem<T> {

    @NotNull
    ItemStack render(int currentPage, int currentSlot, T item, PageableScene<T> scene);

}
