package net.achymake.smpcore.listeners;

import net.achymake.smpcore.listeners.anvil.PrepareAnvil;
import net.achymake.smpcore.listeners.block.*;
import net.achymake.smpcore.listeners.bucket.*;
import net.achymake.smpcore.listeners.chat.AsyncPlayerChat;
import net.achymake.smpcore.listeners.chat.PlayerCommandPreprocess;
import net.achymake.smpcore.listeners.connection.*;
import net.achymake.smpcore.listeners.damage.*;
import net.achymake.smpcore.listeners.death.PlayerDeath;
import net.achymake.smpcore.listeners.interact.*;
import net.achymake.smpcore.listeners.leash.PlayerLeashEntity;
import net.achymake.smpcore.listeners.mount.PlayerMount;
import net.achymake.smpcore.listeners.move.PlayerMoveFrozen;
import net.achymake.smpcore.listeners.move.PlayerMoveVanished;
import net.achymake.smpcore.listeners.respawn.PlayerRespawn;
import net.achymake.smpcore.listeners.shear.PlayerShearEntity;
import net.achymake.smpcore.listeners.sign.SignChange;
import net.achymake.smpcore.listeners.spawn.PlayerSpawnLocation;
import net.achymake.smpcore.listeners.teleport.PlayerTeleport;

public class Events {
    public static void setup() {
        new PrepareAnvil();
        new BlockBreak();
        new BlockBreakNotify();
        new BlockFertilize();
        new BlockPlace();
        new BlockPlaceNotify();
        new PlayerHarvestBlock();

        new PlayerBucketEmpty();
        new PlayerBucketEmptyNotify();
        new PlayerBucketEntity();
        new PlayerBucketFill();

        new AsyncPlayerChat();
        new PlayerCommandPreprocess();

        new JoinMessage();
        new PlayerLogin();
        new QuitMessage();

        new DamageEntityJailed();
        new DamageEntityWithArrowJailed();
        new DamageEntityWithSnowballJailed();
        new DamageEntityWithSpectralArrowJailed();
        new DamageEntityWithThrownPotionJailed();
        new DamageEntityWithTridentJailed();
        new DamagePlayer();
        new DamagePlayerWithArrow();
        new DamagePlayerWithSnowball();
        new DamagePlayerWithSpectralArrow();
        new DamagePlayerWithThrownPotion();
        new DamagePlayerWithTrident();

        new PlayerDeath();

        new PlayerInteractPhysical();
        new PlayerLeashEntity();

        new PlayerMount();

        new PlayerMoveFrozen();
        new PlayerMoveVanished();

        new PlayerRespawn();

        new PlayerShearEntity();

        new PlayerInteractSignSpawn();
        new PlayerInteractSignWarp();
        new PlayerInteractSignWorkbench();
        new SignChange();

        new PlayerSpawnLocation();

        new PlayerTeleport();
    }
}