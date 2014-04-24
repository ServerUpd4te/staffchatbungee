package com.majornoob.staffchat;

import com.majornoob.staffchat.commands.Sc;
import com.majornoob.staffchat.listeners.PlayerListener;
import com.majornoob.staffchat.util.FileManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jake on 3/8/14.
 */
public class Main extends Plugin {
    public final ArrayList<String> toggledChatters = new ArrayList<>();
    public static Configuration config = null;
    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        config = this.loadConfiguration();
        if (config == null) {
            getLogger().info("Configuration could not be loaded. Stopping plugin.");
            this.stop();
        }
        getProxy().getPluginManager().registerCommand(this, new Sc(this));
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
    }

    private Configuration loadConfiguration() {
        this.prepareBackend();

        if (!new File(getDataFolder(), "conf.yml").exists()) FileManager.copy(getResourceAsStream("conf.yml"), new File(getDataFolder(), "conf.yml"));
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "conf.yml"));
        } catch (IOException e) {
            getLogger().info("There was an I/O error during configuration loading. Stopping plugin.");
            this.stop();
        }
        return null;
    }

    private void prepareBackend() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            getDataFolder().setWritable(true);
            getDataFolder().setReadable(true);
            getDataFolder().setExecutable(true);
        }
    }
    
    private void stop() {
        getLogger().info("Plugin is stopping. It will no longer respond to events or commands.");
        getProxy().getPluginManager().unregisterCommands(this);
        getProxy().getPluginManager().unregisterListeners(this);
        getLogger().info("Plugin stopped.");
    }

    public static Main getInstance() {
        return plugin;
    }
}
