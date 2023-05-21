package net.achymake.smpcore.listeners.interact;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.WarpConfig;
import org.bukkit.Tag;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractSignWarp implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final WarpConfig warpConfig = smpCore.getWarpConfig();
    private final Message message = smpCore.getMessage();
    public PlayerInteractSignWarp() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignWarp(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!Tag.SIGNS.isTagged(event.getClickedBlock().getType()))return;
        Sign sign = (Sign) event.getClickedBlock().getState();
        for (String warpName : warpConfig.getWarps()) {
            if (!sign.getLine(0).equalsIgnoreCase("[warp " + warpName + "]"))return;
            if (!warpConfig.warpExist(warpName))return;
            if (!event.getPlayer().hasPermission("players.command.warp.signs"))return;
            if (!event.getPlayer().hasPermission("players.command.warp." + warpName))return;
            warpConfig.getWarp(warpName).getChunk().load();
            event.getPlayer().teleport(warpConfig.getWarp(warpName));
            message.send(event.getPlayer(), "&6Teleporting to&f "+warpName);
        }
    }
}