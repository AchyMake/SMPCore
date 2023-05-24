package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.api.EconomyProvider;
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

public class BalanceCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final EconomyProvider economyProvider = SMPCore.getEconomyProvider();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                message.send(sender, "&6Balance:&a " + economyProvider.currencyNameSingular() + economyProvider.format(playerConfig.getEconomy((Player) sender)));
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.balance.others")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (playerConfig.exist(offlinePlayer)) {
                    message.send(sender, offlinePlayer.getName() + " &6balance:&a " + economyProvider.currencyNameSingular() + economyProvider.format(playerConfig.getEconomy(offlinePlayer)));
                } else {
                    message.send(sender, offlinePlayer.getName() + "&c has never joined");
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