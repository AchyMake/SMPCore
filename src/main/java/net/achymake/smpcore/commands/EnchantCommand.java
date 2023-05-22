package net.achymake.smpcore.commands;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantCommand implements CommandExecutor, TabCompleter {
    private final Message message = SMPCore.getMessage();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                message.send(sender, "&cUsage:&f /enchant enchantmentName amount");
            }
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.getInventory().getItemInMainHand().getType().isAir()) {
                    message.send(sender, "&cYou have to hold an item");
                } else {
                    ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
                    Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
                    if (itemMeta.hasEnchant(enchantment)) {
                        player.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                        message.send(sender, "&6You removed&f " + enchantment.getName());
                    } else {
                        player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, 1);
                        message.send(sender, "&6You added&f " + enchantment.getName() + "&6 with lvl&f 1");
                    }
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (player.getInventory().getItemInMainHand().getType().isAir()) {
                    message.send(sender, "&cYou have to hold an item");
                } else {
                    Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
                    if (Integer.valueOf(args[1]) > 0) {
                        player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, Integer.valueOf(args[1]));
                        message.send(sender, "&6You added&f " + enchantment.getName() + "&6 with lvl&f " + args[1]);
                    } else {
                        player.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                        message.send(sender, "&6You removed&f " + enchantment.getName());
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                for (Enchantment enchantment : Enchantment.values()) {
                    commands.add(enchantment.getName().toLowerCase());
                }
            }
            if (args.length == 2) {
                commands.add(String.valueOf(Enchantment.getByName(args[0].toUpperCase()).getMaxLevel()));
            }
        }
        return commands;
    }
}