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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RespondCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final PlayerData playerData = SMPCore.getPlayerData();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&cUsage:&f /r message");
            }
            Player player = (Player) sender;
            if (!playerConfig.isMuted(player)){
                if (args.length > 0) {
                    if (playerData.hasString(player, "last-whisper")) {
                        Player target = player.getServer().getPlayer(UUID.fromString(playerData.getString(player, "last-whisper")));
                        if (target != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (String words : args) {
                                stringBuilder.append(words);
                                stringBuilder.append(" ");
                            }
                            message.send(player, "&7You > " + target.getName() + ": " + stringBuilder.toString().strip());
                            message.send(target, "&7" + player.getName() + " > You: " + stringBuilder.toString().strip());
                            playerData.setString(target, "last-whisper", player.getUniqueId().toString());
                            player.getServer().broadcast(message.color("&7" + player.getName() + " > " + target.getName() + ": " + stringBuilder.toString().strip()), "players.notify.whisper");
                        }
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }
}