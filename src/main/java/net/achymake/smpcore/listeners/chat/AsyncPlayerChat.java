package net.achymake.smpcore.listeners.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    public AsyncPlayerChat() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChat (AsyncPlayerChatEvent event) {
        if (playerConfig.isMuted(event.getPlayer())) {
            event.setCancelled(true);
        } else {
            if (event.getPlayer().hasPermission("smpcore.chatcolor.chat")) {
                event.setMessage(message.color(event.getMessage()));
            }
            event.setFormat(message.color(prefix(event.getPlayer()) + event.getPlayer().getName() + "&r"  + suffix(event.getPlayer()) + "&r") + ": " + event.getMessage());
        }
    }
    private String prefix(Player player) {
        if (PlaceholderAPI.isRegistered("vault")) {
            return PlaceholderAPI.setPlaceholders(player, "%vault_prefix%");
        } else {
            return "";
        }
    }
    private String suffix(Player player) {
        if (me.clip.placeholderapi.PlaceholderAPI.isRegistered("vault")) {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "%vault_suffix%");
        } else {
            return "";
        }
    }
}