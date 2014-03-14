package com.majornoob.staffchat;

import com.majornoob.staffchat.commands.Sc;
import com.majornoob.staffchat.listeners.PlayerListener;
import com.majornoob.staffchat.util.Configuration;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;

/**
 * Created by Jake on 3/8/14.
 */
public class Main extends Plugin {
    public final ArrayList<String> toggledChatters = new ArrayList<>();
    public static Configuration config = null;

    @Override
    public void onEnable() {
        this.prepareConfiguration();
        getProxy().getPluginManager().registerCommand(this, new Sc(this));
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
    }

    private void prepareConfiguration() {
        config = new Configuration(this);
    }
}
