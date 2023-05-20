package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
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
    public void removeData(Player player, String type) {
        data(player).remove(NamespacedKey.minecraft(type));
    }
}