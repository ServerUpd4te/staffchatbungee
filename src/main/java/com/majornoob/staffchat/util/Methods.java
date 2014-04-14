package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Jake on 3/9/14.
 */
public class Methods {
    public static void sendMessage(ProxiedPlayer sender, ProxiedPlayer to, String msg) {
        to.sendMessage(
                new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Main.config.getString("format")
                        .replace("%timestamp", new SimpleDateFormat(Main.config.getString("timestamp-format")).format(new Date()))
                        .replace("%server", cap(sender.getServer().getInfo().getName().toLowerCase()))
                        .replace("%user", sender.getName())
                        .replace("%message", msg))))
        );
    }

    public static String formatMsg(String[] messageParts) {
        String msg = "";
        for (int i = 0; i < messageParts.length; i++) {
            msg += messageParts[i] + " ";
        }
        return msg;
    }

    public static List<ProxiedPlayer> getOnlinePlayers() {
        List<ProxiedPlayer> temp = new ArrayList();
        for (Map.Entry<String, ServerInfo> serverMap : ProxyServer.getInstance().getServers().entrySet()) {
            for (ProxiedPlayer p : serverMap.getValue().getPlayers()) {
                temp.add(p);
            }
        }
        return temp;
    }

    private static String cap(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
