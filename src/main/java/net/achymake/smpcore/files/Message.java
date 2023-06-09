package net.achymake.smpcore.files;

import net.achymake.smpcore.SMPCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {
    private final SMPCore smpCore;
    public Message (SMPCore smpCore) {
        this.smpCore = smpCore;
    }
    public void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }
    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(color(message)));
    }
    public void sendLog(String message) {
        smpCore.getServer().getConsoleSender().sendMessage("[" + smpCore.getName() + "] " + message);
    }
    public void sendAnnouncement(String message) {
        smpCore.getServer().getConsoleSender().sendMessage("[Server] " + message);
    }
    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}