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
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            if (playerConfig.exist(offlinePlayer)) {
                if (offlinePlayer.isOnline()) {
                    Player target = offlinePlayer.getPlayer();
                    if (target == sender) {
                        execute(sender, target);
                    } else if (!target.hasPermission("smpcore.command.freeze.exempt")) {
                        execute(sender, target);
                    }
                } else {
                    execute(sender, offlinePlayer);
                }
            }
        }
        return true;
    }
    private void execute(CommandSender sender, OfflinePlayer target){
        playerConfig.setBoolean(target, "is-Frozen", !playerConfig.isFrozen(target));
        if (playerConfig.isFrozen(target)) {
            Message.send(sender, "&6You froze&f " + target.getName());
        } else {
            Message.send(sender, "&6You unfroze&f " + target.getName());
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            for (Player players : sender.getServer().getOnlinePlayers()) {
                commands.add(players.getName());
            }
        }
        return commands;
    }
}