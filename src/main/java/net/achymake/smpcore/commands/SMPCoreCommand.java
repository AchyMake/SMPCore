package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SMPCoreCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final Message message = smpCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                message.send(player, "&6"+smpCore.getName()+" "+smpCore.getDescription().getVersion());
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("smpcore.command.reload")) {
                        smpCore.reload();
                        message.send(sender,"&6SMPCore reloaded");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    smpCore.reload();
                    message.send(sender,"SMPCore reloaded");
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
                if (player.hasPermission("smpcore.command.reload")) {
                    commands.add("reload");
                }
            }
        }
        return commands;
    }
}