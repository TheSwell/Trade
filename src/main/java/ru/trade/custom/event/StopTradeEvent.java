package ru.trade.custom.event;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import ru.trade.Trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StopTradeEvent extends PlayerEvent {
    private final Inventory inventory;

    private static final HandlerList HANDLERS = new HandlerList();

    public StopTradeEvent(@NonNull Player who) {
        super(who);
        this.inventory = Trade.getAPI().getTradeInventory(who);
    }

    public boolean isTrading() {
        return Trade.getAPI().isTrading(player);
    }

    public List<ArmorStand> getArmorStands() {
        List<ArmorStand> stands = new ArrayList<>();
        for (Map.Entry<ArmorStand, String> key :
                Trade.getAPI().standMap.entrySet()) {
            if (key.getValue().equalsIgnoreCase(player.getName())) {
                stands.add(key.getKey());
            }
        }
        return stands;
    }

    public List<ItemStack> getItems() {
        if (isTrading())
            return Arrays.asList(inventory.getContents());
        return new ArrayList<>();
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
