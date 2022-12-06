package ru.trade.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.trade.Trade;
import ru.trade.listeners.inventoryes.SellerInventoryListener;
import ru.trade.utils.Replacer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatListener implements Listener {
    List<String> getPriceRequest = SellerInventoryListener.getPriceRequest();

    Map<Inventory, Map<Integer, Double>> itemPriceMap = SellerInventoryListener.getItemPrice();

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player player = e.getPlayer();
        if (SellerInventoryListener.getPriceRequest().contains(player.getName())) {
            if (Trade.getAPI().isTrading(player)) {
                String message = e.getMessage();
                Inventory inventory = Trade.getAPI().getTradeInventory(player);
                int slot = SellerInventoryListener.getSaveSelectedSlot().get(player.getName());
                String disableChars = "!@\"'#№;$%:^&?*()_-=+/\\`~<>";
                try {

                    boolean isCorrect = true;
                    for (int j = 0; j < message.length(); j++)
                        for (int i = 0; i < disableChars.length(); i++)
                            if (disableChars.charAt(i) == message.charAt(j)) {
                                isCorrect = false;
                            }
                    if (isCorrect) {
                        Trade.getAPI().addItemInTradeInventory(player, slot,
                                inventory.getItem(slot), Double.parseDouble(message.replaceAll(",", ".")));
                        Map<Integer, Double> slotPrice = itemPriceMap.getOrDefault(inventory, new HashMap<>());
                        slotPrice.put(slot, Double.parseDouble(message.replaceAll(",", ".")));
                        itemPriceMap.put(inventory, slotPrice);
                        player.openInventory(inventory);
                        getPriceRequest.remove(player.getName());
                    } else {
                        player.sendMessage(Replacer.setPlaceHolderInConfig("symbolsError"));
                        inventory.setItem(slot, new ItemStack(Material.AIR));
                    }
                } catch (NumberFormatException exception) {
                    getPriceRequest.remove(player.getName());
                    player.openInventory(Trade.getAPI().getTradeInventory(player));
                    inventory.setItem(slot, new ItemStack(Material.AIR));
                }
            }
            SellerInventoryListener.getSaveSelectedSlot().remove(player.getName());
            e.setCancelled(true);
        }
    }
}
