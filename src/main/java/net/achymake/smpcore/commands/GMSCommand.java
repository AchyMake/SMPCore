package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GMSCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                    player.setGameMode(GameMode.SURVIVAL);
                    message.send(player, "&6You changed gamemode to&f survival");
                }
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.gamemode.others")) {
                    Player target = player.getServer().getPlayerExact(args[0]);
                    if (target == sender) {
                        if (!target.getGameMode().equals(GameMode.SURVIVAL)) {
                            target.setGameMode(GameMode.SURVIVAL);
                            message.send(target, player.getName() + "&6 changed your gamemode to&f survival");
                            message.send(player, "&6You changed&f " + target.getName() + "&6 gamemode to&f survival");
                        }
                    } else {
                        if (target != null) {
                            if (!target.hasPermission("smpcore.command.gamemode.exempt")) {
                                if (!target.getGameMode().equals(GameMode.SURVIVAL)) {
                                    target.setGameMode(GameMode.SURVIVAL);
                                    message.send(target, player.getName() + "&6 changed your gamemode to&f survival");
                                    message.send(player, "&6You changed&f " + target.getName() + "&6 gamemode to&f survival");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    if (!target.getGameMode().equals(GameMode.SURVIVAL)) {
                        target.setGameMode(GameMode.SURVIVAL);
                        message.send(target, sender.getName() + "&6 changed your gamemode to&f survival");
                        message.send(sender, "You changed " + target.getName() + " gamemode to survival");
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
                if (player.hasPermission("smpcore.command.gamemode.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        if (!players.hasPermission("smpcore.command.gamemode.exempt")) {
                            commands.add(players.getName());
                        }
                    }
                }
            }
        }
        return commands;
    }
}