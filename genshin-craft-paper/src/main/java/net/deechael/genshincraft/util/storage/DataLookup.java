package net.deechael.genshincraft.util.storage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * To lookup data
 *
 * @param <T> data holder
 */
public interface DataLookup<T extends DataHolder> {

    /**
     * Add condition with "=" operator
     *
     * @param key   key of the column
     * @param value the value to be checked
     * @return self
     */
    default DataLookup<T> condition(@NotNull String key, @NotNull String value) {
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
    DataLookup<T> condition(@NotNull String key, @NotNull String operator, @NotNull String value);

    /**
     * Add condition
     *
     * @param value condition
     * @return self
     */
    DataLookup<T> condition(@NotNull String value);

    /**
     * List all the results which pass muster
     *
     * @return results
     */
    @NotNull
    List<T> lookup();

    /**
     * Get the table being lookup
     *
     * @return table
     */
    @NotNull
    DataTable<T> table();

}
