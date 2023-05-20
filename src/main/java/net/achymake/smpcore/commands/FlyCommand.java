package net.achymake.smpcore.commands;

import net.achymake.smpcore.files.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player){
                Player player = (Player) sender;
                player.setAllowFlight(!player.getAllowFlight());
                if (player.getAllowFlight()){
                    Message.send(sender, "&6Enabled fly");
                } else {
                    Message.send(sender, "&6Disabled fly");
                }
            }
        }
        if (args.length == 1) {
            Player target = sender.getServer().getPlayerExact(args[0]);
            if (target != null) {
                if (sender.hasPermission("smpcore.command.fly.others")) {
                    if (!target.hasPermission("smpcore.command.fly.exempt")) {
                        target.setAllowFlight(!target.getAllowFlight());
                        if (target.getAllowFlight()){
                            Message.send(target, "&6Enabled fly");
                            Message.send(sender, "&6You enabled fly for "+ target.getName());
                        } else {
                            Message.send(target, "&6Disabled fly");
                            Message.send(sender, "&6You disabled fly for "+ target.getName());
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
            if (sender.hasPermission("smpcore.command.fly.others")){
                for (Player players : sender.getServer().getOnlinePlayers()){
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}