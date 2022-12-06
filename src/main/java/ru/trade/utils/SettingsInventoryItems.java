package ru.trade.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsInventoryItems {
    public static ItemStack priceItem = new ItemStack(Material.SUNFLOWER);
    public static ItemMeta priceMeta = priceItem.getItemMeta();

    public static ItemStack cancelItem = new ItemStack(Material.BARRIER);
    public static ItemMeta cancelMeta = cancelItem.getItemMeta();

    public static ItemStack buyButton = new ItemStack(Material.GREEN_WOOL);
    public static ItemMeta buyButtonMeta = buyButton.getItemMeta();


    static {
        priceMeta.setDisplayName(Replacer.setPlaceHolderInConfig("addPriceButton"));
        priceItem.setItemMeta(priceMeta);

        cancelMeta.setDisplayName(Replacer.setPlaceHolderInConfig("backButton"));
        cancelItem.setItemMeta(cancelMeta);

        buyButtonMeta.setDisplayName(Replacer.setPlaceHolderInConfig("buyButton"));
        buyButton.setItemMeta(buyButtonMeta);
    }
}
