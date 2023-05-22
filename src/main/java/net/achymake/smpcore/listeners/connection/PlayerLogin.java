package net.achymake.smpcore.listeners.connection;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    public PlayerLogin(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(PlayerLoginEvent event) {
        playerConfig.setup(event.getPlayer());
        if (event.getPlayer().getServer().getOnlinePlayers().size() >= event.getPlayer().getServer().getMaxPlayers()) {
            if (event.getPlayer().hasPermission("smpcore.join-full-server")) {
                event.allow();
            }
        }
    }
}