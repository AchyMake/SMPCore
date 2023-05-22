package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayerWithSpectralArrow implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final FileConfiguration config = SMPCore.getInstance().getConfig();
    private final Message message = SMPCore.getMessage();
    public DamagePlayerWithSpectralArrow() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithSpectralArrow(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.SPECTRAL_ARROW))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        SpectralArrow damager = (SpectralArrow) event.getDamager();
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