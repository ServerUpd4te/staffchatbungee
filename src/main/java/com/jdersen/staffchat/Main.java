package com.jdersen.staffchat;

import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

/**
 * Created by Jake on 3/8/14.
 */
public class Main extends Plugin {
    private Misc misc;

    @Override
    public void onEnable() {
        File df = getDataFolder();
        if (! df.exists()) {
            df.mkdir();
            df.setWritable(true);
            df.setReadable(true);
        }
        if (! new File(getDataFolder(), "conf.yml").exists()) {
            Misc.copy(getResourceAsStream("conf.yml"), new File(getDataFolder(), "conf.yml"));
        }
        if (! new File(getDataFolder(), "lang.yml").exists()) {
            Misc.copy(getResourceAsStream("lang.yml"), new File(getDataFolder(), "lang.yml"));
        }

        ConfigManager.loadIntoMemory(getDataFolder());

        getProxy().getPluginManager().registerCommand(this, new MainCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
        getProxy().getPluginManager().registerListener(this, new MainListener(this));

        misc = new Misc(this);
    }

    @Override
    public void onDisable() {
        PlayerManager.erasePlayers();
    }

    public Misc getMisc() {
        return misc;
    }
}
