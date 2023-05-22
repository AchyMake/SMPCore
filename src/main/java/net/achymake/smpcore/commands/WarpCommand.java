package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.WarpConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final WarpConfig warpConfig = SMPCore.getWarpConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (playerConfig.isFrozen((Player) sender) || playerConfig.isJailed((Player) sender)) {
                return false;
            } else {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    message.send(player, "&cUsage:&f /warp warpName");
                }
                if (args.length == 1) {
                    Player player = (Player) sender;
                    if (player.hasPermission("smpcore.command.warp." + args[0])) {
                        if (warpConfig.warpExist(args[0])) {
                            warpConfig.getWarp(args[0]).getChunk().load();
                            message.send(player, "&6Teleporting to&f "+ args[0]);
                            player.teleport(warpConfig.getWarp(args[0]));
                        }
                    }
                }
                if (args.length == 2) {
                    Player player = (Player) sender;
                    if (player.hasPermission("smpcore.command.warp.others")) {
                        if (player.hasPermission("smpcore.command.warp." + args[0])) {
                            Player target = player.getServer().getPlayerExact(args[1]);
                            if (target != null) {
                                if (playerConfig.isFrozen(target) || playerConfig.isJailed(target)) {
                                    return false;
                                } else {
                                    if (warpConfig.warpExist(args[0])) {
                                        warpConfig.getWarp(args[0]).getChunk().load();
                                        message.send(target, "&6Teleporting to&f " + args[0]);
                                        target.teleport(warpConfig.getWarp(args[0]));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                Player target = sender.getServer().getPlayerExact(args[1]);
                if (target != null) {
                    if (playerConfig.isFrozen(target) || playerConfig.isJailed(target)) {
                        return false;
                    } else {
                        if (warpConfig.warpExist(args[0])) {
                            warpConfig.getWarp(args[0]).getChunk().load();
                            message.send(target, "&6Teleporting to&f " + args[0]);
                            target.teleport(warpConfig.getWarp(args[0]));
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
                for (String warps : warpConfig.getWarps()) {
                    if (player.hasPermission("smpcore.command.warp." + warps)) {
                        commands.add(warps);
                    }
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.warp.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}