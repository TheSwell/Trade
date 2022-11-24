package ru.trade.interfaces;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface TradeInterface {

    public void createTradeInventory(Player player);

    public void removeTradeInventory(Player player);

    public Inventory getTradeInventory(Player player);

    public List<ItemStack> getItemsFromTradeInventory(Player player);

    public void addItemInTradeInventory(Player player, int slot, ItemStack itemStack, double price);

    public boolean hasTradeInventory(Player player);

}
