package com.majornoob.staffchat;

import com.majornoob.staffchat.util.Loader;
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
    public static Main instance;
    public static Configuration config = null;

    @Override
    public void onEnable() {
        instance = this;
        Loader.load();

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), "conf.yml"));
        } catch (IOException e) {
            e.printStackTrace();
            instance.getProxy().getPluginManager().unregisterCommands(instance);
            instance.getProxy().getPluginManager().unregisterListeners(instance);
        }
    }
}
