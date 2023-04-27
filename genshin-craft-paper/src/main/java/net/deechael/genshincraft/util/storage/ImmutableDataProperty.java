package net.deechael.genshincraft.util.storage;

import org.jetbrains.annotations.Nullable;

public interface ImmutableDataProperty<T> extends DataProperty<T> {

    @Override
    default void set(@Nullable T t) {
        throw new UnsupportedOperationException("This data is immutable!");
    }

    @Override
    default boolean isMutable() {
        return false;
    }

}
