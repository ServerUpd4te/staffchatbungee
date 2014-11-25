package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
                .replace("%server", sender.getServer().getInfo().getName())
                .replace("%user", sender.getName())
                .replace("%message", message);
        to.sendMessage(new TextComponent(this.tac(m)));
    }

    public void runReload(CommandSender sender) {
        Configuration config_lock = this.instance.getConfig();
        this.instance.setConfig(null);
        Configuration lang_lock = this.instance.getLanguage();
        this.instance.setLanguage(null);

        try {
            this.instance.setConfig(ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    new File(this.instance.getDataFolder(), "conf.yml")));
            this.instance.setLanguage(ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    new File(this.instance.getDataFolder(), "lang.yml")));
        } catch (IOException ex) {
            this.instance.setConfig(config_lock);
            this.instance.setLanguage(lang_lock);
        }

        this.instance.reregisterCommands();

        this.sendLM(sender, "plugin-reloaded-msg");
    }

    public void sendStrippedLM(CommandSender cs, String s) {
        cs.sendMessage(TextComponent.fromLegacyText(this.instance.getLanguage().getString(s)));
    }
    public void sendLM(CommandSender cs, String s) {
        String m = this.instance.getLanguage().getString("prefix") + " " + this.instance.getLanguage().getString(s);
        cs.sendMessage(TextComponent.fromLegacyText(this.tac(m)));
    }

    private String tac(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
