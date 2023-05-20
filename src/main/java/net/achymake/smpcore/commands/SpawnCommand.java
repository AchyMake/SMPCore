package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.SpawnConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final SpawnConfig spawnConfig = smpCore.getSpawnConfig();
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
                        Message.send(sender, "&6Teleporting to&f spawn");
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
                            Message.send(target, "&6Teleporting to&f spawn");
                            target.teleport(spawnConfig.getSpawn());
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
            if (sender.hasPermission("smpcore.command.spawn.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}