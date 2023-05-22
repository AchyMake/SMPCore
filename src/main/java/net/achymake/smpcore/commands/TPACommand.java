package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TPACommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final PlayerData playerData = smpCore.getPlayerData();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&cUsage:&f /tpa target");
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (playerConfig.isFrozen(player) || playerConfig.isJailed(player)) {
                    return false;
                } else {
                    Player target = sender.getServer().getPlayerExact(args[0]);
                    if (target == null) {
                        message.send(player, args[0] + "&c is currently offline");
                    } else if (target == player) {
                        message.send(player, "&cYou can't send request to your self");
                    } else if (playerData.hasString(player, "tpa.sent")) {
                        message.send(player, "&cYou already sent tpa request");
                        message.send(player, "&cYou can type&f /tpcancel");
                    } else {
                        int taskID = player.getServer().getScheduler().runTaskLater(SMPCore.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                playerData.removeData(target, "tpa.from");
                                playerData.removeData(player, "tpa.sent");
                                playerData.removeData(player, "task.tpa");
                                message.send(player, "&cTeleport request has expired");
                                message.send(target, "&cTeleport request has expired");
                            }
                        }, 300).getTaskId();
                        playerData.setString(player, "tpa.sent", target.getUniqueId().toString());
                        playerData.setString(target, "tpa.from", player.getUniqueId().toString());
                        playerData.setInt(player, "task.tpa", taskID);
                        message.send(target, player.getName() + "&6 has sent you a tpa request");
                        message.send(target, "&6You can type&a /tpaccept&6 or&c /tpdeny");
                        message.send(player, "&6You have sent a tpa request to&f " + target.getName());
                        message.send(player, "&6You can type&c /tpcancel");
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
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}