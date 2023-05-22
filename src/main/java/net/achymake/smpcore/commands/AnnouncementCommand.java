package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AnnouncementCommand implements CommandExecutor, TabCompleter {
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                message.send(sender, "&cUsage:&f /announcement message");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (String words : args) {
                    stringBuilder.append(words);
                    stringBuilder.append(" ");
                }
                message.sendAnnouncement(stringBuilder.toString());
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    message.send(players, "&6Server:&f "+ stringBuilder);
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                message.send(sender, "Usage: /announcement message");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (String words : args) {
                    stringBuilder.append(words);
                    stringBuilder.append(" ");
                }
                message.sendAnnouncement(stringBuilder.toString());
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    message.send(players, "&6Server:&f "+ stringBuilder);
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