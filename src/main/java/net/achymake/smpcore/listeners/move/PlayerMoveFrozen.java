package net.achymake.smpcore.listeners.move;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveFrozen implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public PlayerMoveFrozen(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMoveWhileFrozen(PlayerMoveEvent event) {
        if (!playerConfig.get(event.getPlayer()).getBoolean("is-Frozen"))return;
        event.setCancelled(true);
    }
}