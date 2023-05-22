package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityWithSpectralArrowJailed implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public DamageEntityWithSpectralArrowJailed() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntityWithSpectralArrowJailed(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.SPECTRAL_ARROW))return;
        SpectralArrow damager = (SpectralArrow) event.getDamager();
        if (damager.getShooter() instanceof Player) {
            if (playerConfig.isFrozen((Player) event.getDamager()) || playerConfig.isJailed((Player) event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }
}