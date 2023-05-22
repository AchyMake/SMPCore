package net.achymake.smpcore.listeners.sign;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChange implements Listener {
    private final Message message = SMPCore.getMessage();
    public SignChange() {
        SMPCore.getInstance().getServer().getPluginManager().registerEvents(this, SMPCore.getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
        for (int i = 0; i < event.getLines().length; i++) {
            if (!event.getLine(i).contains("&"))return;
            if (!event.getPlayer().hasPermission("smpcore.chatcolor.sign"))return;
            event.setLine(i, message.color(event.getLine(i)));
        }
    }
}