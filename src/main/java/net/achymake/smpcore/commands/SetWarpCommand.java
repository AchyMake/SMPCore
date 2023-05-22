package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.WarpConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetWarpCommand implements CommandExecutor, TabCompleter {
    private final WarpConfig warpConfig = SMPCore.getWarpConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&cUsage:&f /setwarp warpName");
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (warpConfig.warpExist(args[0])) {
                    warpConfig.setWarp(player.getLocation(), args[0]);
                    message.send(sender, args[0] + "&6 has been relocated");
                } else {
                    warpConfig.setWarp(player.getLocation(), args[0]);
                    message.send(sender, args[0] + "&6 has been set");
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