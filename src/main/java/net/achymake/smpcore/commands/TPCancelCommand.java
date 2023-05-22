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

public class TPCancelCommand implements CommandExecutor, TabCompleter {
    private final PlayerData playerData = SMPCore.getPlayerData();
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (playerData.hasString(player, "tpa.sent")) {
                    Player target = player.getServer().getPlayer(UUID.fromString(playerData.getString(player, "tpa.sent")));
                    if (target != null) {
                        if (player.getServer().getScheduler().isQueued(playerData.getInt(player, "task.tpa"))) {
                            player.getServer().getScheduler().cancelTask(playerData.getInt(player, "task.tpa"));
                            message.send(target, player.getName() + "&6 cancelled tpa request");
                            message.send(player, "&6You cancelled tpa request");
                            playerData.removeData(target, "tpa.from");
                            playerData.removeData(player, "tpa.sent");
                            playerData.removeData(player, "task.tpa");
                        }
                    }
                } else {
                    message.send(player, "&cYou haven't sent any tpa request");
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