package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerConfig {
    private final SMPCore smpCore;
    public PlayerConfig (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private final Message message = SMPCore.getInstance().getMessage();
    private static final HashMap<String, Long> commandCooldown = new HashMap<>();
    private final List<Player> vanished = new ArrayList<>();
    public boolean exist(OfflinePlayer offlinePlayer) {
        return new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml").exists();
    }
    public void setup(OfflinePlayer offlinePlayer) {
        File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (file.exists()) {
            if (!config.getString("name").equals(offlinePlayer.getName())) {
                config.set("name", offlinePlayer.getName());
                try {
                    config.save(file);
                } catch (IOException e) {
                    message.sendLog(e.getMessage());
                }
            }
        } else {
            config.set("name", offlinePlayer.getName());
            config.set("display-name", offlinePlayer.getName());
            config.set("account", smpCore.getConfig().getDouble("economy.starting-balance"));
            config.set("is-PVP", true);
            config.set("max-homes", smpCore.getConfig().getInt("homes.default"));
            config.createSection("homes");
            try {
                config.save(file);
            } catch (IOException e) {
                message.sendLog(e.getMessage());
            }
        }
    }
    public FileConfiguration get(OfflinePlayer offlinePlayer) {
        File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }
    public void setInt(OfflinePlayer offlinePlayer, String path, int amount) {
        File file = new File(smpCore.getDataFolder(),"userdata/" + offlinePlayer.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(path, amount);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public void setDouble(OfflinePlayer offlinePlayer, String path, double amount) {
        File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(path, amount);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public void setFloat(OfflinePlayer offlinePlayer, String path, float amount) {
        File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(path, amount);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public void setString(OfflinePlayer offlinePlayer, String path, String value) {
        File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(path, value);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public void setStringList(OfflinePlayer offlinePlayer, String path, List<String> value) {
        File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(path, value);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public void setBoolean(OfflinePlayer offlinePlayer, String path, boolean value) {
        File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(path, value);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public boolean locationExist(OfflinePlayer offlinePlayer, String locationName) {
       return get(offlinePlayer).isConfigurationSection(locationName);
    }
    public List<String> getHomes(OfflinePlayer offlinePlayer) {
        return new ArrayList<>(get(offlinePlayer).getConfigurationSection("homes").getKeys(false));
    }
    public void setLocation(Player player, String locationName) {
        setString(player, locationName + ".world", player.getWorld().getName());
        setDouble(player, locationName + ".x", player.getLocation().getX());
        setDouble(player, locationName + ".y", player.getLocation().getY());
        setDouble(player, locationName + ".z", player.getLocation().getZ());
        setFloat(player, locationName + ".yaw", player.getLocation().getYaw());
        setFloat(player, locationName + ".pitch", player.getLocation().getPitch());
    }
    public Location getLocation(OfflinePlayer offlinePlayer, String locationName) {
        String worldName = get(offlinePlayer).getString(locationName + ".world");
        double x = get(offlinePlayer).getDouble(locationName + ".x");
        double y = get(offlinePlayer).getDouble(locationName + ".y");
        double z = get(offlinePlayer).getDouble(locationName + ".z");
        float yaw = get(offlinePlayer).getLong(locationName + ".yaw");
        float pitch = get(offlinePlayer).getLong(locationName + ".pitch");
        return new Location(smpCore.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }
    public void hideVanished(Player player) {
        if (!vanished.isEmpty()) {
            for (Player vanishedPlayers : vanished) {
                player.hidePlayer(smpCore, vanishedPlayers);
            }
        }
    }
    public void setVanish(OfflinePlayer offlinePlayer, boolean value){
        if (value) {
            setBoolean(offlinePlayer,"is-Vanished", true);
            if (offlinePlayer.isOnline()) {
                Player player = offlinePlayer.getPlayer();
                vanished.add(player);
                for (Player players : smpCore.getServer().getOnlinePlayers()) {
                    players.hidePlayer(smpCore, player);
                }
                player.setAllowFlight(true);
                player.setInvulnerable(true);
                player.setSleepingIgnored(true);
                player.setCollidable(false);
                player.setSilent(true);
                player.setCanPickupItems(false);
                for (Player vanishedPlayers : vanished) {
                    vanishedPlayers.showPlayer(smpCore, player);
                    player.showPlayer(smpCore, vanishedPlayers);
                }
            }
        } else {
            setBoolean(offlinePlayer,"is-Vanished", false);
            if (offlinePlayer.isOnline()) {
                Player player = offlinePlayer.getPlayer();
                vanished.remove(player);
                for (Player players : smpCore.getServer().getOnlinePlayers()) {
                    players.showPlayer(smpCore, player);
                }
                if (!player.hasPermission("players.command.fly")) {
                    player.setAllowFlight(false);
                }
                player.setInvulnerable(false);
                player.setSleepingIgnored(false);
                player.setCollidable(true);
                player.setSilent(false);
                player.setCanPickupItems(true);
                for (Player vanishedPlayers : vanished) {
                    player.hidePlayer(smpCore, vanishedPlayers);
                }
            }
        }
    }
    public double getEconomy(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer).getDouble("account");
    }
    public void addEconomy(OfflinePlayer offlinePlayer, double amount) {
        double newAmount = amount + getEconomy(offlinePlayer);
        setDouble(offlinePlayer, "account", newAmount);
    }
    public void removeEconomy(OfflinePlayer offlinePlayer, double amount) {
        double newAmount = getEconomy(offlinePlayer) - amount;
        setDouble(offlinePlayer, "account", newAmount);
    }
    public void setEconomy(OfflinePlayer offlinePlayer, double amount) {
        setDouble(offlinePlayer,"account", amount);
    }
    public void resetEconomy(OfflinePlayer offlinePlayer) {
        setDouble(offlinePlayer,"account", smpCore.getConfig().getDouble("economy.starting-balance"));
    }
    public double getPoints(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer).getDouble("points");
    }
    public boolean isPVP(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer).getBoolean("is-PVP");
    }
    public boolean isFrozen(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer).getBoolean("is-Frozen");
    }
    public boolean isJailed(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer).getBoolean("is-Jailed");
    }
    public boolean isMuted(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer).getBoolean("is-Muted");
    }
    public boolean isVanished(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer).getBoolean("is-Vanished");
    }
    public HashMap<String, Long> getCommandCooldown() {
        return commandCooldown;
    }
    public List<Player> getVanished() {
        return vanished;
    }
    public SMPCore getSmpCore() {
        return smpCore;
    }
}