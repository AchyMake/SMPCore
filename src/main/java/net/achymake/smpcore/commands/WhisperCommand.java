package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
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

public class WhisperCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final PlayerData playerData = SMPCore.getPlayerData();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (playerConfig.isMuted((Player) sender) || playerConfig.isJailed((Player) sender)) {
                return false;
            } else {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    message.send(player, "&cUsage:&f /whisper target message");
                }
                if (args.length > 1) {
                    Player player = (Player) sender;
                    Player target = player.getServer().getPlayerExact(args[0]);
                    if (target != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int i = 1; i < args.length; i++) {
                            stringBuilder.append(args[i]);
                            stringBuilder.append(" ");
                        }
                        message.send(player, "&7You > " + target.getName() + ": " + stringBuilder.toString().strip());
                        message.send(target, "&7" + player.getName() + " > You: " + stringBuilder.toString().strip());
                        playerData.setString(player, "last-whisper", target.getUniqueId().toString());
                        player.getServer().broadcast(message.color("&7" + player.getName() + " > " + target.getName() + ": " + stringBuilder.toString().strip()),"smpcore.notify.whisper");
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
                for (Player players : player.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            }
        }
        return commands;
    }
}