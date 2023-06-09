package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TPAcceptCommand implements CommandExecutor, TabCompleter {
    private final PlayerData playerData = SMPCore.getPlayerData();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (playerData.hasString(player, "tpa.from")) {
                    Player target = player.getServer().getPlayer(UUID.fromString(playerData.getString(player,"tpa.from")));
                    if (target != null) {
                        if (player.getServer().getScheduler().isQueued(playerData.getInt(target, "task.tpa"))) {
                            player.getServer().getScheduler().cancelTask(playerData.getInt(target, "task.tpa"));
                            target.teleport(player);
                            message.send(target, "&6Teleporting to&f " + player.getName());
                            message.send(player, "&6You accepted&f " + target.getName() + "&6 tpa request");
                            playerData.removeData(target, "tpa.sent");
                            playerData.removeData(player, "tpa.from");
                            playerData.removeData(target, "task.tpa");
                        }
                    }
                } else {
                    message.send(player, "&cYou haven't any tpa request");
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