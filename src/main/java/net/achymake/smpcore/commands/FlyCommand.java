package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand implements CommandExecutor, TabCompleter {
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                player.setAllowFlight(!player.getAllowFlight());
                if (player.getAllowFlight()){
                    message.send(sender, "&6Enabled fly");
                } else {
                    message.send(sender, "&6Disabled fly");
                }
            }
            if (args.length == 1) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    if (sender.hasPermission("smpcore.command.fly.others")) {
                        if (!target.hasPermission("smpcore.command.fly.exempt")) {
                            target.setAllowFlight(!target.getAllowFlight());
                            if (target.getAllowFlight()){
                                message.send(target, "&6Enabled fly");
                                message.send(sender, "&6You enabled fly for&f " + target.getName());
                            } else {
                                message.send(target, "&6Disabled fly");
                                message.send(sender, "&6You disabled fly for&f " + target.getName());
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
                    target.setAllowFlight(!target.getAllowFlight());
                    if (target.getAllowFlight()){
                        message.send(target, "&6Enabled fly");
                        message.send(sender, "You enabled fly for " + target.getName());
                    } else {
                        message.send(target, "&6Disabled fly");
                        message.send(sender, "You disabled fly for " + target.getName());
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
                if (player.hasPermission("smpcore.command.fly.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        if (!players.hasPermission("smpcore.command.fly.exempt")) {
                            commands.add(players.getName());
                        }
                    }
                }
            }
        }
        return commands;
    }
}