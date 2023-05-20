package net.achymake.smpcore.commands;

import net.achymake.smpcore.files.Message;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GMSPCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
                    player.setGameMode(GameMode.SPECTATOR);
                    Message.send(player, "&6You changed gamemode to&f spectator");
                }
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.gamemode.others")) {
                    Player target = player.getServer().getPlayerExact(args[0]);
                    if (target == sender) {
                        if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                            target.setGameMode(GameMode.SPECTATOR);
                            Message.send(target, player.getName() + "&6 changed your gamemode to&f spectator");
                            Message.send(player, "&6You changed&f " + target.getName() + "&6 gamemode to&f spectator");
                        }
                    } else {
                        if (target != null) {
                            if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                                target.setGameMode(GameMode.SPECTATOR);
                                Message.send(target, player.getName() + "&6 changed your gamemode to&f spectator");
                                Message.send(player, "&6You changed&f " + target.getName() + "&6 gamemode to&f spectator");
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
                    if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                        target.setGameMode(GameMode.SPECTATOR);
                        Message.send(target, sender.getName() + "&6 changed your gamemode to&f spectator");
                        Message.send(sender, "You changed " + target.getName() + " gamemode to spectator");
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
            if (sender.hasPermission("smpcore.command.gamemode.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}