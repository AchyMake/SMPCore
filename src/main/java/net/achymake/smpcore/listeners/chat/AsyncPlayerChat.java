package net.achymake.smpcore.listeners.chat;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    public AsyncPlayerChat(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChat (AsyncPlayerChatEvent event) {
        if (playerConfig.isMuted(event.getPlayer())) {
            event.setCancelled(true);
        } else {
            if (event.getPlayer().hasPermission("smpcore.chatcolor.chat")) {
                event.setMessage(message.color(event.getMessage()));
            }
            event.setFormat(message.color(playerConfig.prefix(event.getPlayer()) + event.getPlayer().getName() + "&r"  + playerConfig.suffix(event.getPlayer()) + "&r") + ": " + event.getMessage());
        }
    }
}