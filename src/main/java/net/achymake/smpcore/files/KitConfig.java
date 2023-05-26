package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitConfig {
    private final SMPCore smpCore;
    public KitConfig (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    private final Message message = SMPCore.getMessage();
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final File file = new File(SMPCore.getInstance().getDataFolder(), "kits.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public boolean exist() {
        return file.exists();
    }
    public void setup() {
        if (!file.exists()) {
            List<String> lore = new ArrayList<>();
            lore.add("&9from");
            lore.add("&7-&6 Starter");
            config.addDefault("starter.cooldown",3600);
            config.addDefault("starter.materials.sword.type","STONE_SWORD");
            config.addDefault("starter.materials.sword.amount",1);
            config.addDefault("starter.materials.sword.name","&6Stone Sword");
            config.addDefault("starter.materials.sword.lore",lore);
            config.addDefault("starter.materials.sword.enchantments.unbreaking.type","DURABILITY");
            config.addDefault("starter.materials.sword.enchantments.unbreaking.amount",1);
            config.addDefault("starter.materials.pickaxe.type","STONE_PICKAXE");
            config.addDefault("starter.materials.pickaxe.amount",1);
            config.addDefault("starter.materials.pickaxe.name","&6Stone Pickaxe");
            config.addDefault("starter.materials.pickaxe.lore",lore);
            config.addDefault("starter.materials.pickaxe.enchantments.unbreaking.type","DURABILITY");
            config.addDefault("starter.materials.pickaxe.enchantments.unbreaking.amount",1);
            config.addDefault("starter.materials.axe.type","STONE_AXE");
            config.addDefault("starter.materials.axe.amount",1);
            config.addDefault("starter.materials.axe.name","&6Stone Axe");
            config.addDefault("starter.materials.axe.lore",lore);
            config.addDefault("starter.materials.axe.enchantments.unbreaking.type","DURABILITY");
            config.addDefault("starter.materials.axe.enchantments.unbreaking.amount",1);
            config.addDefault("starter.materials.shovel.type","STONE_SHOVEL");
            config.addDefault("starter.materials.shovel.amount",1);
            config.addDefault("starter.materials.shovel.name","&6Stone Shovel");
            config.addDefault("starter.materials.shovel.lore",lore);
            config.addDefault("starter.materials.shovel.enchantments.unbreaking.type","DURABILITY");
            config.addDefault("starter.materials.shovel.enchantments.unbreaking.amount",1);
            config.addDefault("starter.materials.food.type","COOKED_BEEF");
            config.addDefault("starter.materials.food.amount",16);
            config.addDefault("food.cooldown",1800);
            config.addDefault("food.materials.food.type","COOKED_BEEF");
            config.addDefault("food.materials.food.amount",16);
            config.options().copyDefaults(true);
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
    public void giveKit(Player player, String kitName) {
        if (player.hasPermission("smpcore.command.kit.cooldown-exempt")) {
            dropKit(player,kitName);
            message.send(player, "&6You received &f" + kitName + "&6 kit");
        } else if (!playerConfig.getCommandCooldown().containsKey(kitName + "-" + player.getUniqueId())) {
            playerConfig.getCommandCooldown().put(kitName + "-" + player.getUniqueId(),System.currentTimeMillis());
            dropKit(player,kitName);
            message.send(player, "&6You received &f" + kitName + "&6 kit");
        } else {
            Long timeElapsed = System.currentTimeMillis() - playerConfig.getCommandCooldown().get(kitName + "-" + player.getUniqueId());
            String cooldownTimer = config.getString(kitName+".cooldown");
            Integer integer = Integer.valueOf(cooldownTimer.replace(cooldownTimer, cooldownTimer + "000"));
            if (timeElapsed > integer) {
                playerConfig.getCommandCooldown().put(kitName + "-" + player.getUniqueId(),System.currentTimeMillis());
                dropKit(player, kitName);
                message.send(player, "&6You received &f" + kitName + "&6 kit");
            } else {
                long timer = (integer-timeElapsed);
                message.sendActionBar(player, "&cYou have to wait&f "+String.valueOf(timer).substring(0,String.valueOf(timer).length()-3) + "&c seconds");
            }
        }
    }
    public List<ItemStack> getKit(String kitName) {
        List<ItemStack> giveItems = new ArrayList<>();
        for (String items : config.getConfigurationSection(kitName + ".materials").getKeys(false)) {
            ItemStack item = new ItemStack(Material.valueOf(config.getString(kitName + ".materials." + items + ".type")), config.getInt(kitName + ".materials." + items + ".amount"));
            ItemMeta itemMeta = item.getItemMeta();
            if (config.getKeys(true).contains(kitName+".materials." + items + ".name")) {
                itemMeta.setDisplayName(message.color(config.getString(kitName + ".materials." + items + ".name")));
            }
            if (config.getKeys(true).contains(kitName+".materials." + items + ".lore")) {
                List<String> lore = new ArrayList<>();
                for (String listedLore : config.getStringList(kitName + ".materials." + items + ".lore")) {
                    lore.add(message.color(listedLore));
                }
                itemMeta.setLore(lore);
            }
            if (config.getKeys(true).contains(kitName+".materials." + items + ".enchantments")) {
                for (String enchantList : config.getConfigurationSection(kitName + ".materials." + items + ".enchantments").getKeys(false)){
                    itemMeta.addEnchant(Enchantment.getByName(config.getString(kitName + ".materials." + items + ".enchantments." + enchantList + ".type")), config.getInt(kitName+".materials."+items+".enchantments."+enchantList+".amount"),true);
                }
            }
            item.setItemMeta(itemMeta);
            giveItems.add(item);
        }
        return giveItems;
    }
    public void dropKit(Player player,String kitName) {
        for (ItemStack itemStack : getKit(kitName)) {
            if (Arrays.asList(player.getInventory().getStorageContents()).contains(null)) {
                player.getInventory().addItem(itemStack);
            } else {
                player.getWorld().dropItem(player.getLocation(), itemStack);
            }
        }
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
