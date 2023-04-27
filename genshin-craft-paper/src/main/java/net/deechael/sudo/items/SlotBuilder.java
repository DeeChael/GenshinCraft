package net.deechael.sudo.items;

import net.deechael.dutil.builder.BaseBuilder;
import net.deechael.sudo.Renderer;
import org.jetbrains.annotations.NotNull;

public interface SlotBuilder extends BaseBuilder<Void, Renderer> {

    /**
     * Start building a clicker
     *
     * @return clicker builder
     */
    ClickerBuilder clicker();

    /**
     * Start building an image
     *
     * @return image builder
     */
    ImageBuilder image();

    @Override
    default @NotNull Void build() {
        return null;
    }

    @NotNull
    Renderer done();
}
