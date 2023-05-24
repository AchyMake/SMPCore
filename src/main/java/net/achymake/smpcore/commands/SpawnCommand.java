package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.SpawnConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final SpawnConfig spawnConfig = SMPCore.getSpawnConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (playerConfig.isFrozen(player) || playerConfig.isJailed(player)) {
                    return false;
                } else {
                    if (spawnConfig.spawnExist()) {
                        spawnConfig.getSpawn().getChunk().load();
                        message.send(sender, "&6Teleporting to&f spawn");
                        player.teleport(spawnConfig.getSpawn());
                    }
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.spawn.others")) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    if (playerConfig.isFrozen(target) || playerConfig.isJailed(target)) {
                        return false;
                    } else {
                        if (spawnConfig.spawnExist()) {
                            spawnConfig.getSpawn().getChunk().load();
                            message.send(target, "&6Teleporting to&f spawn");
                            target.teleport(spawnConfig.getSpawn());
                            message.send(sender, "&6You teleported&f " + target.getName() + "&6 to&f spawn");
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
                if (player.hasPermission("smpcore.command.spawn.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}