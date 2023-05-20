package net.achymake.smpcore.listeners.block;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.text.MessageFormat;

public class BlockBreakNotify implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final FileConfiguration config = smpCore.getConfig();
    public BlockBreakNotify() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreakNotify(BlockBreakEvent event) {
        if (!config.getBoolean("notification.enable"))return;
        if (!config.getStringList("notification.block-break").contains(event.getBlock().getType().toString()))return;
        for (Player players : event.getPlayer().getServer().getOnlinePlayers()) {
            if (players.hasPermission("players.notify.block-break")) {
                for (String messages : config.getStringList("notification.message")) {
                    players.sendMessage(Message.color(MessageFormat.format(messages, event.getPlayer().getName(), event.getBlock().getType().toString(), event.getBlock().getWorld().getName(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ())));
                }
            }
        }
    }
}