package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PVPCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                togglePVP(player);
                if (playerConfig.isPVP(player)) {
                    message.send(player, "&6You enabled pvp");
                } else {
                    message.send(player, "&6You disabled pvp");
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.pvp.others")) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target == sender) {
                    togglePVP((Player) sender);
                    if (playerConfig.isPVP(target)) {
                        message.send(sender, "&6You enabled pvp for your self");
                    } else {
                        message.send(sender, "&6You disabled pvp for your self");
                    }
                } else {
                    if (target != null) {
                        if (!target.hasPermission("smpcore.command.pvp.exempt")) {
                            togglePVP(target);
                            if (playerConfig.isPVP(target)) {
                                message.send(target, sender.getName() + "&6 enabled pvp for you");
                                message.send(sender, "&6You enabled pvp for&f " + target.getName());
                            } else {
                                message.send(target, sender.getName() + "&6 disabled pvp for you");
                                message.send(sender, "&6You disabled pvp for&f " + target.getName());
                            }
                        }
                    } else {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                        if (playerConfig.exist(offlinePlayer)) {
                            togglePVP(offlinePlayer);
                            if (playerConfig.isPVP(offlinePlayer)) {
                                message.send(sender, "&6You enabled pvp for&f " + offlinePlayer.getName());
                            } else {
                                message.send(sender, "&6You disabled pvp for&f " + offlinePlayer.getName());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private void togglePVP(OfflinePlayer target) {
        playerConfig.setBoolean(target, "is-PVP", !playerConfig.isPVP(target));
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.pvp.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        if (!players.hasPermission("smpcore.command.pvp.exempt")) {
                            commands.add(players.getName());
                        }
                    }
                }
            }
        }
        return commands;
    }
}