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
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                playerConfig.setVanish(player, !playerConfig.isVanished(player));
                if (playerConfig.isVanished(player)) {
                    message.send(player,"&6You are now vanished");
                } else {
                    message.send(player, "&6You are no longer vanished");
                    message.sendActionBar(player, "&6&lVanish:&c Disabled");
                }
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.vanish.others")) {
                    Player target = player.getServer().getPlayerExact(args[0]);
                    if (target != null) {
                        playerConfig.setVanish(target, !playerConfig.isVanished(target));
                        if (playerConfig.isVanished(target)) {
                            message.send(target, player.getName() + "&6 made you vanish");
                            message.send(player, target.getName() + "&6 is now vanished");
                        } else {
                            message.send(target, player.getName() + "&6 made you no longer vanish");
                            message.send(player, target.getName() + "&6 is no longer vanished");
                        }
                    } else {
                        OfflinePlayer offlinePlayer = player.getServer().getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            playerConfig.setVanish(offlinePlayer, !playerConfig.isVanished(offlinePlayer));
                            if (playerConfig.isVanished(offlinePlayer)) {
                                message.send(player, offlinePlayer.getName() + "&6 is now vanished");
                            } else {
                                message.send(player, offlinePlayer.getName() + "&6 is no longer vanished");
                            }
                        } else {
                            message.send(sender, offlinePlayer.getName() + "&c has never joined");
                        }
                    }
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                Player target = player.getServer().getPlayerExact(args[0]);
                boolean value = Boolean.valueOf(args[1]);
                if (value) {
                    if (target != null) {
                        if (!playerConfig.isVanished(target)) {
                            playerConfig.setVanish(target, true);
                            message.send(target, player.getName() + "&6 made you vanish");
                            message.send(player, target.getName() + "&6 is now vanished");
                        }
                    } else {
                        OfflinePlayer offlinePlayer = player.getServer().getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            if (!playerConfig.isVanished(offlinePlayer)) {
                                playerConfig.setVanish(offlinePlayer, true);
                                if (playerConfig.isVanished(offlinePlayer)) {
                                    message.send(player, offlinePlayer.getName() + "&6 is now vanished");
                                } else {
                                    message.send(player, offlinePlayer.getName() + "&6 is no longer vanished");
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
                            message.send(target, player.getName() + "&6 made you no longer vanish");
                            message.send(player, target.getName() + "&6 is no longer vanished");
                        }
                    } else {
                        OfflinePlayer offlinePlayer = player.getServer().getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            if (playerConfig.isVanished(offlinePlayer)) {
                                playerConfig.setVanish(offlinePlayer, false);
                                if (playerConfig.isVanished(offlinePlayer)) {
                                    message.send(player, offlinePlayer.getName() + "&6 is now vanished");
                                } else {
                                    message.send(player, offlinePlayer.getName() + "&6 is no longer vanished");
                                }
                            }
                        } else {
                            message.send(sender, offlinePlayer.getName() + "&c has never joined");
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                if (sender.hasPermission("smpcore.command.vanish.others")) {
                    Player target = sender.getServer().getPlayerExact(args[0]);
                    if (target != null) {
                        playerConfig.setVanish(target, !playerConfig.isVanished(target));
                        if (playerConfig.isVanished(target)) {
                            message.send(target,"&6You are now vanished");
                            message.send(sender, target.getName() + " is now vanished");
                        } else {
                            message.send(target,"&6You are no longer vanished");
                            message.send(sender, target.getName() + " is no longer vanished");
                        }
                    } else {
                        OfflinePlayer offlinePlayer = sender.getServer().getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            playerConfig.setVanish(offlinePlayer, !playerConfig.isVanished(offlinePlayer));
                            if (playerConfig.isVanished(offlinePlayer)) {
                                message.send(sender, offlinePlayer.getName() + " is now vanished");
                            } else {
                                message.send(sender, offlinePlayer.getName() + " is no longer vanished");
                            }
                        } else {
                            message.send(sender, offlinePlayer.getName() + " has never joined");
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
                            message.send(target,"&6You are now vanished");
                            message.send(sender, target.getName() + " is now vanished");
                        }
                    } else {
                        OfflinePlayer offlinePlayer = sender.getServer().getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            if (!playerConfig.isVanished(offlinePlayer)) {
                                playerConfig.setVanish(offlinePlayer, true);
                                if (playerConfig.isVanished(offlinePlayer)) {
                                    message.send(sender, offlinePlayer.getName() + " is now vanished");
                                } else {
                                    message.send(sender, offlinePlayer.getName() + " is no longer vanished");
                                }
                            }
                        } else {
                            message.send(sender, offlinePlayer.getName() + " has never joined");
                        }
                    }
                } else {
                    if (target != null) {
                        if (playerConfig.isVanished(target)) {

                        }
                        playerConfig.setVanish(target, false);
                        message.send(target,"&6You are no longer vanished");
                        message.send(sender, target.getName() + " is no longer vanished");
                    } else {
                        OfflinePlayer offlinePlayer = sender.getServer().getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            if (playerConfig.isVanished(offlinePlayer)) {
                                playerConfig.setVanish(offlinePlayer, false);
                                if (playerConfig.isVanished(offlinePlayer)) {
                                    message.send(sender, offlinePlayer.getName() + " is now vanished");
                                } else {
                                    message.send(sender, offlinePlayer.getName() + " is no longer vanished");
                                }
                            }
                        } else {
                            message.send(sender, offlinePlayer.getName() + " has never joined");
                        }
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.vanish.others")) {
                for (OfflinePlayer offlinePlayer : sender.getServer().getOfflinePlayers()) {
                    commands.add(offlinePlayer.getName());
                }
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("smpcore.command.vanish.others")) {
                commands.add("true");
                commands.add("false");
            }
        }
        return commands;
    }
}