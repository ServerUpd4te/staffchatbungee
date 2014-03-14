package com.majornoob.staffchat.listeners;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.util.Methods;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Jake on 3/8/14.
 */
public class PlayerListener implements Listener {
    private Main plugin;

    public PlayerListener(Main instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if (!event.getMessage().startsWith("/") && event.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) event.getSender();
            if (p.hasPermission("staffchat.send") && (this.plugin.toggledChatters.contains(p.getName()) || event.getMessage().startsWith("!"))) {
                for (ProxiedPlayer player : Methods.getOnlinePlayers()) {
                    if (player.hasPermission("staffchat.receive")) {
                        Methods.sendMessage(p, player, (event.getMessage().startsWith("!")) ? event.getMessage().substring(1, event.getMessage().length()):event.getMessage());
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}
