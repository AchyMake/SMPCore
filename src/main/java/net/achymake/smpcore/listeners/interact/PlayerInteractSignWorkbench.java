package net.achymake.smpcore.listeners.interact;

import net.achymake.smpcore.SMPCore;
import org.bukkit.Tag;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractSignWorkbench implements Listener {
    public PlayerInteractSignWorkbench(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignWorkbench(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!Tag.SIGNS.isTagged(event.getClickedBlock().getType()))return;
        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!sign.getLine(0).equalsIgnoreCase("[workbench]"))return;
        if (!event.getPlayer().hasPermission("smpcore.command.workbench.signs"))return;
        event.getPlayer().openWorkbench(event.getPlayer().getLocation(), true);
    }
}