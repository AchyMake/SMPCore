package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetHomeCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (playerConfig.homeExist(player, "home")) {
                    playerConfig.setHome(player, "home");
                    message.send(player, "home&6 has been set");
                } else if (playerConfig.get(player).getInt("max-homes") > playerConfig.get(player).getConfigurationSection("homes").getKeys(false).size()) {
                    playerConfig.setHome(player, "home");
                    message.send(player, "home&6 has been set");
                } else {
                    message.send(player, "&cYou have reach your limit of&f " + playerConfig.get(player).getConfigurationSection("homes").getKeys(false).size() + "&c homes");
                }
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("buy")) {
                    message.send(player, "&cYou can't set home for&f " + args[0]);

                } else if (args[0].equalsIgnoreCase("bed")) {
                    message.send(player, "&cYou can't set home for&f "+args[0]);
                } else {
                    if (playerConfig.homeExist(player, args[0])) {
                        playerConfig.setHome(player, args[0]);
                        message.send(player, args[0] + "&6 has been set");
                    } else if (playerConfig.get(player).getInt("max-homes") > playerConfig.get(player).getConfigurationSection("homes").getKeys(false).size()){
                        playerConfig.setHome(player, args[0]);
                        message.send(player, args[0] + "&6 has been set");
                    } else {
                        message.send(player, "&cYou have reach your limit of&f " + playerConfig.get(player).getConfigurationSection("homes").getKeys(false).size() + "&c homes");
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
                commands.addAll(playerConfig.getHomes(player));
            }
        }
        return commands;
    }
}