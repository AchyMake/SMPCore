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

public class PVPCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            if (sender instanceof Player){
                Player player = (Player) sender;
                playerConfig.setBoolean(player, "is-PVP", !playerConfig.isPVP(player));
                if (playerConfig.isPVP(player)) {
                    Message.send(player, "&6You enabled pvp");
                } else {
                    Message.send(player, "&6You disabled pvp");
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.pvp.others")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (playerConfig.exist(offlinePlayer)) {
                    playerConfig.setBoolean(offlinePlayer, "is-PVP", !playerConfig.isPVP(offlinePlayer));
                    if (playerConfig.isPVP(offlinePlayer)) {
                        Message.send(sender, "&6You enabled pvp for&f " + offlinePlayer.getName());
                    } else {
                        Message.send(sender, "&6You disabled pvp for&f " + offlinePlayer.getName());
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
            if (sender.hasPermission("smpcore.command.pvp.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}