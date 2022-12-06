package ru.trade.custom.event;

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

public class StartTradeEvent extends PlayerEvent {

    private final Inventory inventory;
    private static final HandlerList HANDLERS = new HandlerList();

    public StartTradeEvent(@NonNull Player who) {
        super(who);
        this.inventory = Trade.getAPI().getTradeInventory(who);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isTrading() {
        return Trade.getAPI().isTrading(player);
    }

    public List<ItemStack> getItemsFromTradeInventory(Player player) {
        if (isTrading())
            return Arrays.asList(Trade.getAPI().tradeInventoryMap.get(player.getName()).getContents());
        return new ArrayList<>();
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


}
