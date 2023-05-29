package net.achymake.smpcore.listeners.move;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    public PlayerMove(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (playerConfig.get(event.getPlayer()).getBoolean("is-Frozen")) {
            event.setCancelled(true);
        }
        if (playerConfig.get(event.getPlayer()).getBoolean("is-Vanished")) {
            message.sendActionBar(event.getPlayer(),"&6&lVanish:&a Enabled");
        }
    }
}