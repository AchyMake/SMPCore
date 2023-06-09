package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchCommand implements CommandExecutor, TabCompleter {
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.openWorkbench(player.getLocation(), true);
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.workbench.others")) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target == sender) {
                    target.openWorkbench(target.getLocation(), true);
                    message.send(sender, "&6You opened crafting table for your self");
                } else {
                    if (target != null) {
                        target.openWorkbench(target.getLocation(), true);
                        message.send(target, sender.getName() + "&6 opened crafting table for you");
                        message.send(sender, "&6You opened crafting table for " + target.getName());
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
                if (player.hasPermission("smpcore.command.workbench.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}