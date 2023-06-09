package net.achymake.smpcore.listeners.interact;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractPhysical implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public PlayerInteractPhysical(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractPhysical(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null)return;
        if (!event.getAction().equals(Action.PHYSICAL))return;
        if (playerConfig.isFrozen(event.getPlayer()) || playerConfig.isJailed(event.getPlayer()) || playerConfig.isVanished(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}