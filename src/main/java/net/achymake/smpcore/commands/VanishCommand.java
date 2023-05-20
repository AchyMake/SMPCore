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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class VanishCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                playerConfig.setVanish(player, !playerConfig.isVanished(player));
                if (playerConfig.isVanished(player)) {
                    Message.send(player,"&6You are now vanished");
                } else {
                    Message.send(player, "&6You are no longer vanished");
                    Message.sendActionBar(player, "&6&lVanish:&c Disabled");
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.vanish.others")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (playerConfig.exist(offlinePlayer)) {
                    playerConfig.setVanish(offlinePlayer, !playerConfig.isVanished(offlinePlayer));
                    if (playerConfig.isVanished(offlinePlayer)) {
                        Message.send(sender, offlinePlayer.getName() + "&6 are now vanished");
                    } else {
                        Message.send(sender, offlinePlayer.getName() + "&6 are no longer vanished");
                    }
                } else {
                    Message.send(sender, MessageFormat.format("{0}&c has never joined", offlinePlayer.getName()));
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.vanish.others")) {
                for (OfflinePlayer offlinePlayer : sender.getServer().getOfflinePlayers()) {
                    commands.add(offlinePlayer.getName());
                }
            }
        }
        return commands;
    }
}