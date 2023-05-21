package net.achymake.smpcore.listeners.interact;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import net.achymake.smpcore.files.SpawnConfig;
import org.bukkit.Tag;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractSignSpawn implements Listener {
    private final SMPCore smpCore = SMPCore.getInstance();
    private final SpawnConfig spawnConfig = smpCore.getSpawnConfig();
    private final Message message = smpCore.getMessage();
    public PlayerInteractSignSpawn() {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignSpawn(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!Tag.SIGNS.isTagged(event.getClickedBlock().getType()))return;
        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!sign.getLine(0).equalsIgnoreCase("[spawn]"))return;
        if (!event.getPlayer().hasPermission("smpcore.command.spawn.signs"))return;
        if (!spawnConfig.spawnExist())return;
        if (!event.getPlayer().hasPermission("smpcore.command.spawn"))return;
        spawnConfig.getSpawn().getChunk().load();
        event.getPlayer().teleport(spawnConfig.getSpawn());
        message.send(event.getPlayer(), "&6Teleporting to&f spawn");
    }
}