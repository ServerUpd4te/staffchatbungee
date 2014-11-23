package com.majornoob.staffchat.commands;

import com.majornoob.staffchat.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jake on 7/16/2014.
 */
public class ScreloadCommand extends Command {
    private Main instance;

    public ScreloadCommand(Main instance) {
        super("screload", "staffchat.admin");

        this.instance = instance;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Configuration backupConfig = Main.config;
        Configuration backupLanguage = Main.language;
        Main.config = null;
        Main.language = null;

        try {
            Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.instance.getDataFolder(), "conf.yml"));
            Main.language = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.instance.getDataFolder(), "lang.yml"));
            sender.sendMessage(new TextComponent("[Staff Chat] Reloaded"));
        } catch (IOException ex) {
            sender.sendMessage(new TextComponent("[Staff Chat] Error reloading"));
            Main.config = backupConfig;
            Main.language = backupLanguage;
        }
    }
}
