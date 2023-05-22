package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.api.EconomyProvider;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final EconomyProvider economyProvider = smpCore.getEconomyProvider();
    private final FileConfiguration config = smpCore.getConfig();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (playerConfig.isFrozen(player) || playerConfig.isJailed(player)) {
                return false;
            } else {
                if (args.length == 0) {
                    if (playerConfig.locationExist(player, "homes.home")) {
                        playerConfig.getLocation(player, "homes.home").getChunk().load();
                        player.teleport(playerConfig.getLocation(player, "homes.home"));
                        message.send(player, "&6Teleporting to&f home");
                    } else {
                        message.send(player, "home&c does not exist");
                    }
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("bed")) {
                        if (player.hasPermission("smpcore.command.home.bed")) {
                            if (player.getBedSpawnLocation() != null){
                                Location location = player.getBedSpawnLocation();
                                location.setPitch(player.getLocation().getPitch());
                                location.setYaw(player.getLocation().getYaw());
                                player.getBedSpawnLocation().getChunk().load();
                                message.send(player, "&6Teleporting to&f " + args[0]);
                                player.teleport(location);
                            } else {
                                message.send(player, args[0] + "&c does not exist");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("buy")) {
                        if (player.hasPermission("smpcore.command.home.buy")) {
                            message.send(player, "&6Homes costs&a " + economyProvider.format(config.getDouble("homes.cost")));
                        }
                    } else {
                        if (playerConfig.locationExist(player, "homes." + args[0])) {
                            playerConfig.getLocation(player, "homes." + args[0]).getChunk().load();
                            player.teleport(playerConfig.getLocation(player, "homes." + args[0]));
                            message.send(player, "&6Teleporting to&f " + args[0]);
                        } else {
                            message.send(player, args[0] + "&c does not exist");
                        }
                    }
                }
                if (args.length == 2) {
                    int amount = Integer.parseInt(args[1]);
                    if (args[0].equalsIgnoreCase("buy")) {
                        if (player.hasPermission("smpcore.command.home.buy")) {
                            if (playerConfig.getEconomy(player) >= config.getDouble("homes.cost") * amount) {
                                playerConfig.setInt(player, "max-homes", amount);
                                playerConfig.removeEconomy(player, config.getDouble("homes.cost") * amount);
                                message.send(player, "&6You bought " + amount + " homes for&a " + economyProvider.format(config.getDouble("homes.cost") * amount));
                            } else {
                                message.send(player, "&cYou don't have&a " + economyProvider.format(config.getDouble("homes.cost") * amount) + "&c to buy&f " + amount + "&c homes");
                            }
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
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.commands.home.buy")) {
                    commands.add("buy");
                }
                if (player.hasPermission("smpcore.commands.home.bed")) {
                    commands.add("bed");
                }
                for (String homes : playerConfig.getHomes(player)) {
                    commands.add(homes);
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.commands.home.buy")) {
                    if (args[0].equalsIgnoreCase("buy")) {
                        commands.add("1");
                        commands.add("2");
                        commands.add("3");
                    }
                }
            }
        }
        return commands;
    }
}