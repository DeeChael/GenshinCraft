package net.deechael.sudo.impl.items;

import net.deechael.sudo.Renderer;
import net.deechael.sudo.items.ClickerBuilder;
import net.deechael.sudo.items.ImageBuilder;
import net.deechael.sudo.items.SlotBuilder;
import org.jetbrains.annotations.NotNull;

public class SlotBuilderImpl implements SlotBuilder {

    private final Renderer renderer;
    private ClickerBuilderImpl clickerBuilder = null;
    private ImageBuilderImpl imageBuilder = null;

    public SlotBuilderImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public ClickerBuilderImpl getClickerBuilder() {
        return clickerBuilder;
    }

    public ImageBuilderImpl getImageBuilder() {
        return imageBuilder;
    }

    @Override
    public ClickerBuilder clicker() {
        if (this.clickerBuilder == null)
            this.clickerBuilder = new ClickerBuilderImpl(this);
        return this.clickerBuilder;
    }

    @Override
    public ImageBuilder image() {
        if (this.imageBuilder == null)
            this.imageBuilder = new ImageBuilderImpl(this);
        return this.imageBuilder;
    }

    @Override
    public @NotNull Renderer done() {
        return this.renderer;
    }

}
