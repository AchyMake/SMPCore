package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {
    private static final SMPCore smpCore = SMPCore.getInstance();
    public static void setup() {
        smpCore.reload();
        smpCore.getJailConfig().setup();
        smpCore.getKitConfig().setup();
        smpCore.getMotdConfig().setup();
        smpCore.getSpawnConfig().setup();
        smpCore.getWarpConfig().setup();
    }
    public static void reload() {
        smpCore.reload();
        smpCore.getJailConfig().reload();
        smpCore.getKitConfig().reload();
        smpCore.getMotdConfig().reload();
        smpCore.getSpawnConfig().reload();
        smpCore.getWarpConfig().reload();
        smpCore.getPlayerConfig().getCommandCooldown().clear();
        for (OfflinePlayer offlinePlayer : smpCore.getServer().getOfflinePlayers()) {
            File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
            if (file.exists()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                try {
                    config.load(file);
                    config.options().copyDefaults(true);
                    config.save(file);
                } catch (IOException | InvalidConfigurationException e) {
                    Message.sendLog(e.getMessage());
                }
            }
        }
    }
}