package net.achymake.smpcore.listeners.move;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveVanished implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    public PlayerMoveVanished() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMoveWhileVanished(PlayerMoveEvent event) {
        if (!playerConfig.get(event.getPlayer()).getBoolean("is-Vanished"))return;
        message.sendActionBar(event.getPlayer(),"&6&lVanish:&a Enabled");
    }
}