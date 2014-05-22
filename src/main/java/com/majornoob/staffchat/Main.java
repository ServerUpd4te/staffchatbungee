package com.majornoob.staffchat;

import com.majornoob.staffchat.util.Loader;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;

/**
 * Created by Jake on 3/8/14.
 */
public class Main extends Plugin {
    public final ArrayList<String> toggledChatters = new ArrayList<>();
    public static Main instance;

    private static Configuration config;

    @Override
    public void onEnable() {
        instance = this;
        Loader.load();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void setConfig(Configuration config) {
        Main.config = config;
    }
}
