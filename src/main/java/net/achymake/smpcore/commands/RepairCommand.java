package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;

public class RepairCommand implements CommandExecutor, TabCompleter {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final PlayerConfig playerConfig = smpCore.getPlayerConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.getInventory().getItemInMainHand().getType().isAir()) {
                    Message.send(player,"&cYou have to hold an item");
                } else {
                    if (playerConfig.getCommandCooldown().containsKey("repair-" + player.getUniqueId())) {
                        Long timeElapsed = System.currentTimeMillis() - playerConfig.getCommandCooldown().get("repair-" + player.getUniqueId());
                        String cooldownTimer = smpCore.getConfig().getString("commands.cooldown.repair");
                        Integer integer = Integer.valueOf(cooldownTimer.replace(cooldownTimer, cooldownTimer + "000"));
                        if (timeElapsed > integer) {
                            playerConfig.getCommandCooldown().put("repair-" + player.getUniqueId(),System.currentTimeMillis());
                            Damageable damageable = (Damageable) player.getInventory().getItemInMainHand().getItemMeta();
                            damageable.setDamage(0);
                            player.getInventory().getItemInMainHand().setItemMeta(damageable);
                            Message.send(sender, "&6You repaired&f "+ player.getInventory().getItemInMainHand().getType());
                        } else {
                            long timer = (integer-timeElapsed);
                            Message.send(sender, "&cYou have to wait&f "+String.valueOf(timer).substring(0,String.valueOf(timer).length()-3)+"&c seconds");
                        }
                    } else {
                        playerConfig.getCommandCooldown().put("repair-" + player.getUniqueId(),System.currentTimeMillis());
                        Damageable damageable = (Damageable) player.getInventory().getItemInMainHand().getItemMeta();
                        damageable.setDamage(0);
                        player.getInventory().getItemInMainHand().setItemMeta(damageable);
                        Message.send(sender, "&6You repaired&f "+ player.getInventory().getItemInMainHand().getType());
                    }
                }
            }
            if (args.length == 1) {
                if (sender.hasPermission("smpcore.command.repair.force")) {
                    if (args[0].equalsIgnoreCase("force")) {
                        if (player.getInventory().getItemInMainHand().getType().isAir()) {
                            Message.send(player,"&cYou have to hold an item");
                        }else{
                            Damageable damageable = (Damageable) player.getInventory().getItemInMainHand().getItemMeta();
                            damageable.setDamage(0);
                            player.getInventory().getItemInMainHand().setItemMeta(damageable);
                            Message.send(sender, "&6You repaired&f "+ player.getInventory().getItemInMainHand().getType());
                        }
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.command.repair.force")){
                commands.add("force");
            }
        }
        return commands;
    }
}