package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.KitConfig;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final KitConfig kitConfig = smpCore.getKitConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Message.send(player, "&6Kits:");
                for (String kitNames : kitConfig.get().getKeys(false)) {
                    if (player.hasPermission("smpcore.command.kit." + kitNames)) {
                        Message.send(player, "- " + kitNames);
                    }
                }
            }
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String kitName = args[0];
                if (player.hasPermission("smpcore.command.kit." + kitName)) {
                    for (String kitNames : kitConfig.get().getKeys(false)) {
                        if (kitName.equals(kitNames)) {
                            kitConfig.giveKit(player,kitName);
                        }
                    }
                }
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("smpcore.command.kit.others")) {
                String kitName = args[0];
                Player target = sender.getServer().getPlayerExact(args[1]);
                if (target != null) {
                    kitConfig.dropKit(target, kitName);
                    Message.send(target, "&6You received&f " + kitName + "&6 kit");
                    Message.send(sender, "&6You dropped&f " + kitName + "&6 kit to&f " + target.getName());
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            for (String kitName : kitConfig.get().getKeys(false)) {
                if (sender.hasPermission("smpcore.command.kit." + kitName)) {
                    commands.add(kitName);
                }
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("smpcore.command.kit.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}