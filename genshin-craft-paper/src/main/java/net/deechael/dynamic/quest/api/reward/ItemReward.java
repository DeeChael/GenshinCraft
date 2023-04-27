package net.deechael.dynamic.quest.api.reward;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiConsumer;

public interface ItemReward extends Reward {

    /**
     * Set the item of the reward
     *
     * @param type     the type of the item
     * @param consumer to edit the item
     * @return self
     */
    ItemReward item(Material type, BiConsumer<ItemStack, ItemMeta> consumer);

    /**
     * Setting amount when creating item won't work, please set the amount of the reward items right here
     *
     * @param amount the amount of reward items
     * @return self
     */
    ItemReward amount(int amount);

}
