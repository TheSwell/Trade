package ru.trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.trade.interfaces.TradeInterface;
import ru.trade.listeners.SellerInventoryListener;
import ru.trade.utils.ItemsUtils;
import ru.trade.utils.PlaceHolder;

import java.util.*;

public class TradeAPI implements TradeInterface {
    public Map<String, Inventory> tradeInventoryMap = new HashMap<>();
    public Map<Inventory, Map<Integer, Double>> itemPrice = new HashMap<>();

    Map<ArmorStand, String> standMap = new HashMap<>();

    ItemsUtils itemsUtils = new ItemsUtils();

    @Override
    public void startTrade(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, player.getName());

        ArmorStand trade = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        ArmorStand sitPos = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, -1.7, 0), EntityType.ARMOR_STAND);
        sitPos.setPassenger(player);
        sitPos.setGravity(false);
        sitPos.setVisible(false);

        trade.setVisible(false);
        trade.setCustomName(PlaceHolder.setPlaceHolderInConfig("tradeName", player));
        trade.setCustomNameVisible(true);
        trade.setSmall(true);

        player.setPassenger(trade);
        standMap.put(sitPos, player.getName());
        standMap.put(trade, player.getName());
        tradeInventoryMap.put(player.getName(), inventory);

        player.openInventory(inventory);
    }

    @Override
    public void stopTrade(Player player) {
        if (isTrading(player)) {
            SellerInventoryListener.getItemPrice().remove(getTradeInventory(player));
            SellerInventoryListener.getSaveSelectedSlot().remove(player.getName());
            for (ItemStack item :
                    getItemsFromTradeInventory(player)) {
                if (item != null) {
                    player.getInventory().addItem(itemsUtils.copyItemWithoutPriceLore(item));
                }
            }
            for (Map.Entry<ArmorStand, String> key :
                    standMap.entrySet()) {
                if (key.getValue().equalsIgnoreCase(player.getName())) {
                    key.getKey().remove();
                }
            }


            tradeInventoryMap.remove(player.getName());
        }
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
            Map<Integer, Double> iprice = new HashMap<>();
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
            iprice.put(slot, price);
            ItemStack item = itemStack.clone();
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (meta.hasLore())
                for (String s :
                        meta.getLore())
                    lore.add(s);
            lore.add(PlaceHolder.setPlaceHolderInConfig("price", price));
            meta.setLore(lore);
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
            itemPrice.put(inventory, iprice);
        }
    }

    @Override
    public boolean isTrading(Player player) {
        return tradeInventoryMap.containsKey(player.getName());
    }
}
