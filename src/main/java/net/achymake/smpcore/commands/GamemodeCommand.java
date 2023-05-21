package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("adventure")) {
                    if (!player.getGameMode().equals(GameMode.ADVENTURE)){
                        player.setGameMode(GameMode.ADVENTURE);
                        message.send(player, "&6You changed gamemode to&f adventure");
                    }
                }
                if (args[0].equalsIgnoreCase("creative")) {
                    if (!player.getGameMode().equals(GameMode.CREATIVE)){
                        player.setGameMode(GameMode.CREATIVE);
                        message.send(player, "&6You changed gamemode to&f creative");
                    }
                }
                if (args[0].equalsIgnoreCase("survival")) {
                    if (!player.getGameMode().equals(GameMode.SURVIVAL)){
                        player.setGameMode(GameMode.SURVIVAL);
                        message.send(player, "&6You changed gamemode to&f survival");
                    }
                }
                if (args[0].equalsIgnoreCase("spectator")) {
                    if (!player.getGameMode().equals(GameMode.SPECTATOR)){
                        player.setGameMode(GameMode.SPECTATOR);
                        message.send(player, "&6You changed gamemode to&f spectator");
                    }
                }
            }
            if (args.length == 2) {
                if (sender.hasPermission("smpcore.command.gamemode.others")) {
                    Player target = sender.getServer().getPlayerExact(args[1]);
                    if (target == sender) {
                        if (args[0].equalsIgnoreCase("adventure")) {
                            if (!target.getGameMode().equals(GameMode.ADVENTURE)) {
                                target.setGameMode(GameMode.ADVENTURE);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f adventure");
                                message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f adventure");
                            }
                        }
                        if (args[0].equalsIgnoreCase("creative")) {
                            if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                                target.setGameMode(GameMode.CREATIVE);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f creative");
                                message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f creative");
                            }
                        }
                        if (args[0].equalsIgnoreCase("survival")) {
                            if (!target.getGameMode().equals(GameMode.SURVIVAL)) {
                                target.setGameMode(GameMode.SURVIVAL);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f survival");
                                message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f survival");
                            }
                        }
                        if (args[0].equalsIgnoreCase("spectator")) {
                            if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                                target.setGameMode(GameMode.SPECTATOR);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f spectator");
                                message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f spectator");
                            }
                        }
                    } else {
                        if (target != null) {
                            if (!target.hasPermission("smpcore.command.gamemode.exempt")) {
                                if (args[0].equalsIgnoreCase("adventure")) {
                                    if (!target.getGameMode().equals(GameMode.ADVENTURE)) {
                                        target.setGameMode(GameMode.ADVENTURE);
                                        message.send(target, sender.getName() + "&6 changed your gamemode to&f adventure");
                                        message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f adventure");
                                    }
                                }
                                if (args[0].equalsIgnoreCase("creative")) {
                                    if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                                        target.setGameMode(GameMode.CREATIVE);
                                        message.send(target, sender.getName() + "&6 changed your gamemode to&f creative");
                                        message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f creative");
                                    }
                                }
                                if (args[0].equalsIgnoreCase("survival")) {
                                    if (!target.getGameMode().equals(GameMode.SURVIVAL)) {
                                        target.setGameMode(GameMode.SURVIVAL);
                                        message.send(target, sender.getName() + "&6 changed your gamemode to&f survival");
                                        message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f survival");
                                    }
                                }
                                if (args[0].equalsIgnoreCase("spectator")) {
                                    if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                                        target.setGameMode(GameMode.SPECTATOR);
                                        message.send(target, sender.getName() + "&6 changed your gamemode to&f spectator");
                                        message.send(sender, "&6You changed gamemode of&f " + target.getName() + "&6 to&f spectator");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                Player target = sender.getServer().getPlayerExact(args[1]);
                if (target != null) {
                    if (!target.hasPermission("smpcore.command.gamemode.exempt")) {
                        if (args[0].equalsIgnoreCase("adventure")) {
                            if (!target.getGameMode().equals(GameMode.ADVENTURE)) {
                                target.setGameMode(GameMode.ADVENTURE);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f adventure");
                                message.send(sender, "You changed gamemode of " + target.getName() + " to adventure");
                            }
                        }
                        if (args[0].equalsIgnoreCase("creative")) {
                            if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                                target.setGameMode(GameMode.CREATIVE);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f creative");
                                message.send(sender, "You changed gamemode of " + target.getName() + " to creative");
                            }
                        }
                        if (args[0].equalsIgnoreCase("survival")) {
                            if (!target.getGameMode().equals(GameMode.SURVIVAL)) {
                                target.setGameMode(GameMode.SURVIVAL);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f survival");
                                message.send(sender, "You changed gamemode of " + target.getName() + " to survival");
                            }
                        }
                        if (args[0].equalsIgnoreCase("spectator")) {
                            if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                                target.setGameMode(GameMode.SPECTATOR);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f spectator");
                                message.send(sender, "You changed gamemode of " + target.getName() + " to spectator");
                            }
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
            if (sender.hasPermission("smpcore.command.gamemode.adventure")) {
                commands.add("adventure");
            }
            if (sender.hasPermission("smpcore.command.gamemode.creative")) {
                commands.add("creative");
            }
            if (sender.hasPermission("smpcore.command.gamemode.survival")) {
                commands.add("survival");
            }
            if (sender.hasPermission("smpcore.command.gamemode.spectator")) {
                commands.add("spectator");
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("smpcore.command.gamemode.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}