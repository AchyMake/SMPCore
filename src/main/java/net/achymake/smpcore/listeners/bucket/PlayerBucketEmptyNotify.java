package net.achymake.smpcore.listeners.bucket;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.text.MessageFormat;

public class PlayerBucketEmptyNotify implements Listener {
    private final FileConfiguration config = SMPCore.getInstance().getConfig();
    private final Message message = SMPCore.getMessage();
    public PlayerBucketEmptyNotify(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBucketEmptyNotify(PlayerBucketEmptyEvent event){
        if (!config.getBoolean("notification.enable"))return;
        if (!config.getStringList("notification.bucket-empty").contains(event.getBucket().toString()))return;
        for (Player players : event.getPlayer().getServer().getOnlinePlayers()) {
            if (players.hasPermission("smpcore.notify.bucket-empty")) {
                for (String messages : config.getStringList("notification.message")) {
                    players.sendMessage(message.color(MessageFormat.format(messages, event.getPlayer().getName(), event.getBucket().toString(), event.getBlock().getWorld().getName(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ())));
                }
            }
        }
    }
}