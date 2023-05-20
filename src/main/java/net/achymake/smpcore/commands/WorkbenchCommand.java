package net.achymake.smpcore.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                player.openWorkbench(player.getLocation(),true);
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.workbench.others")) {
                    Player target = sender.getServer().getPlayerExact(args[0]);
                    if (target == sender) {
                        target.openWorkbench(target.getLocation(),true);
                    } else {
                        if (target != null) {
                            target.openWorkbench(target.getLocation(),true);
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    target.openWorkbench(target.getLocation(),true);
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.workbench.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()){
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}