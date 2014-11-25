package com.majornoob.staffchat.commands;

import com.majornoob.staffchat.Main;
import com.majornoob.staffchat.managers.PlayerManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Jake on 3/8/14.
 */
public class MainCommand extends Command {
    private Main instance;

    public MainCommand(Main instance) {
        super(
                instance.getConfig().getString("main-command"),
                null,
                instance.getConfig().getStringList("aliases").toArray(new String[0]) //TODO: Change for performance
        );
        this.instance = instance;
    }

    public void execute(CommandSender sender, String[] args) {
        boolean senderIsPlayer = false;
        if ((sender instanceof ProxiedPlayer)) senderIsPlayer = true;
        ProxiedPlayer p = ((senderIsPlayer) ? ((ProxiedPlayer)sender) : null);

        // Multiple senders allowed
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            this.instance.getMethods().runReload(sender);
            return;
        }

        // Player only
        if (!senderIsPlayer) {
            this.instance.getMethods().sendLM(sender, "plugin-player-only");
        }
        else if (args.length == 0) {
            if (sender.hasPermission("staffchat.toggle")) {
                if (!PlayerManager.playerExists(p)) {
                    PlayerManager.addPlayer(p);
                    this.instance.getMethods().sendLM(sender, "user-entered-chat");
                } else {
                    PlayerManager.removePlayer(p);
                    this.instance.getMethods().sendLM(sender, "user-left-chat");
                }
            } else this.instance.getMethods().sendStrippedLM(sender, "plugin-doesnt-exist");
        }
        else if (args.length > 0) {
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer player : this.instance.getProxy().getPlayers()) {
                    if (player.hasPermission("staffchat.receive")) {
                        this.instance.getMethods().sendMessage(player, p, args);
                    }
                }
            } else this.instance.getMethods().sendStrippedLM(sender, "plugin-doesnt-exist");
        }
    }
}
