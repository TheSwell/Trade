package ru.trade.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemsUtils {
    public ItemStack copyItemWithoutPriceLore(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            ItemStack newItem = new ItemStack(item.getType());
            newItem.setAmount(item.getAmount());
            ItemMeta meta = newItem.getItemMeta();
            meta.setDisplayName(item.getItemMeta().getDisplayName());
            List<String> lore = new ArrayList<>();
            for (String l :
                    item.getItemMeta().getLore()) {
                if (!l.equalsIgnoreCase(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1))) {
                    lore.add(l);
                }
            }
            meta.setLore(lore);
            newItem.setItemMeta(meta);
            return newItem;
        }
        return item;
    }

    public Double getItemPrice(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {

            String s = String.valueOf(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1));
            Bukkit.getLogger().info(getDoubleFromString(s) + "");
        }
        return null;
    }


    public static double getDoubleFromString(String source) {

        String number = "0";
        int length = source.length();

        boolean cutNumber = false;
        for (int i = 0; i < length; i++) {
            char c = source.charAt(i);
            if (cutNumber) {
                if (Character.isDigit(c) || c == '.' || c == ',') {
                    c = (c == ',' ? '.' : c);
                    number += c;
                } else {
                    cutNumber = false;
                    break;
                }
            } else {
                if (Character.isDigit(c)) {
                    cutNumber = true;
                    number += c;
                }
            }
        }
        return Double.parseDouble(number);
    }
}
