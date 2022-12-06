package ru.trade.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.trade.Trade;
import ru.trade.utils.Replacer;

public class TradeExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (label.equalsIgnoreCase("Trade")) {
            if (args.length == 0)
                if (player.hasPermission("trade")) {
                    Trade.getAPI().startTrade(player);
                } else player.sendMessage(Replacer.setPlaceHolderInConfig("haventPerms", player));
            if (player.hasPermission("trade.admin")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("stop")) {
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if (targetPlayer != null) {
                            if (Trade.getAPI().isTrading(targetPlayer)) {
                                Trade.getAPI().stopTrade(targetPlayer);
                                targetPlayer.closeInventory();
                            } else
                                player.sendMessage(Replacer.setPlaceHolderInConfig("stopCommandNotTrade", player, targetPlayer));
                        }
                    }
                }
            } else player.sendMessage(Replacer.setPlaceHolderInConfig("haventPerms", player));
        }
        return false;
    }
}
