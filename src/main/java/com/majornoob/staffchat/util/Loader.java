package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.commands.ScCommand;
import com.majornoob.staffchat.commands.ScreloadCommand;
import com.majornoob.staffchat.listeners.PlayerListener;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by jake on 5/21/14.
 */
public class Loader {
    private Loader() {}
    public static void load() {
        Main instance = Main.instance;

        instance.getLogger().info("Preparing file backend...");
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
            instance.getDataFolder().setWritable(true);
            instance.getDataFolder().setReadable(true);
        }

        if (!new File(instance.getDataFolder(), "conf.yml").exists()) {
            FileUtils.copy(instance.getResourceAsStream("conf.yml"), new File(instance.getDataFolder(), "conf.yml"));
        }

        if (!new File(instance.getDataFolder(), "lang.yml").exists()) {
            FileUtils.copy(instance.getResourceAsStream("lang.yml"), new File(instance.getDataFolder(), "lang.yml"));
        }

        instance.getLogger().info("Preparing commands...");
        instance.getProxy().getPluginManager().registerCommand(instance, new ScCommand());
        instance.getProxy().getPluginManager().registerCommand(instance, new ScreloadCommand());

        instance.getLogger().info("Preparing events...");
        instance.getProxy().getPluginManager().registerListener(instance, new PlayerListener());
    }
}
