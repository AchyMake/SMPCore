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

public class EcoCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final EconomyProvider economyProvider = SMPCore.getEconomyProvider();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            message.send(sender, "&cUsage:&f /eco add target amount");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reset")) {
                if (sender.hasPermission("smpcore.command.eco.reset")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (playerConfig.exist(offlinePlayer)) {
                        playerConfig.resetEconomy(offlinePlayer);
                        message.send(sender, "&6You reset&f " + offlinePlayer.getName() + "&6 account to&a " + economyProvider.currencyNameSingular() + economyProvider.format(SMPCore.getInstance().getConfig().getDouble("economy.starting-balance")));
                    } else {
                        message.send(sender, offlinePlayer.getName() + "&c has never joined");
                    }
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add")) {
                if (sender.hasPermission("smpcore.command.eco.add")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (playerConfig.exist(offlinePlayer)) {
                        playerConfig.addEconomy(offlinePlayer, Double.parseDouble(args[2]));
                        message.send(sender, "&6You added&a " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[2])) + "&6 to&f " + offlinePlayer.getName() + "&6 account");
                    } else {
                        message.send(sender, offlinePlayer.getName() + "&c has never joined");
                    }
                }
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (sender.hasPermission("smpcore.command.eco.remove")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (playerConfig.exist(offlinePlayer)) {
                        playerConfig.removeEconomy(offlinePlayer, Double.parseDouble(args[2]));
                        message.send(sender, "&6You removed&a " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[2])) + "&6 from&f " + offlinePlayer.getName() + "&6 account");
                    } else {
                        message.send(sender, offlinePlayer.getName() + "&c has never joined");
                    }
                }
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (sender.hasPermission("smpcore.command.eco.set")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (playerConfig.exist(offlinePlayer)) {
                        playerConfig.setEconomy(offlinePlayer, Double.parseDouble(args[2]));
                        message.send(sender, "&6You set&a " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[2])) + "&6 to&f " + offlinePlayer.getName() + "&6 account");
                    } else {
                        message.send(sender, offlinePlayer.getName() + "&c has never joined");
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
                if (player.hasPermission("smpcore.command.eco.add")) {
                    commands.add("add");
                }
                if (player.hasPermission("smpcore.command.eco.remove")) {
                    commands.add("remove");
                }
                if (player.hasPermission("smpcore.command.eco.reset")) {
                    commands.add("reset");
                }
                if (player.hasPermission("smpcore.command.eco.set")) {
                    commands.add("set");
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.eco.add")) {
                    for (OfflinePlayer players : player.getServer().getOfflinePlayers()) {
                        commands.add(players.getName());
                    }
                }
                if (player.hasPermission("smpcore.command.eco.remove")) {
                    for (OfflinePlayer players : player.getServer().getOfflinePlayers()) {
                        commands.add(players.getName());
                    }
                }
                if (player.hasPermission("smpcore.command.eco.reset")) {
                    for (OfflinePlayer players : player.getServer().getOfflinePlayers()) {
                        commands.add(players.getName());
                    }
                }
                if (player.hasPermission("smpcore.command.eco.set")) {
                    for (OfflinePlayer players : player.getServer().getOfflinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}