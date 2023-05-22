package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MuteCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&cUsage:&f /mute target");
            }
            if (args.length == 1) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target == sender) {
                    playerConfig.setBoolean(target, "is-Muted", !playerConfig.isMuted(target));
                    if (playerConfig.isMuted(target)) {
                        message.send(sender, "&6You muted&f " + target.getName());
                    } else {
                        message.send(sender, "&6You unmuted&f " + target.getName());
                    }
                } else {
                    if (target != null) {
                        if (!target.hasPermission("smpcore.command.mute.exempt")) {
                            playerConfig.setBoolean(target, "is-Muted", !playerConfig.isMuted(target));
                            if (playerConfig.isMuted(target)) {
                                message.send(sender, "&6You muted&f " + target.getName());
                            } else {
                                message.send(sender, "&6You unmuted&f " + target.getName());
                            }
                        }
                    } else {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            playerConfig.setBoolean(offlinePlayer, "is-Muted", !playerConfig.isMuted(offlinePlayer));
                            if (playerConfig.isMuted(offlinePlayer)) {
                                message.send(sender, "&6You muted&f " + offlinePlayer.getName());
                            } else {
                                message.send(sender, "&6You unmuted&f " + offlinePlayer.getName());
                            }
                        }else {
                            message.send(sender,offlinePlayer.getName() + "&c has never joined");
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    playerConfig.setBoolean(target, "is-Muted", !playerConfig.isMuted(target));
                    if (playerConfig.isMuted(target)) {
                        message.send(sender, "You muted " + target.getName());
                    } else {
                        message.send(sender, "You unmuted " + target.getName());
                    }
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    if (playerConfig.exist(offlinePlayer)) {
                        playerConfig.setBoolean(offlinePlayer, "is-Muted", !playerConfig.isMuted(offlinePlayer));
                        if (playerConfig.isMuted(offlinePlayer)) {
                            message.send(sender, "You muted " + offlinePlayer.getName());
                        } else {
                            message.send(sender, "You unmuted " + offlinePlayer.getName());
                        }
                    }else {
                        message.send(sender,offlinePlayer.getName() + " has never joined");
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                for (Player players : player.getServer().getOnlinePlayers()) {
                    if (!players.hasPermission("smpcore.command.mute.exempt")) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}