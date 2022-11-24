package ru.trade.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.trade.listeners.BuyerInventoryListener;

import java.util.Map;

public class BuyerUtils {

    public void openBuyConfirmation(Player player, ItemStack item) {
        Map<String, Player> tradeInventory = BuyerInventoryListener.getTradeInventory();
        if (tradeInventory.containsKey(player.getName())) {
            Inventory buyConfirmationInventory = Bukkit.createInventory(null, 9, "Confirm");
            buyConfirmationInventory.setItem(4, item);
            buyConfirmationInventory.setItem(1, new ItemStack(MenuItems.buyButton));
            buyConfirmationInventory.setItem(7, new ItemStack(MenuItems.cancelItem));
            player.openInventory(buyConfirmationInventory);
        }
    }
}
