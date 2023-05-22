package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityWithTridentJailed implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public DamageEntityWithTridentJailed() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntityWithTridentJailed(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.TRIDENT))return;
        Trident damager = (Trident) event.getDamager();
        if (damager.getShooter() instanceof Player) {
            if (playerConfig.isFrozen((Player) event.getDamager()) || playerConfig.isJailed((Player) event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }
}