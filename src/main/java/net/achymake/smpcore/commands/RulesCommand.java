package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.MotdConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class RulesCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final MotdConfig motdConfig = smpCore.getMotdConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (motdConfig.motdExist("rules")) {
                motdConfig.sendMotd(sender, "rules");
            }
        }
        return true;
    }
    @Override
    public List onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }
}