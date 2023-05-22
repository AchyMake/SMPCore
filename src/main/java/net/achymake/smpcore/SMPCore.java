package net.achymake.smpcore;

import net.achymake.smpcore.api.*;
import net.achymake.smpcore.commands.*;
import net.achymake.smpcore.files.*;
import net.achymake.smpcore.listeners.anvil.PrepareAnvil;
import net.achymake.smpcore.listeners.block.*;
import net.achymake.smpcore.listeners.bucket.PlayerBucketEmpty;
import net.achymake.smpcore.listeners.bucket.PlayerBucketEmptyNotify;
import net.achymake.smpcore.listeners.bucket.PlayerBucketEntity;
import net.achymake.smpcore.listeners.bucket.PlayerBucketFill;
import net.achymake.smpcore.listeners.chat.AsyncPlayerChat;
import net.achymake.smpcore.listeners.chat.PlayerCommandPreprocess;
import net.achymake.smpcore.listeners.connection.JoinMessage;
import net.achymake.smpcore.listeners.connection.PlayerLogin;
import net.achymake.smpcore.listeners.connection.QuitMessage;
import net.achymake.smpcore.listeners.damage.*;
import net.achymake.smpcore.listeners.death.PlayerDeath;
import net.achymake.smpcore.listeners.interact.PlayerInteractPhysical;
import net.achymake.smpcore.listeners.interact.PlayerInteractSignSpawn;
import net.achymake.smpcore.listeners.interact.PlayerInteractSignWarp;
import net.achymake.smpcore.listeners.interact.PlayerInteractSignWorkbench;
import net.achymake.smpcore.listeners.leash.PlayerLeashEntity;
import net.achymake.smpcore.listeners.mount.PlayerMount;
import net.achymake.smpcore.listeners.move.PlayerMoveFrozen;
import net.achymake.smpcore.listeners.move.PlayerMoveVanished;
import net.achymake.smpcore.listeners.respawn.PlayerRespawn;
import net.achymake.smpcore.listeners.shear.PlayerShearEntity;
import net.achymake.smpcore.listeners.sign.SignChange;
import net.achymake.smpcore.listeners.spawn.PlayerSpawnLocation;
import net.achymake.smpcore.listeners.teleport.PlayerTeleport;
import net.achymake.smpcore.version.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class SMPCore extends JavaPlugin {
    private static SMPCore instance;
    private static JailConfig jailConfig;
    private static KitConfig kitConfig;
    private static Message message;
    private static MotdConfig motdConfig;
    private static PlayerConfig playerConfig;
    private static PlayerData playerData;
    private static SpawnConfig spawnConfig;
    private static WarpConfig warpConfig;
    private static EconomyProvider economyProvider;
    private final File configFile = new File(getDataFolder(), "config.yml");
    @Override
    public void onEnable() {
        instance = this;
        message = new Message(this);
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            message.sendLog("hooked to Vault");
            getServer().getServicesManager().register(Economy.class, new EconomyProvider(this), this, ServicePriority.Normal);
            economyProvider = new EconomyProvider(this);
        } else {
            message.sendLog("You have to install 'Vault'");
            getServer().getPluginManager().disablePlugin(this);
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderProvider().register();
            message.sendLog("hooked to PlaceholderAPI");
        } else {
            message.sendLog("You have to install 'PlaceholderAPI'");
            getServer().getPluginManager().disablePlugin(this);
        }
        jailConfig = new JailConfig(this);
        kitConfig = new KitConfig(this);
        motdConfig = new MotdConfig(this);
        playerConfig = new PlayerConfig(this);
        playerData = new PlayerData(this);
        spawnConfig = new SpawnConfig(this);
        warpConfig = new WarpConfig(this);
        getCommand("announcement").setExecutor(new AnnouncementCommand());
        getCommand("back").setExecutor(new BackCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("color").setExecutor(new ColorCommand());
        getCommand("delhome").setExecutor(new DelhomeCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        getCommand("eco").setExecutor(new EcoCommand());
        getCommand("enchant").setExecutor(new EnchantCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("gma").setExecutor(new GMACommand());
        getCommand("gmc").setExecutor(new GMCCommand());
        getCommand("gms").setExecutor(new GMSCommand());
        getCommand("gmsp").setExecutor(new GMSPCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("inventory").setExecutor(new InventoryCommand());
        getCommand("jail").setExecutor(new JailCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("motd").setExecutor(new MotdCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("pvp").setExecutor(new PVPCommand());
        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("respond").setExecutor(new RespondCommand());
        getCommand("rules").setExecutor(new RulesCommand());
        getCommand("sethome").setExecutor(new SethomeCommand());
        getCommand("setjail").setExecutor(new SetJailCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("skull").setExecutor(new SkullCommand());
        getCommand("smpcore").setExecutor(new SMPCoreCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("tpaccept").setExecutor(new TPAcceptCommand());
        getCommand("tpa").setExecutor(new TPACommand());
        getCommand("tpcancel").setExecutor(new TPCancelCommand());
        getCommand("tp").setExecutor(new TPCommand());
        getCommand("tpdeny").setExecutor(new TPDenyCommand());
        getCommand("tphere").setExecutor(new TPHereCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("whisper").setExecutor(new WhisperCommand());
        getCommand("workbench").setExecutor(new WorkbenchCommand());
        reload();
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
        message.sendLog("Enabled " + getName() + " " + getDescription().getVersion());
        new UpdateChecker(this, 108685).getUpdate();
    }
    @Override
    public void onDisable() {
        if (!playerConfig.getVanished().isEmpty()) {
            playerConfig.getVanished().clear();
        }
        if (!playerConfig.getCommandCooldown().isEmpty()) {
            playerConfig.getCommandCooldown().clear();
        }
        message.sendLog("Disabled " + getName() + " " + getDescription().getVersion());
    }
    public static JailConfig getJailConfig() {
        return jailConfig;
    }
    public static KitConfig getKitConfig() {
        return kitConfig;
    }
    public static Message getMessage() {
        return message;
    }
    public static MotdConfig getMotdConfig() {
        return motdConfig;
    }
    public static PlayerConfig getPlayerConfig() {
        return playerConfig;
    }
    public static PlayerData getPlayerData() {
        return playerData;
    }
    public static SpawnConfig getSpawnConfig() {
        return spawnConfig;
    }
    public static WarpConfig getWarpConfig() {
        return warpConfig;
    }
    public static EconomyProvider getEconomyProvider() {
        return economyProvider;
    }
    public void reload() {
        if (jailConfig.exist()) {
            jailConfig.reload();
        } else {
            jailConfig.setup();
        }
        if (kitConfig.exist()) {
            kitConfig.reload();
        } else {
            kitConfig.setup();
        }
        if (motdConfig.exist()) {
            motdConfig.reload();
        } else {
            motdConfig.setup();
        }
        if (spawnConfig.exist()) {
            spawnConfig.reload();
        } else {
            spawnConfig.setup();
        }
        if (warpConfig.exist()) {
            warpConfig.reload();
        } else {
            warpConfig.setup();
        }
        if (configFile.exists()) {
            try {
                getConfig().load(configFile);
                getConfig().options().copyDefaults(true);
                saveConfig();
            } catch (IOException | InvalidConfigurationException e) {
                message.sendLog(e.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        for (OfflinePlayer offlinePlayer : getServer().getOfflinePlayers()) {
            File playerFiles = new File(getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
            if (playerFiles.exists()) {
                FileConfiguration playerConfigs = YamlConfiguration.loadConfiguration(playerFiles);
                try {
                    playerConfigs.load(playerFiles);
                    playerConfigs.options().copyDefaults(true);
                    playerConfigs.save(playerFiles);
                } catch (IOException | InvalidConfigurationException e) {
                    message.sendLog(e.getMessage());
                }
            }
        }
    }
    public static SMPCore getInstance() {
        return instance;
    }
}