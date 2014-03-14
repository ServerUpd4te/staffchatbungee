package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;
import net.cubespace.Yamler.Config.Comments;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.Yamler.Config.InvalidConfigurationException;

import java.io.File;

/**
 * Created by Jake on 3/13/14.
 */
public class Configuration extends Config {
    public Configuration(Main instance) {
        CONFIG_HEADER = new String[]{"Configuration of StaffChat Bungee 1.0.0"};
        CONFIG_FILE = new File(instance.getDataFolder(), "conf.yml");
        try {
            this.init();
        } catch (InvalidConfigurationException e) {
            instance.getLogger().severe("Something was wrong with the configuration file!");
            instance.getProxy().stop();
        }
    }

    @Comments({
            "You can modify the format of the staff chat",
            "here. The '^' character is the equivalent of",
            "'&' in Minecraft (color code!) If you don't",
            "want a piece to display, remove its '%' message.",
            "e.g. removing \"%playername\" will remove the",
            "player's name from the message. Good luck and have fun."
    })
    public String messageFormat = "^6{Staff}{%servername}^d %playername: ^c%message";
}
