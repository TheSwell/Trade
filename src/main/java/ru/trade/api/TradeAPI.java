package ru.trade.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.trade.custom.event.StartTradeEvent;
import ru.trade.custom.event.StopTradeEvent;
import ru.trade.api.interfaces.ITrade;
import ru.trade.utils.Replacer;

import java.util.*;

public class TradeAPI implements ITrade {
    public Map<String, Inventory> tradeInventoryMap = new HashMap<>();
    public Map<Inventory, Map<Integer, Double>> itemPrice = new HashMap<>();

    public Map<ArmorStand, String> standMap = new HashMap<>();

    @Override
    public void startTrade(Player player) {
        Bukkit.getPluginManager().callEvent(new StartTradeEvent(player));
    }


    @Override
    public void stopTrade(Player player) {
        Bukkit.getPluginManager().callEvent(new StopTradeEvent(player));
    }

    @Override
    public Inventory getTradeInventory(Player player) {
        if (isTrading(player))
            return tradeInventoryMap.get(player.getName());
        return null;
    }

    @Override
    public List<ItemStack> getItemsFromTradeInventory(Player player) {
        if (isTrading(player))
            return Arrays.asList(tradeInventoryMap.get(player.getName()).getContents());
        return new ArrayList<>();
    }


    @Override
    public void addItemInTradeInventory(Player player, int slot, ItemStack itemStack, double price) {
        if (itemStack != null && isTrading(player)) {
            Map<Integer, Double> iPrice = new HashMap<>();
            Inventory inventory = tradeInventoryMap.get(player.getName());
            for (ItemStack item :
                    inventory.getContents()) {
                for (int i = 0; i < inventory.getSize(); i++) {
                    if (inventory.getItem(i) != null)
                        if (inventory.getItem(i).equals(item)) {
                            inventory.setItem(i, item);
                        }
                }
            }
            iPrice.put(slot, price);
            ItemStack item = itemStack.clone();
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (meta.hasLore())
                for (String s :
                        meta.getLore())
                    lore.add(s);
            lore.add(Replacer.setPlaceHolderInConfig("price", price));
            meta.setLore(lore);
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
            itemPrice.put(inventory, iPrice);
        }
    }

    @Override
    public boolean isTrading(Player player) {
        return tradeInventoryMap.containsKey(player.getName());
    }


}
