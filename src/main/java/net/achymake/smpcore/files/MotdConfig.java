package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MotdConfig {
    private final SMPCore smpCore;
    public MotdConfig (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private final Message message = SMPCore.getMessage();
    private final File file = new File(SMPCore.getInstance().getDataFolder(), "motd.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public boolean exist() {
        return file.exists();
    }
    public void setup() {
        if (!file.exists()) {
            List<String> motd = new ArrayList<>();
            motd.add("&6Welcome back&f {0}");
            motd.add("&6We missed you!");
            config.addDefault("message-of-the-day", motd);
            List<String> welcome = new ArrayList<>();
            config.addDefault("welcome", welcome);
            welcome.add("&6Welcome&f {0}&6 to the server!");
            List<String> rules = new ArrayList<>();
            rules.add("&6Rules:");
            rules.add("&61.&f No server crashing");
            rules.add("&62.&f No griefing");
            rules.add("&63.&f No Monkieng around! uhh?");
            config.addDefault("rules", rules);
            List<String> help = new ArrayList<>();
            help.add("&6Help:");
            help.add("- www.your-server.net/help");
            config.addDefault("help", help);
            config.options().copyDefaults(true);
            save();
        }
    }
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public FileConfiguration get() {
        return config;
    }
    public boolean motdExist(String motd) {
        return config.isList(motd);
    }
    public void sendMotd(CommandSender sender, String motd) {
        for (String messages: config.getStringList(motd)) {
            message.send(sender, MessageFormat.format(messages, sender.getName()));
        }
    }
    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
        save();
    }
}
