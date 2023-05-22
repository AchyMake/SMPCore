package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FeedCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (playerConfig.getCommandCooldown().containsKey("feed-" + player.getUniqueId())) {
                    Long timeElapsed = System.currentTimeMillis() - playerConfig.getCommandCooldown().get("feed-" + player.getUniqueId());
                    String cooldownTimer = smpCore.getConfig().getString("commands.cooldown.feed");
                    Integer integer = Integer.valueOf(cooldownTimer.replace(cooldownTimer, cooldownTimer + "000"));
                    if (timeElapsed > integer) {
                        playerConfig.getCommandCooldown().put("feed-" + player.getUniqueId(), System.currentTimeMillis());
                        player.setFoodLevel(20);
                        message.send(sender, "&6Your starvation has been satisfied");
                    } else {
                        long timer = (integer-timeElapsed);
                        message.send(sender, "&cYou have to wait&f " + String.valueOf(timer).substring(0, String.valueOf(timer).length() - 3) + "&c seconds");
                    }
                } else {
                    playerConfig.getCommandCooldown().put("feed-" + player.getUniqueId(), System.currentTimeMillis());
                    player.setFoodLevel(20);
                    message.send(sender, "&6Your starvation has been satisfied");
                }
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.feed.others")) {
                    Player target = player.getServer().getPlayerExact(args[0]);
                    if (target != null) {
                        target.setFoodLevel(20);
                        message.send(target, "&6Your starvation has been satisfied by&f " + player.getName());
                        message.send(player, "&6You satisfied&f " + target.getName() + "&6's starvation");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    target.setFoodLevel(20);
                    message.send(target, "&6Your starvation has been satisfied");
                    message.send(sender, "You satisfied " + target.getName() + "'s starvation");
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1){
                Player player = (Player) sender;
                if (player.hasPermission("smpcore.command.feed.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}