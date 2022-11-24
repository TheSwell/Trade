package ru.trade.utils;

import org.bukkit.configuration.file.FileConfiguration;
import ru.trade.Trade;

import java.util.HashMap;
import java.util.Map;

public class ConfigDefaultSettings {
    private static final FileConfiguration config = Trade.getInstance().getConfig();
    private static final Map<String, Object> message = new HashMap<>();

    static {
        message.put("stopCommandNotTrade", "Игрок %targetPlayer% не торгует");
        message.put("stoppedTrade", "Игрок %player% прекратил торговлю.");
        message.put("haventMoney", "У вас недостаточно денег.");
        message.put("symbolsError", "Используйте только \".\" или \",\"");
        message.put("thisPlayerEditTrade", "Этот игрок редактирует магазин, ожидайте.");
        message.put("TheSlotIsBusy", "Этот слот занят.");
        message.put("priceHelp", "Напишите цену в чат. (Пример: 10.53)");
        message.put("youStoppedTrade", "Вы прекратили торговлю");
        message.put("tradeName", "&9Торговая точка игрока %player%");
        message.put("price", "Цена: %price%$");

        message.put("addPriceButton", "§6Указать цену");
        message.put("backButton", "&4Назад");
        message.put("buyButton", "&2Купить");



        message.put("haventPerms", "У вас недостаточно прав.");

        config.addDefaults(message);
        config.options().copyDefaults(true);
        Trade.getInstance().saveConfig();
    }
}
