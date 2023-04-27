package net.deechael.sudo;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Audience {

    @Nullable
    Scene getScene();

    @NotNull
    UUID uuid();

    void removeScene();

    void setScene(Scene scene);

    Player getPlayer();

}
