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

public class FeedCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (playerConfig.getCommandCooldown().containsKey("feed-" + player.getUniqueId())) {
                    Long timeElapsed = System.currentTimeMillis() - playerConfig.getCommandCooldown().get("feed-" + player.getUniqueId());
                    String cooldownTimer = smpCore.getConfig().getString("commands.cooldown.feed");
                    Integer integer = Integer.valueOf(cooldownTimer.replace(cooldownTimer, cooldownTimer + "000"));
                    if (timeElapsed > integer) {
                        playerConfig.getCommandCooldown().put("feed-" + player.getUniqueId(), System.currentTimeMillis());
                        player.setFoodLevel(20);
                        Message.send(sender, "&6Your starvation has been satisfied");
                    } else {
                        long timer = (integer-timeElapsed);
                        Message.send(sender, "&cYou have to wait&f "+String.valueOf(timer).substring(0,String.valueOf(timer).length()-3)+"&c seconds");
                    }
                } else {
                    playerConfig.getCommandCooldown().put("feed-" + player.getUniqueId(),System.currentTimeMillis());
                    player.setFoodLevel(20);
                    Message.send(sender, "&6Your starvation has been satisfied");
                }
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.feed.others")) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null){
                    target.setFoodLevel(20);
                    Message.send(sender, "&6You satisfied&f "+target.getName()+"&6 starvation");
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1){
            if (sender.hasPermission("smpcore.command.feed.others")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}