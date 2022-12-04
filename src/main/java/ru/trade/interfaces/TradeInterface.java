package ru.trade.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface TradeInterface {

    public void startTrade(Player player);

    public void stopTrade(Player player);

    public Inventory getTradeInventory(Player player);

    public List<ItemStack> getItemsFromTradeInventory(Player player);

    public void addItemInTradeInventory(Player player, int slot, ItemStack itemStack, double price);

    public boolean isTrading(Player player);

}
