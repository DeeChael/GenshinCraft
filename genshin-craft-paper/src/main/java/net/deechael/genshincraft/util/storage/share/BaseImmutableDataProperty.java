package net.deechael.genshincraft.util.storage.share;

import net.deechael.genshincraft.util.storage.ImmutableDataProperty;
import org.jetbrains.annotations.Nullable;

public class BaseImmutableDataProperty<T> extends BaseDataProperty<T> implements ImmutableDataProperty<T> {

    public BaseImmutableDataProperty(T value) {
        super.set(value);
    }

    @Override
    public void set(@Nullable T t) {
        throw new UnsupportedOperationException("This data is immutable!");
    }

    @Override
    public boolean isMutable() {
        return false;
    }

}
