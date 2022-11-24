package ru.trade.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.trade.Trade;
import ru.trade.TradeAPI;
import ru.trade.utils.PlaceHolder;

public class TradeExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (label.equalsIgnoreCase("Trade")) {
            if (args.length == 0)
                if (player.hasPermission("trade")) {
                    Trade.getAPI().createTradeInventory(player);
                } else player.sendMessage(PlaceHolder.setPlaceHolderInConfig("haventPerms", player));
            if (player.hasPermission("trade.admin")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("stop")) {
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if (targetPlayer != null) {
                            if (Trade.getAPI().hasTradeInventory(targetPlayer)) {
                                Trade.getAPI().removeTradeInventory(targetPlayer);
                                targetPlayer.closeInventory();
                            } else
                                player.sendMessage(PlaceHolder.setPlaceHolderInConfig("stopCommandNotTrade", player, targetPlayer));
                        }
                    }
                }
            } else player.sendMessage(PlaceHolder.setPlaceHolderInConfig("haventPerms", player));
        }
        return false;
    }
}
