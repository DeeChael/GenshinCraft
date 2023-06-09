package net.deechael.sudo.pageable;

import net.deechael.sudo.Audience;
import net.deechael.sudo.Renderer;
import net.deechael.sudo.items.*;
import net.kyori.adventure.text.Component;

public interface PageableRenderer<T> extends Renderer {

    /**
     * The menu owning this renderer
     *
     * @return owner
     */
    PageableMenu<T> menu();

    /**
     * Who is viewing this menu
     *
     * @return viewer
     */
    Audience viewer();

    /**
     * Set the title of the menu
     *
     * @param title new title
     * @return self
     */
    PageableRenderer<T> title(String title);

    /**
     * Set the title of the menu
     *
     * @param title new title
     * @return self
     */
    PageableRenderer<T> title(Component title);

    /**
     * Set the lines of the menu
     *
     * @param lines lines in [1, 6]
     * @return renderer
     */
    PageableRenderer<T> line(int lines);

    /**
     * Set the event handler of closing
     *
     * @param closer event handler
     * @return self
     */
    PageableRenderer<T> close(Closer closer);

    /**
     * Start building a slot
     *
     * @param raw slot index
     * @return slot builder
     */
    @Override
    default SlotBuilder slot(int raw) {
        throw new RuntimeException("Cannot set item in Pageable Renderer");
    }

    /**
     * Set the image of previous page button
     *
     * @param image image
     * @return self
     */
    PageableRenderer<T> previous(PageableImage<T> image);

    /**
     * Set the image of next page button
     *
     * @param image image
     * @return self
     */
    PageableRenderer<T> next(PageableImage<T> image);

    /**
     * Set the image of back page button
     *
     * @param image image
     * @return self
     */
    PageableRenderer<T> back(PageableImage<T> image);

    /**
     * Set the item renderer
     *
     * @param renderer item renderer
     * @return self
     */
    PageableRenderer<T> item(PageableItem<T> renderer);

    /**
     * Set the item clicker
     *
     * @param clicker item clicker
     * @return self
     */
    PageableRenderer<T> item(PageableClicker<T> clicker);

    /**
     * Add items
     *
     * @param items items
     * @return self
     */
    PageableRenderer<T> item(T... items);

    /**
     * Add items
     *
     * @param items items
     * @return self
     */
    PageableRenderer<T> item(Iterable<T> items);

    /**
     * Set the start page
     *
     * @param page start page
     * @return self
     */
    PageableRenderer<T> page(int page);

    /**
     * Render this menu
     *
     * @return rendered scene
     */
    PageableScene<T> render();

}
