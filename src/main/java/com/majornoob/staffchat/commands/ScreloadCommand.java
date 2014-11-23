package com.majornoob.staffchat.commands;

import com.majornoob.staffchat.Main;
import net.md_5.bungee.api.CommandSender;
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
    private Main instance;

    public ScreloadCommand(Main instance) {
        super("screload", "staffchat.admin");

        this.instance = instance;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Configuration config_lock = this.instance.getConfig();
        this.instance.setConfig(null);
        Configuration lang_lock = this.instance.getLanguage();
        this.instance.setLanguage(null);

        try {
            this.instance.setConfig(ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.instance.getDataFolder(), "conf.yml")));
            this.instance.setLanguage(ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.instance.getDataFolder(), "lang.yml")));
        } catch (IOException ex) {
            this.instance.setConfig(config_lock);
            this.instance.setLanguage(lang_lock);
        }
    }
}
