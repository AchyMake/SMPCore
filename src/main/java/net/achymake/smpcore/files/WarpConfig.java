package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WarpConfig {
    private final SMPCore smpCore;
    public WarpConfig (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private final Message message = SMPCore.getMessage();
    private final File file = new File(SMPCore.getInstance().getDataFolder(), "warps.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public boolean exist() {
        return file.exists();
    }
    public void setup() {
        if (!file.exists()) {
            config.options().copyDefaults(true);
            try {
                config.save(file);
            } catch (IOException e) {
                message.sendLog(e.getMessage());
            }
        }
    }
    public FileConfiguration get() {
        return config;
    }
    public boolean warpExist(String warpName) {
        return config.isConfigurationSection(warpName);
    }
    public List<String> getWarps() {
        return new ArrayList<>(config.getKeys(false));
    }
    public void setWarp(Location location, String warpName) {
        config.set(warpName + ".world", location.getWorld().getName());
        config.set(warpName + ".x", location.getX());
        config.set(warpName + ".y", location.getY());
        config.set(warpName + ".z", location.getZ());
        config.set(warpName + ".yaw", location.getYaw());
        config.set(warpName + ".pitch", location.getPitch());
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public void delWarp(String warpName) {
        config.set(warpName, null);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public Location getWarp(String warpName) {
        String world = config.getString(warpName + ".world");
        double x = config.getDouble(warpName + ".x");
        double y = config.getDouble(warpName + ".y");
        double z = config.getDouble(warpName + ".z");
        float yaw = config.getLong(warpName + ".yaw");
        float pitch = config.getLong(warpName + ".pitch");
        return new Location(smpCore.getServer().getWorld(world), x, y, z, yaw, pitch);
    }
    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
}