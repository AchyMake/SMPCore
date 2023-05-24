package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                playerConfig.setVanish(player, !playerConfig.isVanished(player));
                if (playerConfig.isVanished(player)) {
                    message.send(player,"&6You are now vanished");
                } else {
                    message.send(player, "&6You are no longer vanished");
                    message.sendActionBar(player, "&6&lVanish:&c Disabled");
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.vanish.others")) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    playerConfig.setVanish(target, !playerConfig.isVanished(target));
                    if (playerConfig.isVanished(target)) {
                        message.send(target, sender.getName() + "&6 made you vanish");
                        message.send(sender, target.getName() + "&6 is now vanished");
                    } else {
                        message.send(target, sender.getName() + "&6 made you no longer vanish");
                        message.send(sender, target.getName() + "&6 is no longer vanished");
                    }
                } else {
                    OfflinePlayer offlinePlayer = sender.getServer().getOfflinePlayer(args[0]);
                    if (playerConfig.exist(offlinePlayer)) {
                        playerConfig.setVanish(offlinePlayer, !playerConfig.isVanished(offlinePlayer));
                        if (playerConfig.isVanished(offlinePlayer)) {
                            message.send(sender, offlinePlayer.getName() + "&6 is now vanished");
                        } else {
                            message.send(sender, offlinePlayer.getName() + "&6 is no longer vanished");
                        }
                    } else {
                        message.send(sender, offlinePlayer.getName() + "&c has never joined");
                    }
                }
            }
        }
        if (args.length == 2) {
            Player target = sender.getServer().getPlayerExact(args[0]);
            boolean value = Boolean.valueOf(args[1]);
            if (value) {
                if (target != null) {
                    if (!playerConfig.isVanished(target)) {
                        playerConfig.setVanish(target, true);
                        message.send(target, sender.getName() + "&6 made you vanish");
                        message.send(sender, target.getName() + "&6 is now vanished");
                    }
                } else {
                    OfflinePlayer offlinePlayer = sender.getServer().getOfflinePlayer(args[0]);
                    if (playerConfig.exist(offlinePlayer)) {
                        if (!playerConfig.isVanished(offlinePlayer)) {
                            playerConfig.setVanish(offlinePlayer, true);
                            if (playerConfig.isVanished(offlinePlayer)) {
                                message.send(sender, offlinePlayer.getName() + "&6 is now vanished");
                            } else {
                                message.send(sender, offlinePlayer.getName() + "&6 is no longer vanished");
                            }
                        }
                    } else {
                        message.send(sender, offlinePlayer.getName() + "&c has never joined");
                    }
                }
            } else {
                if (target != null) {
                    if (playerConfig.isVanished(target)) {
                        playerConfig.setVanish(target, false);
                        message.send(target, sender.getName() + "&6 made you no longer vanish");
                        message.send(sender, target.getName() + "&6 is no longer vanished");
                    }
                } else {
                    OfflinePlayer offlinePlayer = sender.getServer().getOfflinePlayer(args[0]);
                    if (playerConfig.exist(offlinePlayer)) {
                        if (playerConfig.isVanished(offlinePlayer)) {
                            playerConfig.setVanish(offlinePlayer, false);
                            if (playerConfig.isVanished(offlinePlayer)) {
                                message.send(sender, offlinePlayer.getName() + "&6 is now vanished");
                            } else {
                                message.send(sender, offlinePlayer.getName() + "&6 is no longer vanished");
                            }
                        }
                    } else {
                        message.send(sender, offlinePlayer.getName() + "&c has never joined");
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
                if (player.hasPermission("smpcore.command.vanish.others")) {
                    for (OfflinePlayer offlinePlayer : player.getServer().getOfflinePlayers()) {
                        commands.add(offlinePlayer.getName());
                    }
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.vanish.others")) {
                    commands.add("true");
                    commands.add("false");
                }
            }
        }
        return commands;
    }
}