package net.achymake.smpcore.commands;

import net.achymake.smpcore.files.Message;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GMCCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    player.setGameMode(GameMode.CREATIVE);
                    Message.send(player, "&6You changed gamemode to&f creative");
                }
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.gamemode.others")) {
                    Player target = player.getServer().getPlayerExact(args[0]);
                    if (target == sender) {
                        if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                            target.setGameMode(GameMode.CREATIVE);
                            Message.send(target, player.getName() + "&6 changed your gamemode to&f creative");
                            Message.send(player, "&6You changed&f " + target.getName() + "&6 gamemode to&f creative");
                        }
                    } else {
                        if (target != null) {
                            if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                                target.setGameMode(GameMode.CREATIVE);
                                Message.send(target, player.getName() + "&6 changed your gamemode to&f creative");
                                Message.send(player, "&6You changed&f " + target.getName() + "&6 gamemode to&f creative");
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
                    if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                        target.setGameMode(GameMode.CREATIVE);
                        Message.send(target, sender.getName() + "&6 changed your gamemode to&f creative");
                        Message.send(sender, "You changed " + target.getName() + " gamemode to creative");
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