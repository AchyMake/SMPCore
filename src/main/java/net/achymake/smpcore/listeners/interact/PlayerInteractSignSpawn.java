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
    private final SpawnConfig spawnConfig = SMPCore.getSpawnConfig();
    private final Message message = SMPCore.getMessage();
    public PlayerInteractSignSpawn(SMPCore smpCore) {
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractSignSpawn(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!Tag.SIGNS.isTagged(event.getClickedBlock().getType()))return;
        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!sign.getLine(0).equalsIgnoreCase("[spawn]"))return;
        if (!event.getPlayer().hasPermission("smpcore.command.spawn.signs"))return;
        if (!spawnConfig.spawnExist())return;
        spawnConfig.getSpawn().getChunk().load();
        message.send(event.getPlayer(), "&6Teleporting to&f Spawn");
        event.getPlayer().teleport(spawnConfig.getSpawn());
    }
}