package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayerWithTrident implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final FileConfiguration config = smpCore.getConfig();
    private final Message message = smpCore.getMessage();
    public DamagePlayerWithTrident() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithTrident(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.TRIDENT))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Trident damager = (Trident) event.getDamager();
        Player target = (Player) event.getEntity();
        if (damager.getShooter() instanceof Player){
            if (playerConfig.isJailed((Player) damager.getShooter()))return;
            if (!playerConfig.isPVP((Player) damager.getShooter())) {
                if (config.getBoolean("pvp." + target.getWorld().getName()))return;
                event.setCancelled(true);
                if (damager.getShooter() == null)return;
                message.send((Player) damager.getShooter(),"&cYour pvp is false");
            } else if (!playerConfig.isPVP(target)) {
                if (config.getBoolean("pvp." + target.getWorld().getName()))return;
                event.setCancelled(true);
                if (damager.getShooter() == null)return;
                message.send((Player) damager.getShooter(), target.getName() + "&c pvp is false");
            }
        }
    }
}