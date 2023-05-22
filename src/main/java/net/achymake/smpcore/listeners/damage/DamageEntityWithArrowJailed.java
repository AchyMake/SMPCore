package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityWithArrowJailed implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public DamageEntityWithArrowJailed(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntityWithArrowJailed(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.ARROW))return;
        Arrow damager = (Arrow) event.getDamager();
        if (damager.getShooter() instanceof Player) {
            if (playerConfig.isFrozen((Player) event.getDamager()) || playerConfig.isJailed((Player) event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }
}