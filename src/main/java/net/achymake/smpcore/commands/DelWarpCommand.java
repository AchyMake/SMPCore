package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.WarpConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelWarpCommand implements CommandExecutor, TabCompleter {
    private final WarpConfig warpConfig = SMPCore.getWarpConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                message.send(sender, "&cUsage:&f /delwarp warpName");
            }
            if (args.length == 1) {
                if (warpConfig.warpExist(args[0])) {
                    warpConfig.delWarp(args[0]);
                    message.send(sender, args[0] + "&6 has been deleted");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                message.send(sender, "Usage: /delwarp warpName");
            }
            if (args.length == 1) {
                if (warpConfig.warpExist(args[0])) {
                    warpConfig.delWarp(args[0]);
                    message.send(sender, args[0] + " has been deleted");
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
                commands.addAll(warpConfig.getWarps());
            }
        }
        return commands;
    }
}