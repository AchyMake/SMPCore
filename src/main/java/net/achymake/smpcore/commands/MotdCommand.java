package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.MotdConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MotdCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final MotdConfig motdConfig = smpCore.getMotdConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (motdConfig.motdExist("message-of-the-day")) {
                motdConfig.sendMotd(sender, "message-of-the-day");
            }
        }
        if (args.length == 1) {
            String motd = args[0];
            if (motdConfig.motdExist(motd)) {
                motdConfig.sendMotd(sender, motd);
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            for (String motd : motdConfig.get().getKeys(false)) {
                commands.add(motd);
            }
        }
        return commands;
    }
}