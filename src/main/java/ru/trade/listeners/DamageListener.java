package ru.trade.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import ru.trade.Trade;
import ru.trade.api.TradeAPI;

public class DamageListener implements Listener {
    TradeAPI api = Trade.getAPI();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            api.stopTrade(player);
        }
    }

}
