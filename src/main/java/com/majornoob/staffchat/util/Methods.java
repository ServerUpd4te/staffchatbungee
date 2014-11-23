package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by Jake on 3/9/14.
 */
public class Methods {
    private Main instance;

    public Methods(Main instance) {
        this.instance = instance;
    }

    public void sendMessage(ProxiedPlayer to, ProxiedPlayer sender, String[] parts) {
        String message = "";
        for (String part : parts) {
            message += part+" ";
        }

        sendMessage(to, sender, message.substring(0, (message.length() - 1)));
    }

    public void sendMessage(ProxiedPlayer to, ProxiedPlayer sender, String message) {
        String m = this.instance.getConfig()
                .getString("format")
                    .replace("%server", this.tac(sender.getServer().getInfo().getName()))
                    .replace("%user", this.tac(sender.getName()))
                    .replace("%message", this.tac(message));

        to.sendMessage(new TextComponent(m));
    }

    // shortened version of ChatColor.translateAlternateColorCodes();
    private String tac(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
