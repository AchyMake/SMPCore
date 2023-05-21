package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerData {
    private final SMPCore smpCore;
    public PlayerData (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private PersistentDataContainer data(Player player) {
        return player.getPersistentDataContainer();
    }
    public void setString(Player player, String key, String value) {
        data(player).set(NamespacedKey.minecraft(key), PersistentDataType.STRING, value);
    }
    public boolean hasString(Player player, String key) {
        return data(player).has(NamespacedKey.minecraft(key), PersistentDataType.STRING);
    }
    public String getString(Player player, String key) {
        return data(player).get(NamespacedKey.minecraft(key), PersistentDataType.STRING);
    }
    public void setInt(Player player, String key, int value) {
        data(player).set(NamespacedKey.minecraft(key), PersistentDataType.INTEGER, value);
    }
    public boolean hasInt(Player player, String key) {
        return data(player).has(NamespacedKey.minecraft(key), PersistentDataType.INTEGER);
    }
    public int getInt(Player player, String key) {
        return data(player).get(NamespacedKey.minecraft(key), PersistentDataType.INTEGER);
    }
    public void setFloat(Player player, String key, float value) {
        data(player).set(NamespacedKey.minecraft(key), PersistentDataType.FLOAT, value);
    }
    public boolean hasFloat(Player player, String key) {
        return data(player).has(NamespacedKey.minecraft(key), PersistentDataType.FLOAT);
    }
    public float getFloat(Player player, String key) {
        return data(player).get(NamespacedKey.minecraft(key), PersistentDataType.FLOAT);
    }
    public void setDouble(Player player, String key, double value) {
        data(player).set(NamespacedKey.minecraft(key), PersistentDataType.DOUBLE, value);
    }
    public boolean hasDouble(Player player, String key) {
        return data(player).has(NamespacedKey.minecraft(key), PersistentDataType.DOUBLE);
    }
    public double getDouble(Player player, String key) {
        return data(player).get(NamespacedKey.minecraft(key), PersistentDataType.DOUBLE);
    }
    public void setLocation(Player player, String locationName) {
        setString(player, locationName + ".location.world", player.getWorld().getName());
        setDouble(player, locationName + ".location.x", player.getLocation().getX());
        setDouble(player, locationName + ".location.y", player.getLocation().getY());
        setDouble(player, locationName + ".location.z", player.getLocation().getZ());
        setFloat(player, locationName + ".location.yaw", player.getLocation().getYaw());
        setFloat(player, locationName + ".location.pitch", player.getLocation().getPitch());
    }
    public Location getLocation(Player player, String locationName) {
        String worldName = getString(player, locationName + ".location.world");
        double x = getDouble(player, locationName + ".location.x");
        double y = getDouble(player, locationName + ".location.y");
        double z = getDouble(player, locationName + ".location.z");
        float yaw = getFloat(player, locationName + ".location.yaw");
        float pitch = getFloat(player, locationName + ".location.pitch");
        return new Location(player.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }
    public void removeLocation(Player player, String locationName) {
        removeData(player, locationName + ".location.world");
        removeData(player, locationName + ".location.x");
        removeData(player, locationName + ".location.y");
        removeData(player, locationName + ".location.z");
        removeData(player, locationName + ".location.yaw");
        removeData(player, locationName + ".location.pitch");
    }
    public void removeData(Player player, String type) {
        data(player).remove(NamespacedKey.minecraft(type));
    }
}