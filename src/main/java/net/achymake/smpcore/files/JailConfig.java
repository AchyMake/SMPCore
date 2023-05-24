package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class JailConfig {
    private final SMPCore smpCore;
    public JailConfig (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private final Message message = SMPCore.getMessage();
    private final File file = new File(SMPCore.getInstance().getDataFolder(), "jail.yml");
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
    public void setJail(Location location) {
        config.set("jail.world",location.getWorld().getName());
        config.set("jail.x",location.getX());
        config.set("jail.y",location.getY());
        config.set("jail.z",location.getZ());
        config.set("jail.yaw",location.getYaw());
        config.set("jail.pitch",location.getPitch());
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public boolean jailExist() {
        return config.isConfigurationSection("jail");
    }
    public Location getJail(){
        String world = config.getString("jail.world");
        double x = config.getDouble("jail.x");
        double y = config.getDouble("jail.y");
        double z = config.getDouble("jail.z");
        float yaw = config.getLong("jail.yaw");
        float pitch = config.getLong("jail.pitch");
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
    public SMPCore getSmpCore() {
        return smpCore;
    }
}