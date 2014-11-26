package com.jdersen.staffchat;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jake on 3/8/14.
 */
public class MainListener implements Listener {
    private Main instance;
    public MainListener(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer sender;
        String trueMessage = event.getMessage();
        // If the message is not a command
        if (trueMessage.startsWith("/")) {
            return;
        }
        // and the sender is a ProxiedPlayer (player)
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
        else sender = (ProxiedPlayer) event.getSender();
        // and the player is toggled or simply trying to send a message with "!"
        if (!PlayerManager.playerExists(sender) && !trueMessage.startsWith("!")) return;
        else if (trueMessage.startsWith("!")) {
            if (ConfigManager.getConf().getBoolean("enable-exclamation-trigger")) {
                if (!(trueMessage.length() > 1)) return;
                trueMessage = trueMessage.substring(1, trueMessage.length());
            } else return;
        }
        // and the player has the proper permission
        if (!sender.hasPermission("staffchat.send")) return;
        // then cancel the normal message and perform event
        event.setCancelled(true);
        // for every person on the server
        for (ProxiedPlayer receiver : this.instance.getProxy().getPlayers()) {
            // if they have the receive permission
            if (!receiver.hasPermission("staffchat.receive")) continue;
            // send a message
            this.instance.getMisc().sendMessage(receiver, sender, trueMessage.split(" "));
        }
    }

    @EventHandler
    public void onPlayerLogin(PostLoginEvent event) {
        // if persisting presence is enabled
        if (ConfigManager.getConf().getBoolean("enable-persisting-presence")) {
            final ProxiedPlayer p = event.getPlayer();
            // and this player is still toggled
            if (PlayerManager.playerExists(p.getUniqueId())) {
                // let them know they're still toggled
                this.instance.getProxy().getScheduler().schedule(this.instance, new Runnable() {
                    @Override
                    public void run() {
                        instance.getMisc().sendLM(p, "user-logged-in-chat", false);
                    }
                }, 500L, TimeUnit.MILLISECONDS);
            }
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerDisconnectEvent event) {
        // if persisting presence is not enabled
        if (!ConfigManager.getConf().getBoolean("enable-persisting-presence")) {
            // and this player is toggled
            if (PlayerManager.playerExists(event.getPlayer())) {
                // remove them from the toggle list
                PlayerManager.removePlayer(event.getPlayer());
            }
        }
    }
}
