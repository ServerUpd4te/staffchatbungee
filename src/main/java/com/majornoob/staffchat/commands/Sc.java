package com.majornoob.staffchat.commands;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.util.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Jake on 3/8/14.
 */
public class Sc extends Command {
    private Main plugin;

    public Sc(Main instance) {
        super("sc");
        this.plugin = instance;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("staffchat.toggle")) {
                if (this.plugin.toggledChatters.contains(sender.getName())) {
                    this.plugin.toggledChatters.remove(sender.getName());
                    sender.sendMessage(new TextComponent("You have left the staff chat. Type /sc again to re-join."));
                } else {
                    this.plugin.toggledChatters.add(sender.getName());
                    sender.sendMessage(new TextComponent("You have joined the staff chat. Type /sc again to leave."));
                }
            }
        } else if (args.length >= 1) {
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer p : Methods.getOnlinePlayers()) {
                    if (p.hasPermission("staffchat.receive")) {
                        Methods.sendMessage((ProxiedPlayer)sender, p, Methods.formatMsg(args));
                    }
                }
            }
        }
    }
}
