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
    private Main instance;

    public ScCommand(Main instance) {
        super("staff", null, "sc", "s");

        this.instance = instance;
    }

    public void execute(CommandSender sendr, String[] args) {
        if (!(sendr instanceof ProxiedPlayer)) {
            sendr.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&cOnly players can join the staff chat!")));
            return;
        }

        ProxiedPlayer sender = (ProxiedPlayer)sendr;

        if (args.length == 0) {
            if (sender.hasPermission("staffchat.toggle")) {
                if (this.instance.toggledChatters.contains(sender.getUniqueId())) {
                    sender.sendMessage(TextComponent.fromLegacyText(Main.language.getString("user-left-chat")));
                    this.instance.toggledChatters.remove(sender.getUniqueId());
                } else {
                    sender.sendMessage(TextComponent.fromLegacyText(Main.language.getString("user-entered-chat")));
                    this.instance.toggledChatters.add(sender.getUniqueId());
                }
            }
        } else if (args.length > 0) {
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer player : this.instance.getProxy().getPlayers()) {
                    if (player.hasPermission("staffchat.receive")) {
                        Methods.sendMessage(player, sender, Methods.formatMsg(args));
                    }
                }
            }
        }
    }
}
