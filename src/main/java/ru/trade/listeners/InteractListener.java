package ru.trade.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import ru.trade.Trade;
import ru.trade.utils.PlaceHolder;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractByPlayer(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        if (e.getRightClicked() instanceof Player clickedPlayer) {
            if (e.getHand().equals(EquipmentSlot.HAND))
                if (Trade.getAPI().hasTradeInventory(clickedPlayer)) {
                    if (!SellerInventoryListener.getSaveSelectedSlot().containsKey(clickedPlayer.getName())) {
                        player.openInventory(Trade.getAPI().getTradeInventory(clickedPlayer));
                        BuyerInventoryListener.getTradeInventory().put(player.getName(), clickedPlayer.getPlayer());
                    } else {
                        player.sendMessage(PlaceHolder.setPlaceHolderInConfig("thisPlayerEditTrade"));
                    }
                }
        }
    }
}
