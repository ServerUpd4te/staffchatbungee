package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Jake on 3/9/14.
 */
public class Methods {
    public static void sendMessage(ProxiedPlayer to, ProxiedPlayer sender, String msg) {
        to.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Main.config.getString("format")
                .replace("%timestamp", new SimpleDateFormat(Main.config.getString("timestamp-format")).format(new Date()))
                .replace("%server", cap(sender.getServer().getInfo().getName().toLowerCase()))
                .replace("%user", sender.getName())
                .replace("%message", msg)))));
    }

    public static String formatMsg(String[] messageParts) {
        String msg = "Failed to construct message! (Plugin Error -> Please Report)";
        for (int i = 0; i < messageParts.length; i++) {
            msg = "";
            msg += messageParts[i] + " ";
            msg = msg.substring(0, msg.length()-1);
        }
        return msg;
    }

    private static String cap(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
