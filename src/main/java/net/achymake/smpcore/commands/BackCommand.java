package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BackCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (playerConfig.isFrozen(player) || playerConfig.isJailed(player)) {
                    return false;
                } else {
                    if (player.hasPermission("smpcore.command.back.death")) {
                        if (player.getLastDeathLocation() == null) {
                            back(player);
                        } else {
                            player.getLastDeathLocation().getChunk().load();
                            Message.send(player, "&6Teleporting to&f death location");
                            player.teleport(player.getLastDeathLocation());
                            player.setLastDeathLocation(null);
                        }
                    } else {
                        back(player);
                    }
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.back.others")) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    if (playerConfig.isFrozen(target) || playerConfig.isJailed(target)) {
                        return false;
                    } else {
                        if (target.hasPermission("smpcore.command.back.death")) {
                            if (target.getLastDeathLocation() == null) {
                                back(target);
                            } else {
                                target.getLastDeathLocation().getChunk().load();
                                Message.send(target, "&6Teleporting to&f death location");
                                target.teleport(target.getLastDeathLocation());
                                target.setLastDeathLocation(null);
                            }
                        } else {
                            back(target);
                        }
                    }
                }
            }
        }
        return true;
    }
    private void back(Player player){
        if (playerConfig.locationExist(player,"last-location")) {
            playerConfig.getLocation(player,"last-location").getChunk().load();
            Message.send(player, "&6Teleporting to&f recent location");
            player.teleport(playerConfig.getLocation(player,"last-location"));
        } else {
            Message.send(player, "&cRecent location either removed or has never been set");
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.back.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}