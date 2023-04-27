package net.deechael.dynamic.quest.reward;

import net.deechael.dynamic.quest.api.quest.QuestData;
import net.deechael.dynamic.quest.api.reward.ItemReward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiConsumer;

public class DynamicItemReward extends DynamicReward implements ItemReward {

    private ItemStack itemStack;
    private int amount = 1;

    @Override
    public ItemReward item(Material type, BiConsumer<ItemStack, ItemMeta> consumer) {
        this.itemStack = new ItemStack(type);
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null)
            Bukkit.getItemFactory().getItemMeta(type);
        if (itemMeta == null)
            throw new RuntimeException("This item type is not allowed");
        consumer.accept(this.itemStack, itemMeta);
        return this;
    }

    @Override
    public ItemReward amount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public void complete(QuestData questData, Player player) {
        if (questData.isCompleted())
            throw new RuntimeException("The quest is not completed");
        int maxStackSize = this.itemStack.getType().getMaxStackSize();
        int times = this.amount % maxStackSize == 0 ? this.amount / maxStackSize : this.amount / maxStackSize + 1;
        int restAmount = this.amount;
        for (int i = 0; i < times; i++) {
            this.itemStack.setAmount(Math.min(restAmount, maxStackSize));
            player.getInventory().addItem();
            amount -= maxStackSize;
        }
    }

}
