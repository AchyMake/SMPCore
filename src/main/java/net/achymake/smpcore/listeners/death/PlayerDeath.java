package net.achymake.smpcore.listeners.death;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;

public class PlayerDeath implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final FileConfiguration config = SMPCore.getInstance().getConfig();
    public PlayerDeath(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent event) {
        playerConfig.setLocation(event.getEntity(),"death-location");
        playerConfig.setBoolean(event.getEntity(),"is-Dead", true);
        if (config.getBoolean("deaths.drop-player-head.enable")) {
            if (config.getInt("deaths.drop-player-head.chance") > new Random().nextInt(100)) {
                ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
                skullMeta.setOwningPlayer(event.getEntity());
                skullItem.setItemMeta(skullMeta);
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), skullItem);
            }
        }
    }
}