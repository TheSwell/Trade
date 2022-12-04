package ru.trade;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.trade.executors.TradeExecutor;
import ru.trade.listeners.*;
import ru.trade.utils.ConfigDefaultSettings;

import java.util.logging.Logger;

public class Trade extends JavaPlugin {
    private static Trade instance;
    private static TradeAPI API;

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;


    @Override
    public void onEnable() {
        instance = this;
        API = new TradeAPI();

        FileConfiguration langConfig = this.getConfig();
        ConfigDefaultSettings configDefaultSettings = new ConfigDefaultSettings();
        saveConfig();
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(new SellerInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BuyerInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);

        getCommand("Trade").setExecutor(new TradeExecutor());
    }

    @Override
    public void onDisable() {
        for (Player onlinePlayers :
                getServer().getOnlinePlayers()) {
            API.stopTrade(onlinePlayers);

            onlinePlayers.closeInventory();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Trade getInstance() {
        return instance;
    }

    public static TradeAPI getAPI() {
        return API;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
