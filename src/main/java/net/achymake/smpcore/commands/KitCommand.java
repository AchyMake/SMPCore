package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.KitConfig;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitCommand implements CommandExecutor, TabCompleter {
    private final KitConfig kitConfig = SMPCore.getKitConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                message.send(sender, "&6Kits:");
                for (String kitNames : kitConfig.get().getKeys(false)) {
                    if (sender.hasPermission("smpcore.command.kit." + kitNames)) {
                        message.send(sender, "- " + kitNames);
                    }
                }
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.kit." + args[0])) {
                    for (String kitNames : kitConfig.get().getKeys(false)) {
                        if (args[0].equals(kitNames)) {
                            kitConfig.giveKit(player, args[0]);
                        }
                    }
                }
            }
            if (args.length == 2) {
                if (sender.hasPermission("smpcore.command.kit.others")) {
                    Player target = sender.getServer().getPlayerExact(args[1]);
                    if (target != null) {
                        kitConfig.dropKit(target, args[0]);
                        message.send(target, "&6You received&f " + args[0] + "&6 kit");
                        message.send(sender, "&6You dropped&f " + args[0] + "&6 kit to&f " + target.getName());
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                if (sender.hasPermission("smpcore.command.kit.others")) {
                    Player target = sender.getServer().getPlayerExact(args[1]);
                    if (target != null) {
                        kitConfig.dropKit(target, args[0]);
                        message.send(target, "&6You received&f " + args[0] + "&6 kit");
                        message.send(sender, "You dropped " + args[0] + " kit to " + target.getName());
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
                for (String kitName : kitConfig.get().getKeys(false)) {
                    if (player.hasPermission("smpcore.command.kit." + kitName)) {
                        commands.add(kitName);
                    }
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.kit.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}