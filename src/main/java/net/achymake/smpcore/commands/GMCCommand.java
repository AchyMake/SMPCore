package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GMCCommand implements CommandExecutor, TabCompleter {
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    player.setGameMode(GameMode.CREATIVE);
                    message.send(player, "&6You changed gamemode to&f creative");
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.gamemode.others")) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target == sender) {
                    if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                        target.setGameMode(GameMode.CREATIVE);
                        message.send(sender, "&6You changed gamemode to&f creative");
                    }
                } else {
                    if (target != null) {
                        if (!target.hasPermission("smpcore.command.gamemode.exempt")) {
                            if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                                target.setGameMode(GameMode.CREATIVE);
                                message.send(target, sender.getName() + "&6 changed your gamemode to&f creative");
                                message.send(sender, "&6You changed&f " + target.getName() + "&6 gamemode to&f creative");
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