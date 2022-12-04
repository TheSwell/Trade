package ru.trade.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.trade.AddItemAttributes;
import ru.trade.Trade;
import ru.trade.TradeAPI;
import ru.trade.utils.ItemsUtils;
import ru.trade.utils.PlaceHolder;

import java.util.*;

public class SellerInventoryListener implements Listener {

    AddItemAttributes itemAttributes = new AddItemAttributes();

    private static Map<Inventory, Map<Integer, Double>> itemPrice = new HashMap<>();
    private static Map<String, Integer> saveSelectedSlot = new HashMap<>();
    private static List<String> priceRequest = new ArrayList<>();

    TradeAPI api = Trade.getAPI();


    ItemsUtils itemsUtils = new ItemsUtils();


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase(player.getName())) {
            if (e.getClick().isShiftClick()) e.setCancelled(true);
            if (e.getClickedInventory() == null) return;
            if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
                e.setCancelled(true);
                if (e.getClick() == ClickType.LEFT) {
                    if (e.getCursor().getType() != Material.AIR) {
                        if (e.getCurrentItem() == null) {
                            if (!e.getClickedInventory().getViewers().isEmpty())
                                try {
                                    for (HumanEntity viewer :
                                            api.getTradeInventory(player).getViewers()) {
                                        Player viewerPlayer = (Player) viewer;
                                        if (viewerPlayer != player) {
                                            viewerPlayer.closeInventory();
                                            viewerPlayer.sendMessage(PlaceHolder.setPlaceHolderInConfig("thisPlayerEditTrade"));
                                        }
                                    }
                                } catch (ConcurrentModificationException ignored) {

                                }
                            e.getClickedInventory().setItem(e.getSlot(), e.getCursor());
                            e.getCursor().setAmount(0);
                            saveSelectedSlot.put(player.getName(), e.getSlot());
                            itemAttributes.openItemAttributeMenu(player);
                        } else player.sendMessage(PlaceHolder.setPlaceHolderInConfig("TheSlotIsBusy"));
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (e.getCurrentItem() != null) {
                        ItemStack item = e.getCurrentItem();
                        player.getInventory().addItem(itemsUtils.copyItemWithoutPriceLore(item));
                        e.getClickedInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
                        itemPrice.get(Trade.getAPI().getTradeInventory(player)).remove(e.getSlot());
                    }
                }
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase("Attributes")) {
            Inventory inventory = api.getTradeInventory(player);
            if (e.getCurrentItem() == null) return;
            switch (e.getCurrentItem().getType()) {
                case SUNFLOWER -> {
                    priceRequest.add(player.getName());
                    player.sendMessage(PlaceHolder.setPlaceHolderInConfig("priceHelp"));
                    player.closeInventory();
                }
                case BARRIER -> {
                    inventory.setItem(saveSelectedSlot.get(player.getName()), new ItemStack(Material.AIR));
                    player.openInventory(inventory);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase(player.getName())) {
            e.setCancelled(true);

        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if (api.isTrading(player)) {
            Inventory inventory = api.getTradeInventory(player);
            if (e.getView().getTitle().equalsIgnoreCase(player.getName())) {
                if (!getPriceRequest().contains(player.getName()) && !saveSelectedSlot.containsKey(player.getName())) {
                    api.stopTrade(player);
                    saveSelectedSlot.remove(player.getName());
                    if (priceRequest.contains(player.getName()))
                        priceRequest.remove(player.getName());
                    player.sendMessage(PlaceHolder.setPlaceHolderInConfig("youStoppedTrade"));
                }
            } else if (e.getView().getTitle().equalsIgnoreCase("Attributes") && !priceRequest.contains(player.getName())) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Trade.getInstance(), () -> {
                    if (saveSelectedSlot.containsKey(player.getName())) {
                        player.getInventory().addItem(itemsUtils.copyItemWithoutPriceLore(inventory.getItem(saveSelectedSlot.get(player.getName()))));
                        inventory.setItem(saveSelectedSlot.get(player.getName()), new ItemStack(Material.AIR));
                        player.openInventory(api.getTradeInventory(player));
                        saveSelectedSlot.remove(player.getName());

                    }
                }, 1L);
            }
        }

    }


    public static List<String> getPriceRequest() {
        return priceRequest;
    }

    public static Map<String, Integer> getSaveSelectedSlot() {
        return saveSelectedSlot;
    }

    public static Map<Inventory, Map<Integer, Double>> getItemPrice() {
        return itemPrice;
    }
}
