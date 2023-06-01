package net.achymake.smpcore.files;

import me.clip.placeholderapi.PlaceholderAPI;
import net.achymake.smpcore.SMPCore;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayerConfig {
    private final SMPCore smpCore;
    public PlayerConfig (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private final Message message = SMPCore.getMessage();
    private final HashMap<String, Long> commandCooldown = new HashMap<>();
    private final List<Player> vanished = new ArrayList<>();
    public boolean exist(OfflinePlayer offlinePlayer) {
        return new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml").exists();
    }
    public void setup(OfflinePlayer offlinePlayer) {
        if (exist(offlinePlayer)) {
            File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (!config.getString("name").equals(offlinePlayer.getName())) {
                config.set("name", offlinePlayer.getName());
                try {
                    config.save(file);
                } catch (IOException e) {
                    message.sendLog(e.getMessage());
                }
            }
        } else {
            File file = new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("name", offlinePlayer.getName());
            config.set("display-name", offlinePlayer.getName());
            config.set("account", smpCore.getConfig().getDouble("economy.starting-balance"));
            config.set("is-PVP", true);
            config.set("max-homes", smpCore.getConfig().getInt("homes.default"));
            config.createSection("homes");
            Location location = randomLocation();
            config.set("locations.spawn.world", location.getWorld().getName());
            config.set("locations.spawn.x", location.getX());
            config.set("locations.spawn.y", location.getY());
            config.set("locations.spawn.z", location.getZ());
            config.set("locations.spawn.yaw", location.getYaw());
            config.set("locations.spawn.pitch", location.getPitch());
            config.set("locations.recent.world", location.getWorld().getName());
            config.set("locations.recent.x", location.getX());
            config.set("locations.recent.y", location.getY());
            config.set("locations.recent.z", location.getZ());
            config.set("locations.recent.yaw", location.getYaw());
            config.set("locations.recent.pitch", location.getPitch());
            try {
                config.save(file);
            } catch (IOException e) {
                message.sendLog(e.getMessage());
            }
        }
    }
    public FileConfiguration get(OfflinePlayer offlinePlayer) {
        return YamlConfiguration.loadConfiguration(new File(smpCore.getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml"));
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
    public boolean homeExist(OfflinePlayer offlinePlayer, String homeName) {
        return get(offlinePlayer).isConfigurationSection("homes." + homeName);
    }
    public List<String> getHomes(OfflinePlayer offlinePlayer) {
        return new ArrayList<>(get(offlinePlayer).getConfigurationSection("homes").getKeys(false));
    }
    public void setHome(Player player, String homeName) {
        setString(player, "homes." + homeName + ".world", player.getWorld().getName());
        setDouble(player, "homes." + homeName + ".x", player.getLocation().getX());
        setDouble(player, "homes." + homeName + ".y", player.getLocation().getY());
        setDouble(player, "homes." + homeName + ".z", player.getLocation().getZ());
        setFloat(player, "homes." + homeName + ".yaw", player.getLocation().getYaw());
        setFloat(player, "homes." + homeName + ".pitch", player.getLocation().getPitch());
    }
    public Location getHome(OfflinePlayer offlinePlayer, String homeName) {
        String worldName = get(offlinePlayer).getString("homes." + homeName + ".world");
        double x = get(offlinePlayer).getDouble("homes." + homeName + ".x");
        double y = get(offlinePlayer).getDouble("homes." + homeName + ".y");
        double z = get(offlinePlayer).getDouble("homes." + homeName + ".z");
        float yaw = get(offlinePlayer).getLong("homes." + homeName + ".yaw");
        float pitch = get(offlinePlayer).getLong("homes." + homeName + ".pitch");
        return new Location(smpCore.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }
    public boolean locationExist(OfflinePlayer offlinePlayer, String locationName) {
        return get(offlinePlayer).isConfigurationSection("locations." + locationName);
    }
    public void setLocation(Player player, String locationName) {
        setString(player, "locations." + locationName + ".world", player.getWorld().getName());
        setDouble(player, "locations." + locationName + ".x", player.getLocation().getX());
        setDouble(player, "locations." + locationName + ".y", player.getLocation().getY());
        setDouble(player, "locations." + locationName + ".z", player.getLocation().getZ());
        setFloat(player, "locations." + locationName + ".yaw", player.getLocation().getYaw());
        setFloat(player, "locations." + locationName + ".pitch", player.getLocation().getPitch());
    }
    public Location getLocation(OfflinePlayer offlinePlayer, String locationName) {
        String worldName = get(offlinePlayer).getString("locations." + locationName + ".world");
        double x = get(offlinePlayer).getDouble("locations." + locationName + ".x");
        double y = get(offlinePlayer).getDouble("locations." + locationName + ".y");
        double z = get(offlinePlayer).getDouble("locations." + locationName + ".z");
        float yaw = get(offlinePlayer).getLong("locations." + locationName + ".yaw");
        float pitch = get(offlinePlayer).getLong("locations." + locationName + ".pitch");
        return new Location(smpCore.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }
    public void hideVanished(Player player) {
        if (!vanished.isEmpty()) {
            for (Player vanishedPlayers : vanished) {
                player.hidePlayer(smpCore, vanishedPlayers);
            }
        }
    }
    public void setVanish(OfflinePlayer offlinePlayer, boolean value) {
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
                if (!player.hasPermission("smpcore.command.fly")) {
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
    public String prefix(Player player) {
        if (PlaceholderAPI.isRegistered("vault")) {
            return PlaceholderAPI.setPlaceholders(player, "%vault_prefix%");
        } else {
            return "";
        }
    }
    public String suffix(Player player) {
        if (PlaceholderAPI.isRegistered("vault")) {
            return PlaceholderAPI.setPlaceholders(player, "%vault_suffix%");
        } else {
            return "";
        }
    }
    public void resetTabList() {
        if (smpCore.getConfig().getBoolean("tablist.enable")) {
            for (Player players : smpCore.getServer().getOnlinePlayers()) {
                players.setPlayerListHeader(message.color(smpCore.getConfig().getString("tablist.header")));
                players.setPlayerListName(message.color(prefix(players) + players.getName() + suffix(players)));
                players.setPlayerListFooter(message.color(MessageFormat.format(smpCore.getConfig().getString("tablist.footer"), smpCore.getServer().getOnlinePlayers().size() - vanished.size(), smpCore.getServer().getMaxPlayers())));
            }
        } else {
            for (Player players : smpCore.getServer().getOnlinePlayers()) {
                players.setPlayerListHeader(null);
                players.setPlayerListName(players.getName());
                players.setPlayerListFooter(null);
            }
        }
    }
    public Block highestRandomBlock() {
        return smpCore.getServer().getWorlds().get(0).getHighestBlockAt(new Random().nextInt(0, 1250), new Random().nextInt(0, 1250));
    }
    public void randomTeleport(Player player) {
        Block block = highestRandomBlock();
        if (block.isLiquid()) {
            randomTeleport(player);
        } else {
            block.getChunk().load();
            player.teleport(block.getLocation().add(0.5, 1.0, 0.5));
        }
    }
    public Location randomLocation() {
        Block block = highestRandomBlock();
        if (block.isLiquid()) {
            return randomLocation();
        } else {
            return block.getLocation().add(0.5, 1, 0.5);
        }
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
}