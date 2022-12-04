package ru.trade.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.trade.Trade;

public class PlaceHolder {

    public static String setPlaceHolderInConfig(String key, Player player, Player targetPlayer) {
        String cfg = Trade.getInstance().getConfig().getString(key);

        return ChatColor.translateAlternateColorCodes('&', cfg.replaceAll("%targetPlayer%", targetPlayer.getName()).replaceAll("%player%", player.getName()));
    }

    public static String
    setPlaceHolderInConfig(String key, Player player) {
        String cfg = Trade.getInstance().getConfig().getString(key);

        return ChatColor.translateAlternateColorCodes('&', cfg.replaceAll("%player%", player.getName()));
    }

    public static String setPlaceHolderInConfig(String key) {
        String cfg = Trade.getInstance().getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', cfg);
    }

    public static String setPlaceHolderInConfig(String key, double price) {
        String cfg = Trade.getInstance().getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', cfg.replaceAll("%price%", String.valueOf(price)));
    }
}
