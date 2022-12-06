package ru.trade.custom.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.trade.Trade;
import ru.trade.custom.event.StartTradeEvent;
import ru.trade.custom.event.StopTradeEvent;
import ru.trade.listeners.inventoryes.SellerInventoryListener;
import ru.trade.utils.ItemUtils;
import ru.trade.utils.Replacer;

public class TradeListener implements Listener {


    @EventHandler
    public void onStartTrade(StartTradeEvent e) {
        Player player = e.getPlayer();

        Inventory inventory = Bukkit.createInventory(null, 36, player.getName());

        ArmorStand trade = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        ArmorStand sitPos = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, -1.7, 0), EntityType.ARMOR_STAND);
        sitPos.setPassenger(player);
        sitPos.setGravity(false);
        sitPos.setVisible(false);

        trade.setVisible(false);
        trade.setCustomName(Replacer.setPlaceHolderInConfig("tradeName", player));
        trade.setCustomNameVisible(true);
        trade.setSmall(true);

        player.setPassenger(trade);
        Trade.getAPI().standMap.put(sitPos, player.getName());
        Trade.getAPI().standMap.put(trade, player.getName());
        Trade.getAPI().tradeInventoryMap.put(player.getName(), inventory);

        player.openInventory(inventory);
    }


    @EventHandler
    public void onStopTrade(StopTradeEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("Эвент работает.");

        if (e.isTrading()) {
            SellerInventoryListener.getItemPrice().remove(e.getInventory());
            SellerInventoryListener.getSaveSelectedSlot().remove(player.getName());
            for (ItemStack item :
                    e.getItems()) {
                if (item != null) {
                    player.getInventory().addItem(ItemUtils.copyItemWithoutPriceLore(item));
                }
            }
            for (ArmorStand stand :
                    e.getArmorStands()) {
                stand.remove();
            }

            Trade.getAPI().tradeInventoryMap.remove(player.getName());
        }
    }


}
