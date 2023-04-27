package net.deechael.genshincraft.util.storage;

import org.jetbrains.annotations.NotNull;

/**
 * To delete data
 *
 * @param <T> data type
 */
public interface DataDelete<T extends DataHolder> {


    /**
     * Add condition with "=" operator
     *
     * @param key   key of the column
     * @param value the value to be checked
     * @return self
     */
    default DataDelete<T> condition(@NotNull String key, @NotNull String value) {
        return this.condition(key, "=", value);
    }

    /**
     * Add condition
     *
     * @param key      key of the column
     * @param operator the operator to use
     * @param value    the value to be checked
     * @return self
     */
    DataDelete<T> condition(@NotNull String key, @NotNull String operator, @NotNull String value);

    /**
     * Add condition
     *
     * @param value condition
     * @return self
     */
    DataDelete<T> condition(@NotNull String value);

    /**
     * Perform the deletion
     */
    void delete();

    /**
     * Get the table being lookup
     *
     * @return table
     */
    @NotNull
    DataTable<T> table();

}
