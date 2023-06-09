package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FreezeCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            message.send(sender, "&cUsage:&f /freeze target");
        }
        if (args.length == 1) {
            Player player = (Player) sender;
            Player target = player.getServer().getPlayerExact(args[0]);
            if (target == sender) {
                playerConfig.setBoolean(target, "is-Frozen", !playerConfig.isFrozen(target));
                if (playerConfig.isFrozen(target)) {
                    message.send(sender, "&6You froze&f " + target.getName());
                } else {
                    message.send(sender, "&6You unfroze&f " + target.getName());
                }
            } else {
                if (target != null) {
                    if (!target.hasPermission("smpcore.command.freeze.exempt")) {
                        playerConfig.setBoolean(target, "is-Frozen", !playerConfig.isFrozen(target));
                        if (playerConfig.isFrozen(target)) {
                            message.send(sender, "&6You froze&f " + target.getName());
                        } else {
                            message.send(sender, "&6You unfroze&f " + target.getName());
                        }
                    }
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    if (playerConfig.exist(offlinePlayer)) {
                        playerConfig.setBoolean(offlinePlayer, "is-Frozen", !playerConfig.isFrozen(offlinePlayer));
                        if (playerConfig.isFrozen(offlinePlayer)) {
                            message.send(sender, "&6You froze&f " + offlinePlayer.getName());
                        } else {
                            message.send(sender, "&6You unfroze&f " + offlinePlayer.getName());
                        }
                    } else {
                        message.send(player, offlinePlayer.getName() + "&c has never joined");
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
                for (Player players : player.getServer().getOnlinePlayers()) {
                    if (!players.hasPermission("smpcore.command.freeze.exempt")) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}