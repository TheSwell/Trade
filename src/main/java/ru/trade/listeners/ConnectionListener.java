package ru.trade.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.trade.Trade;
import ru.trade.TradeAPI;

public class ConnectionListener implements Listener {


    @EventHandler
    public void onExit(PlayerQuitEvent e) {
        if (Trade.getAPI().hasTradeInventory(e.getPlayer())) {
            Trade.getAPI().removeTradeInventory(e.getPlayer());
        }
    }
}
