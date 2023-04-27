package net.deechael.sudo.items;

import net.deechael.sudo.Scene;

@FunctionalInterface
public interface Closer {

    void close(Scene scene);

}
