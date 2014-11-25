package com.jdersen.staffchat;

import com.jdersen.staffchat.Misc;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jacob on 11/25/2014.
 */
/*
    Static handling of Configuration
 */
public class ConfigManager {
    private static Configuration conf = null;
    private static Configuration lang = null;
    public static Configuration getConf() {
        return conf;
    }

    public static Configuration getLang() {
        return lang;
    }

    public static void loadIntoMemory(File directoryToLook) {
        boolean isInitialization = false;
        if (!(conf == null || lang == null)) {
            isInitialization = true;
        }
        Configuration conf_lock = null;
        Configuration lang_lock = null;
        if (!isInitialization) {
            conf_lock = conf;
            lang_lock = lang;
        }
        try {
            conf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    new File(directoryToLook, "conf.yml")
            );
            lang = ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    new File(directoryToLook, "lang.yml")
            );
        } catch (IOException e) {
            e.printStackTrace();
            if (isInitialization) Misc.shutdownPlugin();
            else {
                conf = conf_lock;
                lang = lang_lock;
            }
        }
    }
}
