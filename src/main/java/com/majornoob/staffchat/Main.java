package com.majornoob.staffchat;

import com.majornoob.staffchat.commands.Executor;
import com.majornoob.staffchat.listeners.PlayerListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by Jake on 3/8/14.
 */
public class Main extends JavaPlugin {
    public final ArrayList<String> toggledChatters = new ArrayList<String>();
    public static FileConfiguration config;
    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        prepare();
        loadConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            return new Executor(this).execute((Player) sender, command, args);
        } else {
            sender.sendMessage("This command can only be run by players.");
        }
        return true;
    }

    public void prepare() {
        getDataFolder().mkdir();
        getDataFolder().setExecutable(true);
        getDataFolder().setWritable(true);
        getDataFolder().setReadable(true);
    }

    public void loadConfig() {
        saveDefaultConfig();
        config = getConfig();
    }
}
