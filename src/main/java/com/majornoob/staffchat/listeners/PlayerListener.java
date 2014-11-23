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

import java.util.concurrent.TimeUnit;

/**
 * Created by Jake on 3/8/14.
 */
public class PlayerListener implements Listener {
    private Main instance;

    public PlayerListener(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onExclamationChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer && event.getMessage().startsWith("!") && Main.config.getBoolean("enable-exclamation-trigger")) {
            ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer player : this.instance.getProxy().getPlayers()) {
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
            if (sender.hasPermission("staffchat.send") && this.instance.toggledChatters.contains(sender.getUniqueId())) {
                for (ProxiedPlayer player : this.instance.getProxy().getPlayers()) {
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
        if (! Main.config.getBoolean("enable-persisting-presence")) return;

        final ProxiedPlayer p = event.getPlayer();
        if (this.instance.toggledChatters.contains(p.getUniqueId())) {
            this.instance.getProxy().getScheduler().schedule(this.instance, new Runnable() {
                @Override
                public void run() {
                    p.sendMessage(TextComponent.fromLegacyText(Main.language.getString("user-logged-in-chat")));
                }
            }, 500L, TimeUnit.MILLISECONDS);
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerDisconnectEvent event) {
        if (! Main.config.getBoolean("enable-persisting-presence")) return;

        ProxiedPlayer p = event.getPlayer();
        if (this.instance.toggledChatters.contains(p)) {
            this.instance.toggledChatters.remove(p);
        }
    }
}
