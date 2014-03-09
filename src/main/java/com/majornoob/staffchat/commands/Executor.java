package com.majornoob.staffchat.commands;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.util.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * Created by Jake on 3/8/14.
 */
public class Executor {
    private Main plugin;

    public Executor(Main instance) {
        this.plugin = instance;
    }

    public boolean execute(Player sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sc")) {
            if (args.length == 0) {
                if (sender.hasPermission("staffchat.toggle")) {
                    if (this.plugin.toggledChatters.contains(sender.getName())) {
                        this.plugin.toggledChatters.remove(sender.getName());
                        sender.sendMessage("You have left the staff chat. Type /sc again to re-join.");
                    } else {
                        this.plugin.toggledChatters.add(sender.getName());
                        sender.sendMessage("You have joined the staff chat. Type /sc again to leave.");
                    }
                }
            } else if (args.length >= 1) {
                if (sender.hasPermission("staffchat.send")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission("staffchat.receive")) {
                            Methods.sendMessage(p, Methods.formatMsg(args));
                        }
                    }
                }
            }
        }
        return true;
    }
}
