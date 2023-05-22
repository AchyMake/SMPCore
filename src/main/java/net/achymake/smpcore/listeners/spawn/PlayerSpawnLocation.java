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
    public PlayerSpawnLocation() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        if (playerConfig.locationExist(event.getPlayer(), "quit-location"))return;
        if (!spawnConfig.spawnExist())return;
        spawnConfig.getSpawn().getChunk().load();
        event.setSpawnLocation(spawnConfig.getSpawn());
    }
}