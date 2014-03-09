package com.majornoob.staffchat.listeners;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.util.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Jake on 3/8/14.
 */
public class PlayerListener implements Listener {
    private Main plugin;

    public PlayerListener(Main instance) {
        this.plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (this.plugin.toggledChatters.contains(event.getPlayer().getName()) && event.getPlayer().hasPermission("staffchat.send")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("staffchat.receive")) {
                    Methods.sendMessage(p, event.getMessage());
                }
            }
            event.setCancelled(true);
        }
    }
}
