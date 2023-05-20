package net.achymake.smpcore.listeners.teleport;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    public PlayerTeleport() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onTest(PlayerTeleportEvent event) {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)) {
            playerConfig.setLocation(event.getPlayer(),"last-location");
        } else if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN)) {
            playerConfig.setLocation(event.getPlayer(),"last-location");
        }
    }
}