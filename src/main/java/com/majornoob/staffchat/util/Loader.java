package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.commands.ScCommand;
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

            if (!new File(instance.getDataFolder(), "conf.yml").exists()) {
                FileUtils.copy(instance.getResourceAsStream("conf.yml"), new File(instance.getDataFolder(), "conf.yml"));
            }

            try {

                Main.setConfig(
                        ConfigurationProvider.getProvider(YamlConfiguration.class)
                                .load(new File(instance.getDataFolder(), "conf.yml"))
                );

            } catch (IOException e) {
                instance.getLogger().info("There was an I/O error during configuration loading. Stopping plugin.");

                instance.getProxy().getPluginManager().unregisterCommands(instance);
                instance.getProxy().getPluginManager().unregisterListeners(instance);
            }
        }

        instance.getLogger().info("Preparing commands...");
        instance.getProxy().getPluginManager().registerCommand(instance, new ScCommand(instance));
        instance.getProxy().getPluginManager().registerListener(instance, new PlayerListener(instance));
    }
}
