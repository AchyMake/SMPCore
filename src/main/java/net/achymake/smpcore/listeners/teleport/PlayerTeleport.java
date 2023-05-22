package net.achymake.smpcore.listeners.teleport;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener {
    private final PlayerData playerData = SMPCore.getPlayerData();
    public PlayerTeleport(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onTest(PlayerTeleportEvent event) {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)) {
            playerData.setLocation(event.getPlayer(), "recent");
        } else if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN)) {
            playerData.setLocation(event.getPlayer(), "recent");
        }
    }
}