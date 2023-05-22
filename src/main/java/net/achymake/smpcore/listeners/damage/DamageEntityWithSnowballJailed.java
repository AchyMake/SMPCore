package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityWithSnowballJailed implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public DamageEntityWithSnowballJailed() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntityWithSnowballJailed(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.SNOWBALL))return;
        Snowball damager = (Snowball) event.getDamager();
        if (damager.getShooter() instanceof Player) {
            if (playerConfig.isFrozen((Player) event.getDamager()) || playerConfig.isJailed((Player) event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }
}