package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.MotdConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RulesCommand implements CommandExecutor, TabCompleter {
    private final MotdConfig motdConfig = SMPCore.getMotdConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                if (motdConfig.motdExist("rules")) {
                    motdConfig.sendMotd(sender, "rules");
                }
            }
            if (args.length == 1) {
                if (sender.hasPermission("smpcore.command.rules.others")) {
                    Player target = sender.getServer().getPlayerExact(args[0]);
                    if (target != null) {
                        if (motdConfig.motdExist("rules")) {
                            motdConfig.sendMotd(target, "rules");
                        }
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                if (motdConfig.motdExist("rules")) {
                    motdConfig.sendMotd(sender, "rules");
                }
            }
            if (args.length == 1) {
                Player target = sender.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    if (motdConfig.motdExist("rules")) {
                        motdConfig.sendMotd(target, "rules");
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
                if (player.hasPermission("smpcore.command.rules.others")) {
                    for (Player players : player.getServer().getOnlinePlayers()) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}