package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.SpawnConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SetSpawnCommand implements CommandExecutor, TabCompleter {
    private final SpawnConfig spawnConfig = SMPCore.getSpawnConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (spawnConfig.spawnExist()) {
                    spawnConfig.setSpawn(player.getLocation());
                    message.send(sender, "&6Spawn relocated");
                } else {
                    spawnConfig.setSpawn(player.getLocation());
                    message.send(sender, "&6Spawn has been set");
                }
            }
        }
        return true;
    }
    @Override
    public List onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }
}