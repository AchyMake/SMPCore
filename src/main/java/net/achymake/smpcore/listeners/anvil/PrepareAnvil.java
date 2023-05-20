package net.achymake.smpcore.listeners.anvil;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrepareAnvil implements Listener {
    public PrepareAnvil() {
        SMPCore smpCore = SMPCore.getInstance();
        smpCore.getServer().getPluginManager().registerEvents(this, smpCore);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        if (!event.getView().getPlayer().hasPermission("players.chatcolor.anvil"))return;
        ItemStack itemStack = event.getResult();
        if (itemStack == null)return;
        if (!itemStack.hasItemMeta())return;
        ItemMeta resultMeta = itemStack.getItemMeta();
        resultMeta.setDisplayName(Message.color(event.getInventory().getRenameText()));
        itemStack.setItemMeta(resultMeta);
    }
}