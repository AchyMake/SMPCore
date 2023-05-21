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
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0){
                Player player = (Player) sender;
                message.send(player, "&6Kits:");
                for (String kitNames : kitConfig.get().getKeys(false)) {
                    if (player.hasPermission("smpcore.command.kit." + kitNames)) {
                        message.send(player, "- " + kitNames);
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
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.kit.others")) {
                    Player target = player.getServer().getPlayerExact(args[1]);
                    if (target != null) {
                        kitConfig.dropKit(target, args[0]);
                        message.send(target, "&6You received&f " + args[0] + "&6 kit");
                        message.send(player, "&6You dropped&f " + args[0] + "&6 kit to&f " + target.getName());
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