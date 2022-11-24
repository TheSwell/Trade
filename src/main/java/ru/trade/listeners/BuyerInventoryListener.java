package ru.trade.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import ru.trade.Trade;
import ru.trade.TradeAPI;
import ru.trade.utils.BuyerUtils;
import ru.trade.utils.ItemsUtils;
import ru.trade.utils.PlaceHolder;

import java.util.HashMap;
import java.util.Map;

public class BuyerInventoryListener implements Listener {
    private static final Map<String, Player> tradeInventory = new HashMap<>();
    private static final Map<String, Integer> selectedSlot = new HashMap<>();

    TradeAPI api = Trade.getAPI();
    BuyerUtils buyerUtils = new BuyerUtils();

    ItemsUtils itemsUtils = new ItemsUtils();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (tradeInventory.get(player.getName()) != null)
            if (api.hasTradeInventory(tradeInventory.get(player.getName()))) {
                Inventory inventory = api.getTradeInventory(tradeInventory.get(player.getName()));

                if (e.getInventory() == inventory) {
                    if (e.getClickedInventory().getType() == InventoryType.PLAYER) e.setCancelled(true);
                    if (e.getClickedInventory() == inventory) {
                        e.setCancelled(true);
                        if (e.getCurrentItem() != null) {
                            selectedSlot.put(player.getName(), e.getSlot());
                            buyerUtils.openBuyConfirmation(player, e.getCurrentItem());
                        }
                    }
                }

                if (e.getView().getTitle().contains("Confirm")) {
                    e.setCancelled(true);
                    if (inventory != null) {
                        if (selectedSlot.containsKey(player.getName()) && tradeInventory.containsKey(player.getName())) {
                            int slot = selectedSlot.get(player.getName());
                            if (inventory.getItem(slot) != null) {
                                if (e.getCurrentItem() == null) return;
                                switch (e.getCurrentItem().getType()) {
                                    case GREEN_WOOL -> {
                                        if (Trade.getEconomy().has(player, SellerInventoryListener.getItemPrice().get(inventory).get(slot))) {
                                            Trade.getEconomy().withdrawPlayer(player, SellerInventoryListener.getItemPrice().get(inventory).get(slot));
                                            Trade.getEconomy().depositPlayer(tradeInventory.get(player.getName()), SellerInventoryListener.getItemPrice().get(inventory).get(slot));
                                            player.getInventory().addItem(itemsUtils.copyItemWithoutPriceLore(inventory.getItem(slot)));
                                            inventory.getItem(slot).setAmount(0);
                                            player.sendMessage("§7[§4-§7]§f " + SellerInventoryListener.getItemPrice().get(inventory).get(slot));
                                            tradeInventory.get(player.getName()).sendMessage("§7[§2+§7]§f " + SellerInventoryListener.getItemPrice().get(inventory).get(slot));
                                            SellerInventoryListener.getItemPrice().get(inventory).remove(slot);
                                        } else player.sendMessage("haventMoney");
                                    }
                                }
                            }
                        }
                        player.openInventory(inventory);
                    } else {
                        player.closeInventory();
                        player.sendMessage(PlaceHolder.setPlaceHolderInConfig("stoppedTrade", player));
                    }
                }
            } else {
                e.setCancelled(true);
                player.closeInventory();
            }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
    }

    public static Map<String, Player> getTradeInventory() {
        return tradeInventory;
    }
}
