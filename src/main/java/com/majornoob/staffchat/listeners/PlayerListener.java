package com.majornoob.staffchat.listeners;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.util.Methods;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Jake on 3/8/14.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onExclamationChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer && event.getMessage().startsWith("!") && Main.config.getBoolean("enable-exclamation-trigger")) {
            ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
                    if (player.hasPermission("staffchat.receive")) {
                        Methods.sendMessage(player, sender, event.getMessage().substring(1, event.getMessage().length()));
                    }
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRegularChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer && (!event.getMessage().startsWith("/") && !event.getMessage().startsWith("!"))) {
            ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
            if (sender.hasPermission("staffchat.send") && Main.instance.toggledChatters.contains(sender.getUniqueId())) {
                for (ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
                    if (player.hasPermission("staffchat.receive")) {
                        Methods.sendMessage(player, sender, event.getMessage());
                    }
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerLogin(PostLoginEvent event) {
        ProxiedPlayer p = event.getPlayer();

        if (Main.instance.toggledChatters.contains(p.getUniqueId())) {
            p.sendMessage(new TextComponent("[Staff Chat] You are currently in the Staff Chat!"));
        }
    }
}
