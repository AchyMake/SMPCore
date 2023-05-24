package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.JailConfig;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpcore.files.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JailCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final PlayerData playerData = SMPCore.getPlayerData();
    private final JailConfig jailConfig = SMPCore.getJailConfig();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            message.send(player, "&cUsage:&f /jail target");
        }
        if (args.length == 1) {
            Player target = sender.getServer().getPlayerExact(args[0]);
            if (target != null) {
                if (jailConfig.jailExist()) {
                    if (target == sender) {
                        execute(sender, target);
                    } else if (!target.hasPermission("smpcore.command.jail.exempt")) {
                        execute(sender, target);
                    }
                }
            }
        }
        return true;
    }
    private void execute(CommandSender sender, Player target) {
        if (playerConfig.isJailed(target)) {
            playerData.getLocation(target, "jail").getChunk().load();
            target.teleport(playerData.getLocation(target, "jail"));
            playerConfig.setBoolean(target, "is-Jailed", false);
            message.send(target, "&cYou got free by&f " + sender.getName());
            message.send(sender, "&6You freed&f " + target.getName());
            playerData.removeLocation(target, "jail");
        } else {
            jailConfig.getJail().getChunk().load();
            playerData.setLocation(target, "jail");
            target.teleport(jailConfig.getJail());
            playerConfig.setBoolean(target, "is-Jailed", true);
            message.send(target, "&cYou got jailed by&f " + sender.getName());
            message.send(sender, "&6You jailed&f " + target.getName());
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                for (Player players : player.getServer().getOnlinePlayers()) {
                    if (!players.hasPermission("smpcore.command.jail.exempt")) {
                        commands.add(players.getName());
                    }
                }
            }
        }
        return commands;
    }
}