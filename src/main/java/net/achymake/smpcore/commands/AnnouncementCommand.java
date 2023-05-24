package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AnnouncementCommand implements CommandExecutor, TabCompleter {
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            message.send(sender, "&cUsage:&f /announcement message");
        } else {
            message.sendAnnouncement(announcement(args));
            for (Player players : sender.getServer().getOnlinePlayers()) {
                message.send(players, "&6Server:&f " + announcement(args));
            }
        }
        return true;
    }
    private String announcement(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String words : args) {
            stringBuilder.append(words);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().strip();
    }
    @Override
    public List onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }
}