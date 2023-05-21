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

public class EcoCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final EconomyProvider economyProvider = smpCore.getEconomyProvider();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("reset")) {
                    if (sender.hasPermission("smpcore.command.eco.reset")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (playerConfig.exist(offlinePlayer)) {
                            playerConfig.resetEconomy(offlinePlayer);
                            message.send(sender, "&6You reset&f " + offlinePlayer.getName() + "&6 account to&a " + economyProvider.currencyNameSingular() + economyProvider.format(smpCore.getConfig().getDouble("economy.starting-balance")));
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
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("reset")) {
                    if (sender.hasPermission("smpcore.command.eco.reset")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (playerConfig.exist(offlinePlayer)) {
                            playerConfig.resetEconomy(offlinePlayer);
                            message.send(sender, "You reset " + offlinePlayer.getName() + " account to " + economyProvider.currencyNameSingular() + economyProvider.format(smpCore.getConfig().getDouble("economy.starting-balance")));
                        } else {
                            message.send(sender, offlinePlayer.getName() + " has never joined");
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
                            message.send(sender, "You added " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[2])) + " to " + offlinePlayer.getName() + " account");
                        } else {
                            message.send(sender, offlinePlayer.getName() + " has never joined");
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    if (sender.hasPermission("smpcore.command.eco.remove")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (playerConfig.exist(offlinePlayer)) {
                            playerConfig.removeEconomy(offlinePlayer, Double.parseDouble(args[2]));
                            message.send(sender, "You removed " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[2])) + " from " + offlinePlayer.getName() + " account");
                        } else {
                            message.send(sender, offlinePlayer.getName() + " has never joined");
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("set")) {
                    if (sender.hasPermission("smpcore.command.eco.set")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (playerConfig.exist(offlinePlayer)) {
                            playerConfig.setEconomy(offlinePlayer, Double.parseDouble(args[2]));
                            message.send(sender, "You set " + economyProvider.currencyNameSingular() + economyProvider.format(Double.parseDouble(args[2])) + " to " + offlinePlayer.getName() + " account");
                        } else {
                            message.send(sender, offlinePlayer.getName() + " has never joined");
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
            if (sender.hasPermission("smpcore.command.eco.add")) {
                commands.add("add");
            }
            if (sender.hasPermission("smpcore.command.eco.remove")) {
                commands.add("remove");
            }
            if (sender.hasPermission("smpcore.command.eco.reset")) {
                commands.add("reset");
            }
            if (sender.hasPermission("smpcore.command.eco.set")) {
                commands.add("set");
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("smpcore.command.eco.add")) {
                for (OfflinePlayer players : sender.getServer().getOfflinePlayers()) {
                    commands.add(players.getName());
                }
            }
            if (sender.hasPermission("smpcore.command.eco.remove")) {
                for (OfflinePlayer players : sender.getServer().getOfflinePlayers()) {
                    commands.add(players.getName());
                }
            }
            if (sender.hasPermission("smpcore.command.eco.reset")) {
                for (OfflinePlayer players : sender.getServer().getOfflinePlayers()) {
                    commands.add(players.getName());
                }
            }
            if (sender.hasPermission("smpcore.command.eco.set")) {
                for (OfflinePlayer players : sender.getServer().getOfflinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}