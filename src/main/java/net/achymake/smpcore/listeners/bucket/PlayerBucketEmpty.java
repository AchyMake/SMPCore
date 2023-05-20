package net.achymake.smpcore.listeners.bucket;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerBucketEmpty implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    public PlayerBucketEmpty() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBucketEmptyFrozen(PlayerBucketEmptyEvent event) {
        if (playerConfig.isFrozen(event.getPlayer()) || playerConfig.isJailed(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}