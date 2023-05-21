package net.achymake.smpcore.listeners.connection;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.PlayerData;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.MessageFormat;
import java.util.UUID;

public class QuitMessage implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final PlayerData playerData = smpCore.getPlayerData();
    private final FileConfiguration config = smpCore.getConfig();
    private final Message message = smpCore.getMessage();
    public QuitMessage() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuitMessage (PlayerQuitEvent event) {
        playerConfig.setLocation(event.getPlayer(), "quit-location");
        playerData.removeData(event.getPlayer(), "last-whisper");
        if (playerConfig.isVanished(event.getPlayer())) {
            playerConfig.getVanished().remove(event.getPlayer());
            event.setQuitMessage(null);
        } else {
            if (config.getBoolean("connection.quit.enable")) {
                event.setQuitMessage(message.color(MessageFormat.format(config.getString("connection.quit.message"), event.getPlayer().getName())));
                if (config.getBoolean("connection.quit.sound.enable")) {
                    for (Player players : event.getPlayer().getServer().getOnlinePlayers()) {
                        players.playSound(players, Sound.valueOf(config.getString("connection.quit.sound.type")), Float.valueOf(config.getString("connection.quit.sound.volume")), Float.valueOf(config.getString("connection.quit.sound.pitch")));
                    }
                }
            } else {
                if (event.getPlayer().hasPermission("smpcore.quit-message")) {
                    event.setQuitMessage(message.color(MessageFormat.format(config.getString("connection.quit.message"), event.getPlayer().getName())));
                    if (config.getBoolean("connection.quit.sound.enable")) {
                        for (Player players : event.getPlayer().getServer().getOnlinePlayers()) {
                            players.playSound(players, Sound.valueOf(config.getString("connection.quit.sound.type")), Float.valueOf(config.getString("connection.quit.sound.volume")), Float.valueOf(config.getString("connection.quit.sound.pitch")));
                        }
                    }
                } else {
                    event.setQuitMessage(null);
                }
            }
        }
        if (playerData.hasString(event.getPlayer(), "tpa.from")) {
            Player target = event.getPlayer().getServer().getPlayer(UUID.fromString(playerData.getString(event.getPlayer(), "tpa.from")));
            if (target != null) {
                playerData.removeData(target, "tpa.sent");
                if (event.getPlayer().getServer().getScheduler().isQueued(playerData.getInt(target, "task.tpa"))) {
                    event.getPlayer().getServer().getScheduler().cancelTask(playerData.getInt(target,"task.tpa"));
                    playerData.removeData(target, "tpa.from");
                }
                playerData.removeData(event.getPlayer(), "task.tpa");
            }
        }
        if (playerData.hasString(event.getPlayer(), "tpa.sent")) {
            Player target = event.getPlayer().getServer().getPlayer(UUID.fromString(playerData.getString(event.getPlayer(), "tpa.sent")));
            if (event.getPlayer().getServer().getScheduler().isQueued(playerData.getInt(event.getPlayer(), "task.tpa"))) {
                event.getPlayer().getServer().getScheduler().cancelTask(playerData.getInt(event.getPlayer(), "task.tpa"));
            }
            playerData.removeData(event.getPlayer(), "task.tpa");
            if (target != null) {
                playerData.removeData(target, "tpa.from");
            }
            playerData.removeData(event.getPlayer(), "tpa.sent");
        }
    }
}