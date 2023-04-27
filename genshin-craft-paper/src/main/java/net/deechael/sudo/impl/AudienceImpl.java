package net.deechael.sudo.impl;

import net.deechael.sudo.Audience;
import net.deechael.sudo.Scene;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AudienceImpl implements Audience {

    private final Player player;
    private Scene scene;

    public AudienceImpl(Player player) {
        this.player = player;
    }

    @Override
    public @Nullable Scene getScene() {
        return this.scene;
    }

    @Override
    public @NotNull UUID uuid() {
        return this.player.getUniqueId();
    }

    @Override
    public void removeScene() {
        this.player.closeInventory();
        this.scene = null;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

}
