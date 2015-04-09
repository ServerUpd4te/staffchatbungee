package com.jdersen.staffchat;

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
                ConfigManager.getConf().getString("main-command"),
                null,
                ConfigManager.getConf().getStringList("aliases").toArray(
                        new String[ConfigManager.getConf().getStringList("aliases").size()]
                )
        );
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        boolean senderIsPlayer = false;
        if ((sender instanceof ProxiedPlayer)) senderIsPlayer = true;
        ProxiedPlayer p = ((senderIsPlayer) ? ((ProxiedPlayer)sender) : null);

        // Player only
        if (!senderIsPlayer) {
            this.instance.getMisc().sendLM(sender, "plugin-player-only", false);
        }
        else if (args.length == 0) {
            if (sender.hasPermission("staffchat.toggle")) {
                if (!PlayerManager.playerExists(p)) {
                    PlayerManager.addPlayer(p);
                    this.instance.getMisc().sendLM(sender, "user-entered-chat", false);
                } else {
                    PlayerManager.removePlayer(p);
                    this.instance.getMisc().sendLM(sender, "user-left-chat", false);
                }
            } else this.instance.getMisc().sendLM(sender, "plugin-doesnt-exist", true);
        }
        else if (args.length > 0) {
            if (sender.hasPermission("staffchat.send")) {
                for (ProxiedPlayer player : this.instance.getProxy().getPlayers()) {
                    if (player.hasPermission("staffchat.receive")) {
                        this.instance.getMisc().sendMessage(player, p, args);
                    }
                }
            } else this.instance.getMisc().sendLM(sender, "plugin-doesnt-exist", true);
        }
    }
}
