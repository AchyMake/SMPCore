package net.achymake.smpcore.listeners.chat;

import net.achymake.smpcore.SMPCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final FileConfiguration config = smpCore.getConfig();
    public PlayerCommandPreprocess () {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("players.exempt.commands"))return;
        for (String disabled : config.getStringList("commands.disable")) {
            if (event.getMessage().toLowerCase().contains(disabled)) {
                event.setCancelled(true);
            }
        }
    }
}