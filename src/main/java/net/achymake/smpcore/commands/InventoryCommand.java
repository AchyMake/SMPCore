package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InventoryCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&cUsage:&f /inventory target");
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                Player target = player.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    if (target != player) {
                        if (!target.hasPermission("smpcore.command.inventory.exempt")) {
                            player.openInventory(target.getInventory());
                            message.send(sender, "&6Opened inventory of " + target.getName());
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
                if (player.hasPermission("smpcore.command.inventory")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        if (!players.hasPermission("smpcore.command.inventory.exempt")) {
                            commands.add(players.getName());
                        }
                    }
                }
            }
        }
        return commands;
    }
}