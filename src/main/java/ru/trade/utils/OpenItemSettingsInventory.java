package ru.trade.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.trade.Trade;
import ru.trade.listeners.inventoryes.SellerInventoryListener;

public class OpenItemSettingsInventory {

    public void openSettingsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "Attributes");
        int slot = SellerInventoryListener.getSaveSelectedSlot().get(player.getName());
        ItemStack item = Trade.getAPI().getTradeInventory(player).getItem(slot);


        inventory.setItem(1, SettingsInventoryItems.priceItem);
        inventory.setItem(4, item);
        inventory.setItem(7, SettingsInventoryItems.cancelItem);

        player.openInventory(inventory);
    }
}
