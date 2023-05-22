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

public class PayCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final EconomyProvider economyProvider = smpCore.getEconomyProvider();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&cUsage:&f /pay target amount");
            }
            if (args.length == 2) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (playerConfig.exist(offlinePlayer)) {
                    if (economyProvider.has((OfflinePlayer) sender, Double.parseDouble(args[1]))) {
                        economyProvider.withdrawPlayer((OfflinePlayer) sender, Double.parseDouble(args[1]));
                        economyProvider.depositPlayer(offlinePlayer, Double.parseDouble(args[1]));
                        message.send(sender, "&6You paid&f " + offlinePlayer.getName() + "&a " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[1])));
                    } else {
                        message.send(sender, "&cYou don't have&a " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[1])) + "&c to pay&f " + offlinePlayer.getName());
                    }
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
                for (Player players : player.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}