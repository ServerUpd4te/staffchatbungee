package com.majornoob.staffchat;

import com.majornoob.staffchat.commands.ScCommand;
import com.majornoob.staffchat.commands.ScreloadCommand;
import com.majornoob.staffchat.listeners.PlayerListener;
import com.majornoob.staffchat.util.FileUtils;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jake on 3/8/14.
 */
public class Main extends Plugin {
    public final ArrayList<UUID> toggledChatters = new ArrayList<>();
    public static Configuration config = null;
    public static Configuration language = null;

    @Override
    public void onEnable() {
        if (! getDataFolder().exists()) {
            getDataFolder().mkdir();
            getDataFolder().setReadable(true);
            getDataFolder().setWritable(true);
        }
        if (! new File(getDataFolder(), "conf.yml").exists()) {
            FileUtils.copy(getResourceAsStream("conf.yml"), new File(getDataFolder(), "conf.yml"));
        }
        if (!new File(getDataFolder(), "lang.yml").exists()) {
            FileUtils.copy(getResourceAsStream("lang.yml"), new File(getDataFolder(), "lang.yml"));
        }

        getLogger().info("Preparing commands...");
        getProxy().getPluginManager().registerCommand(this, new ScCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ScreloadCommand(this));

        getLogger().info("Preparing events...");
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));

        getLogger().info("Loading configurations...");
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "conf.yml"));
            language = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "lang.yml"));
        } catch (IOException e) {
            e.printStackTrace();
            getProxy().getPluginManager().unregisterCommands(this);
            getProxy().getPluginManager().unregisterListeners(this);
        }
    }
}
