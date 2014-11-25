package com.majornoob.staffchat;

import com.majornoob.staffchat.commands.MainCommand;
import com.majornoob.staffchat.managers.ConfigurationManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jake on 3/9/14.
 */
public class Misc {
    private static Main instance;

    public Misc(Main instance) {
        Misc.instance = instance;
    }

    public void sendMessage(ProxiedPlayer to, ProxiedPlayer sender, String[] parts) {
        String message = "";
        for (String part : parts) message += part + " ";
        message = message.substring(0, (message.length() - 1));
        message = ConfigurationManager.getConf().getString("format")
                .replace("%server", sender.getServer().getInfo().getName())
                .replace("%user", sender.getName())
                .replace("%message", message);
        to.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
    }

    public void sendLM(CommandSender cs, String s, boolean strip) {
        s = ConfigurationManager.getLang().getString("prefix") + " " + ConfigurationManager.getLang().getString(s);
        cs.sendMessage(TextComponent.fromLegacyText((strip)?s:ChatColor.translateAlternateColorCodes('&', s)));
    }

    public void reloadPlugin(CommandSender sender) {
        ConfigurationManager.loadIntoMemory(instance.getDataFolder());
        instance.getProxy().getPluginManager().unregisterCommands(instance);
        instance.getProxy().getPluginManager().registerCommand(instance, new MainCommand(instance));
        this.sendLM(sender, "plugin-reloaded-msg", false);
    }

    public static void shutdownPlugin() {
        Misc.instance.getProxy().getPluginManager().unregisterCommands(instance);
        Misc.instance.getProxy().getPluginManager().unregisterListeners(instance);
    }

    public static boolean copy(InputStream from, File to) {
        try (InputStream input = from) {
            if (!to.exists()) if (!to.createNewFile()) throw new IOException("File extraction failed!");
            try (FileOutputStream output = new FileOutputStream(to)) {
                byte[] b = new byte[8192];
                int length;
                while ((length = input.read(b)) > 0) output.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
