package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityJailed implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    public DamageEntityJailed() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntityJailed(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER))return;
        if (playerConfig.isFrozen((Player) event.getDamager()) || playerConfig.isJailed((Player) event.getDamager())) {
            event.setCancelled(true);
        }
    }
}