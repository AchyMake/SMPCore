package net.achymake.smpcore.listeners.respawn;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    public PlayerRespawn(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onRespawn(PlayerRespawnEvent event) {
        if (!playerConfig.get(event.getPlayer()).getBoolean("is-Dead"))return;
        playerConfig.setBoolean(event.getPlayer(), "is-Dead", false);
        if (event.getPlayer().hasPermission("smpcore.death-location")) {
            Location location = playerConfig.getLocation(event.getPlayer(),"death-location");
            String world = location.getWorld().getEnvironment().toString().toLowerCase();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            message.send(event.getPlayer(), "&6Death location:");
            message.send(event.getPlayer(), "&6World:&f " + world + "&6 X:&f " + x + "&6 Y:&f " + y + "&6 Z:&f " + z);
        }
    }
}