package net.achymake.smpcore.commands;

import net.achymake.smpcore.files.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AnnouncementCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String words : args) {
                stringBuilder.append(words);
                stringBuilder.append(" ");
            }
            Message.sendAnnouncement(stringBuilder.toString());
            for (Player players : sender.getServer().getOnlinePlayers()) {
                Message.send(players, "&6Server:&f "+ stringBuilder);
            }
        }
        return true;
    }
    @Override
    public List onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }
}