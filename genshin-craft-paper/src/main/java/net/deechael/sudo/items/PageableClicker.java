package net.deechael.sudo.items;

import net.deechael.sudo.pageable.PageableScene;

@FunctionalInterface
public interface PageableClicker<T> {

    void click(int currentPage, int currentSlot, T item, PageableScene<T> scene);

}
