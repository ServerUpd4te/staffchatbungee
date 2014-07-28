package com.majornoob.staffchat.commands;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.util.Methods;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Jake on 3/8/14.
 */
public class ScCommand extends Command {
    public ScCommand() {
        super("staff", null, "sc", "s");
    }

    public void execute(CommandSender sendr, String[] args) {
        if (!(sendr instanceof ProxiedPlayer)) {
            sendr.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&cSorry, only players can join the staff chat.")));
            return;
        }

        ProxiedPlayer sender = (ProxiedPlayer)sendr;

        if (args.length == 0) {
            if (sender.hasPermission("staffchat.toggle")) {
                if (Main.instance.toggledChatters.contains(sender.getUniqueId())) {
                    Main.instance.toggledChatters.remove(sender.getUniqueId());
                    sender.sendMessage(new TextComponent("You have left the staff chat. Type /staff again to re-join."));
                } else {
                    Main.instance.toggledChatters.add(sender.getUniqueId());
                    sender.sendMessage(new TextComponent("You have joined the staff chat. Type /staff again to leave."));
                }
            }
        } else if (args.length > 0) {
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
                    if (player.hasPermission("staffchat.receive")) {
                        Methods.sendMessage(player, sender, Methods.formatMsg(args));
                    }
                }
            }
        }
    }
}
