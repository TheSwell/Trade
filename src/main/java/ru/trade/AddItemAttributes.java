package ru.trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.trade.listeners.SellerInventoryListener;
import ru.trade.utils.MenuItems;

public class AddItemAttributes {

    public void openItemAttributeMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "Attributes");
        double price;
        int slot = SellerInventoryListener.getSaveSelectedSlot().get(player.getName());
        ItemStack item = Trade.getAPI().getTradeInventory(player).getItem(slot);


        inventory.setItem(1, MenuItems.priceItem);
        inventory.setItem(4, item);
        inventory.setItem(7, MenuItems.cancelItem);

        player.openInventory(inventory);
    }
}
