package net.achymake.smpcore.listeners.mount;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class PlayerMount implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public PlayerMount(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMountFrozen(EntityMountEvent event){
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Player player = (Player) event.getEntity();
        if (playerConfig.isFrozen(player) || playerConfig.isJailed(player)) {
            event.setCancelled(true);
        }
    }
}