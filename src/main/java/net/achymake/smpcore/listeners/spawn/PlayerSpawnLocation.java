package net.achymake.smpcore.listeners.spawn;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.SpawnConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnLocation implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final SpawnConfig spawnConfig = SMPCore.getSpawnConfig();
    public PlayerSpawnLocation(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        if (playerConfig.locationExist(event.getPlayer(), "quit"))return;
        if (spawnConfig.spawnExist()) {
            spawnConfig.getSpawn().getChunk().load();
            event.setSpawnLocation(spawnConfig.getSpawn());
            playerConfig.setLocation(event.getPlayer(), "spawn");
        } else {
            event.setSpawnLocation(playerConfig.getLocation(event.getPlayer(), "spawn"));
        }
    }
}