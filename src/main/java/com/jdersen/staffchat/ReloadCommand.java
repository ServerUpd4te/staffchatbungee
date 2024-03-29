package com.jdersen.staffchat;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {
    private Main instance;

    public ReloadCommand(Main instance) {
        super("screload", null, new String[0]);
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("staffchat.admin")) {
            this.instance.getMisc().reloadPlugin(sender);
        } else {
            this.instance.getMisc().sendLMNoPrefix(sender, "plugin-doesnt-exist", true);
        }
    }
}
