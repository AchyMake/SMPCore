package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SpawnConfig {
    private final SMPCore smpCore;
    public SpawnConfig (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private final Message message = SMPCore.getMessage();
    private final File file = new File(SMPCore.getInstance().getDataFolder(), "spawn.yml");
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
    public void setSpawn(Location location) {
        config.set("spawn.world",location.getWorld().getName());
        config.set("spawn.x",location.getX());
        config.set("spawn.y",location.getY());
        config.set("spawn.z",location.getZ());
        config.set("spawn.yaw",location.getYaw());
        config.set("spawn.pitch",location.getPitch());
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public boolean spawnExist() {
        return config.isConfigurationSection("spawn");
    }
    public Location getSpawn(){
        String world = config.getString("spawn.world");
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        float yaw = config.getLong("spawn.yaw");
        float pitch = config.getLong("spawn.pitch");
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