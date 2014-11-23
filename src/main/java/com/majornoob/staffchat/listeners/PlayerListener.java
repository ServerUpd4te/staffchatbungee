package com.majornoob.staffchat.listeners;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.managers.PlayerManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
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
    public void onChat(ChatEvent event) {
        ProxiedPlayer sender;
        String trueMessage = event.getMessage();
        if (trueMessage.startsWith("/")) return;
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
            else sender = (ProxiedPlayer) event.getSender();
        if (!PlayerManager.playerExists(sender) && !trueMessage.startsWith("!")) return;
        if (trueMessage.startsWith("!")) {
            if (this.instance.getConfig().getBoolean("enable-exclamation-trigger")) {
                if (!(trueMessage.length() > 1)) return;
                trueMessage = trueMessage.substring(1, trueMessage.length());
            } else return;
        }
        if (!sender.hasPermission("staffchat.send")) return;
        event.setCancelled(true);
        for (ProxiedPlayer receiver : this.instance.getProxy().getPlayers()) {
            if (!receiver.hasPermission("staffchat.receive")) continue;
            this.instance.getMethods().sendMessage(receiver, sender, trueMessage);
        }
    }

    @EventHandler
    public void onPlayerLogin(PostLoginEvent event) {
        if (this.instance.getConfig().getBoolean("enable-persisting-presence")) {
            final ProxiedPlayer p = event.getPlayer();
            if (PlayerManager.playerExists(p.getUniqueId())) {
                this.instance.getProxy().getScheduler().schedule(this.instance, new Runnable() {
                    @Override
                    public void run() {
                        instance.getMethods().sendLM(p, "user-logged-in-chat");
                    }
                }, 500L, TimeUnit.MILLISECONDS);
            }
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerDisconnectEvent event) {
        if (!this.instance.getConfig().getBoolean("enable-persisting-presence")) {
            PlayerManager.removePlayer(event.getPlayer());
        }
    }
}
