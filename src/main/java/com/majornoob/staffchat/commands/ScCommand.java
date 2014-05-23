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
        super("sc", "staffchat.use");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&cSorry, only players can join the staff chat.")));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer)sender;

        if (args.length == 0) {
            if (sender.hasPermission("staffchat.toggle")) {
                if (Main.instance.toggledChatters.contains(p.getUniqueId())) {
                    Main.instance.toggledChatters.remove(p.getUniqueId());
                    sender.sendMessage(new TextComponent("You have left the staff chat. Type /sc again to re-join."));
                } else {
                    Main.instance.toggledChatters.add(p.getUniqueId());
                    sender.sendMessage(new TextComponent("You have joined the staff chat. Type /sc again to leave."));
                }
            }
        } else if (args.length >= 1) {
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer pl : Main.instance.getProxy().getPlayers()) {
                    if (pl.hasPermission("staffchat.receive")) {
                        Methods.sendMessage(p, pl, Methods.formatMsg(args));
                    }
                }
            }
        }
    }
}
