package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Jake on 3/9/14.
 */
public class Methods {
    public static void sendMessage(Player to, String msg) {
        to.sendMessage(ChatColor.translateAlternateColorCodes('^', Main.config.getString("message-format").replace("%playername", to.getName()).replace("%message", msg)));
    }

    public static String formatMsg(String[] messageParts) {
        String msg = "";
        for (int i = 0; i < messageParts.length; i++) {
            msg += messageParts[i] + " ";
        }
        return msg;
    }
}
