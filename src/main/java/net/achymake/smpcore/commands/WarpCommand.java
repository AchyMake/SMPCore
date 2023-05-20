package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.WarpConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final WarpConfig warpConfig = smpCore.getWarpConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (playerConfig.isFrozen((Player) sender) || playerConfig.isJailed((Player) sender)) {
                return false;
            } else {
                if (args.length == 1) {
                    Player player = (Player) sender;
                    String warpName = args[0];
                    if (sender.hasPermission("smpcore.command.warp." + warpName)) {
                        if (warpConfig.warpExist(warpName)) {
                            warpConfig.getWarp(warpName).getChunk().load();
                            Message.send(sender, "&6Teleporting to&f "+ warpName);
                            player.teleport(warpConfig.getWarp(warpName));
                        }
                    }
                }
                if (args.length == 2) {
                    if (sender.hasPermission("smpcore.command.warp.others")) {
                        String warpName = args[0];
                        if (sender.hasPermission("players.command.warp." + warpName)) {
                            Player target = sender.getServer().getPlayerExact(args[1]);
                            if (target != null) {
                                if (playerConfig.isFrozen(target) || playerConfig.isJailed(target)) {
                                    return false;
                                } else {
                                    if (warpConfig.warpExist(warpName)) {
                                        warpConfig.getWarp(warpName).getChunk().load();
                                        Message.send(target, "&6Teleporting to&f " + warpName);
                                        target.teleport(warpConfig.getWarp(warpName));
                                    }
                                }
                            }
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
        if (args.length == 1) {
            for (String warps : warpConfig.getWarps()) {
                if (sender.hasPermission("smpcore.command.warp." + warps)) {
                    commands.add(warps);
                }
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("smpcore.command.warp.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}