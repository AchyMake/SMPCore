package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.MotdConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MotdCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final MotdConfig motdConfig = smpCore.getMotdConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                if (motdConfig.motdExist("message-of-the-day")) {
                    motdConfig.sendMotd(sender, "message-of-the-day");
                }
            }
            if (args.length == 1) {
                if (motdConfig.motdExist(args[0])) {
                    motdConfig.sendMotd(sender, args[0]);
                }
            }
            if (args.length == 2) {
                if (sender.hasPermission("smpcore.command.motd.others")) {
                    Player target = sender.getServer().getPlayerExact(args[1]);
                    if (target != null) {
                        if (motdConfig.motdExist(args[0])) {
                            motdConfig.sendMotd(target, args[0]);
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                if (motdConfig.motdExist("message-of-the-day")) {
                    motdConfig.sendMotd(sender, "message-of-the-day");
                }
            }
            if (args.length == 1) {
                if (motdConfig.motdExist(args[0])) {
                    motdConfig.sendMotd(sender, args[0]);
                }
            }
            if (args.length == 2) {
                Player target = sender.getServer().getPlayerExact(args[1]);
                if (target != null) {
                    if (motdConfig.motdExist(args[0])) {
                        motdConfig.sendMotd(target, args[0]);
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
                for (String motd : motdConfig.get().getKeys(false)) {
                    commands.add(motd);
                }
            }
            if (args.length == 2) {
                if (sender.hasPermission("smpcore.command.motd.others")) {
                    for (Player players : sender.getServer().getOnlinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}