package net.achymake.smpcore.listeners.damage;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayer implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final FileConfiguration config = SMPCore.getInstance().getConfig();
    private final Message message = SMPCore.getMessage();
    public DamagePlayer(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayer(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Player player = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        if (playerConfig.isJailed(player))return;
        if (!playerConfig.isPVP(player)) {
            if (config.getBoolean("pvp." + target.getWorld().getName()))return;
            event.setCancelled(true);
            message.send(player,"&cYour pvp is false");
        } else if (!playerConfig.isPVP(target)) {
            if (config.getBoolean("pvp." + target.getWorld().getName()))return;
            event.setCancelled(true);
            message.send(player, target.getName() + "&c pvp is false");
        }
    }
}