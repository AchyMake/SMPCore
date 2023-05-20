package net.achymake.smpcore.listeners.block;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class PlayerHarvestBlock implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    public PlayerHarvestBlock() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockFertilizeFrozen(PlayerHarvestBlockEvent event) {
        if (playerConfig.isFrozen(event.getPlayer()) || playerConfig.isJailed(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}