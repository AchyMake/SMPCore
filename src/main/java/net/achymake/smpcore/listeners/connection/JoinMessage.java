package net.achymake.smpcore.listeners.connection;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.*;
import net.achymake.smpcore.version.UpdateChecker;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.MessageFormat;

public class JoinMessage implements Listener {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final MotdConfig motdConfig = SMPCore.getMotdConfig();
    private final FileConfiguration config = SMPCore.getInstance().getConfig();
    private final Message message = SMPCore.getMessage();
    public JoinMessage() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoinMessage (PlayerJoinEvent event) {
        if (playerConfig.isVanished(event.getPlayer())) {
            playerConfig.setVanish(event.getPlayer(), true);
            event.setJoinMessage(null);
            message.send(event.getPlayer(), "&6You joined back vanished");
        } else {
            playerConfig.hideVanished(event.getPlayer());
            if (config.getBoolean("connection.join.enable")) {
                event.setJoinMessage(message.color(MessageFormat.format(config.getString("connection.join.message"), event.getPlayer().getName())));
                sendMotd(event.getPlayer());
                for (Player players : event.getPlayer().getServer().getOnlinePlayers()) {
                    players.playSound(players, Sound.valueOf(config.getString("connection.join.sound.type")), Float.valueOf(config.getString("connection.join.sound.volume")), Float.valueOf(config.getString("connection.join.sound.pitch")));
                }
            } else {
                if (event.getPlayer().hasPermission("smpcore.join-message")) {
                    if (config.getBoolean("connection.join.sound.enable")) {
                        event.setJoinMessage(message.color(MessageFormat.format(config.getString("connection.join.message"), event.getPlayer().getName())));
                        sendMotd(event.getPlayer());
                        for (Player players : event.getPlayer().getServer().getOnlinePlayers()) {
                            players.playSound(players, Sound.valueOf(config.getString("connection.join.sound.type")), Float.valueOf(config.getString("connection.join.sound.volume")), Float.valueOf(config.getString("connection.join.sound.pitch")));
                        }
                    }
                } else {
                    event.setJoinMessage(null);
                    sendMotd(event.getPlayer());
                }
            }
        }
        if (event.getPlayer().hasPermission("smpcore.command.reload")) {
            new UpdateChecker(SMPCore.getInstance(), 108685).sendMessage(event.getPlayer());
        }
    }
    private void sendMotd(Player player) {
        if (playerConfig.locationExist(player, "quit-location")) {
            if (motdConfig.motdExist("welcome-back")) {
                motdConfig.sendMotd(player, "welcome-back");
            }
        } else {
            if (motdConfig.motdExist("welcome")) {
                motdConfig.sendMotd(player, "welcome");
            }
        }
    }
}