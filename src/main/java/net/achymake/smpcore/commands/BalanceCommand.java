package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.api.EconomyProvider;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BalanceCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final EconomyProvider economyProvider = smpCore.getEconomyProvider();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&6Balance:&a " + economyProvider.currencyNameSingular() + economyProvider.format(playerConfig.getEconomy(player)));
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.balance.others")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    if (playerConfig.exist(offlinePlayer)) {
                        message.send(player, offlinePlayer.getName() + " &6balance:&a " + economyProvider.currencyNameSingular() + economyProvider.format(playerConfig.getEconomy(offlinePlayer)));
                    } else {
                        message.send(player, offlinePlayer.getName() + "&c has never joined");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                if (sender.hasPermission("smpcore.command.balance.others")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    if (playerConfig.exist(offlinePlayer)) {
                        message.send(sender, offlinePlayer.getName() + " balance: " + economyProvider.currencyNameSingular() + economyProvider.format(playerConfig.getEconomy(offlinePlayer)));
                    } else {
                        message.send(sender, offlinePlayer.getName() + " has never joined");
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
                if (player.hasPermission("smpcore.command.balance.others")) {
                    for (OfflinePlayer offlinePlayer : player.getServer().getOfflinePlayers()) {
                        commands.add(offlinePlayer.getName());
                    }
                }
            }
        }
        return commands;
    }
}