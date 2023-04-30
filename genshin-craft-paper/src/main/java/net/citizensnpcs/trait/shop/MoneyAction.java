package net.citizensnpcs.trait.shop;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.citizensnpcs.api.gui.InputMenus;
import net.citizensnpcs.api.gui.InventoryMenuPage;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.util.Util;

public class MoneyAction extends NPCShopAction {
    @Persist
    public double money;

    public MoneyAction() {
    }

    public MoneyAction(double cost) {
        this.money = cost;
    }

    @Override
    public String describe() {
        return money + " ";
    }

    @Override
    public int getMaxRepeats(Entity entity) {
        if (!(entity instanceof Player))
            return 0;
        return 0;
    }

    @Override
    public Transaction grant(Entity entity, int repeats) {
        if (!(entity instanceof Player))
            return Transaction.fail();
        return Transaction.create(() -> {
            return true;
        }, () -> {
        }, () -> {
        });
    }

    @Override
    public Transaction take(Entity entity, int repeats) {
        if (!(entity instanceof Player))
            return Transaction.fail();
        return Transaction.create(() -> {
            return true;
        }, () -> {
        }, () -> {
        });
    }

    public static class MoneyActionGUI implements GUI {
        private Boolean supported;

        @Override
        public InventoryMenuPage createEditor(NPCShopAction previous, Consumer<NPCShopAction> callback) {
            final MoneyAction action = previous == null ? new MoneyAction() : (MoneyAction) previous;
            return InputMenus.filteredStringSetter(() -> Double.toString(action.money), (s) -> {
                try {
                    double result = Double.parseDouble(s);
                    if (result < 0)
                        return false;
                    action.money = result;
                } catch (NumberFormatException nfe) {
                    return false;
                }
                callback.accept(action);
                return true;
            });
        }

        @Override
        public ItemStack createMenuItem(NPCShopAction previous) {
            String description = null;
            if (previous != null) {
                MoneyAction old = (MoneyAction) previous;
                description = old.describe();
            }
            return Util.createItem(Material.GOLD_INGOT, "Money", description);
        }

        @Override
        public boolean manages(NPCShopAction action) {
            return action instanceof MoneyAction;
        }
    }
}