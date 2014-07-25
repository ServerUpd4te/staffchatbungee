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
    public ScreloadCommand() {
        super("screload", "staffchat.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Configuration backup = Main.config;
        Main.config = null;
        try {
            Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.instance.getDataFolder(), "conf.yml"));
            sender.sendMessage(new TextComponent("You reloaded the Staff Chat configuration."));
            Main.instance.getLogger().info("Configuration reloaded by " + sender.getName());
        } catch (IOException ex) {
            sender.sendMessage(new TextComponent("The configuration could not be reloaded."));
            Main.instance.getLogger().warning("Failed to reload config, restoring...");
            Main.config = backup;
            Main.instance.getLogger().info("Restored previous configuration.");
        }
    }
}
