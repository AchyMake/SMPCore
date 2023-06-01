package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Random;

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
        if (!exist()) {
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
    public void setSpawn(Location location) {
        config.set("spawn.world", location.getWorld().getName());
        config.set("spawn.x", location.getX());
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getZ());
        config.set("spawn.yaw", location.getYaw());
        config.set("spawn.pitch", location.getPitch());
        save();
    }
    public boolean spawnExist() {
        return config.isConfigurationSection("spawn");
    }
    public Location getSpawn() {
        String world = config.getString("spawn.world");
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        float yaw = config.getLong("spawn.yaw");
        float pitch = config.getLong("spawn.pitch");
        return new Location(smpCore.getServer().getWorld(world), x, y, z, yaw, pitch);
    }
    public Block highestRandomBlock() {
        return smpCore.getServer().getWorlds().get(0).getHighestBlockAt(new Random().nextInt(0, 1250), new Random().nextInt(0, 1250));
    }
    public void random(Player player) {
        Block block = highestRandomBlock();
        if (block.isLiquid()) {
            random(player);
        } else {
            block.getChunk().load();
            player.teleport(block.getLocation().add(0.5, 1.0, 0.5));
        }
    }
    public Location randomSpawn() {
        Block block = highestRandomBlock();
        if (block.isLiquid()) {
            return randomSpawn();
        } else {
            return block.getLocation().add(0.5, 1, 0.5);
        }
    }
    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
        save();
    }
}