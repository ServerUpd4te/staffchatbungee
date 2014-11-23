package com.majornoob.staffchat;

import com.majornoob.staffchat.commands.MainCommand;
import com.majornoob.staffchat.listeners.PlayerListener;
import com.majornoob.staffchat.managers.PlayerManager;
import com.majornoob.staffchat.util.FileUtils;
import com.majornoob.staffchat.util.Methods;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jake on 3/8/14.
 */
public class Main extends Plugin {
    private static Configuration config = null;
    private static Configuration language = null;
    private Methods methods;

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
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "conf.yml"));
            language = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "lang.yml"));
        } catch (IOException e) {
            e.printStackTrace();
            getProxy().getPluginManager().unregisterCommands(this);
            getProxy().getPluginManager().unregisterListeners(this);
        }
        getProxy().getPluginManager().registerCommand(this, new MainCommand(this));
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
        methods = new Methods(this);
    }

    @Override
    public void onDisable() {
        PlayerManager.erasePlayers();
    }

    public void reregisterCommands() {
        getProxy().getPluginManager().unregisterCommands(this);
        getProxy().getPluginManager().registerCommand(this, new MainCommand(this));
    }

    public Configuration getConfig() {
        return config;
    }
    public void setConfig(Configuration config) {
        com.majornoob.staffchat.Main.config = config;
    }

    public Configuration getLanguage() {
        return language;
    }
    public void setLanguage(Configuration language) {
        com.majornoob.staffchat.Main.language = language;
    }

    public Methods getMethods() {
        return methods;
    }
}
