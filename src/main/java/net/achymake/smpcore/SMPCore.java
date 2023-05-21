package net.achymake.smpcore;

import net.achymake.smpcore.api.*;
import net.achymake.smpcore.commands.*;
import net.achymake.smpcore.files.*;
import net.achymake.smpcore.listeners.Events;
import net.achymake.smpcore.version.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        setupVault();
        setupPlaceholderAPI();
        jailConfig = new JailConfig(this);
        kitConfig = new KitConfig(this);
        motdConfig = new MotdConfig(this);
        playerConfig = new PlayerConfig(this);
        playerData = new PlayerData(this);
        spawnConfig = new SpawnConfig(this);
        warpConfig = new WarpConfig(this);
        reload();
        setupCommands();
        Events.setup();
        message.sendLog("Enabled " + getName() + " " + getDescription().getVersion());
        new UpdateChecker(this, 108685).getUpdate();
    }
    @Override
    public void onDisable() {
        if (!getVanished().isEmpty()) {
            getVanished().clear();
        }
        if (!getPlayerConfig().getCommandCooldown().isEmpty()) {
            getPlayerConfig().getCommandCooldown().clear();
        }
        message.sendLog("Disabled " + getName() + " " + getDescription().getVersion());
    }
    private void setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            message.sendLog("hooked to Vault");
            getServer().getServicesManager().register(Economy.class, new EconomyProvider(this), this, ServicePriority.Normal);
            economyProvider = new EconomyProvider(this);
        } else {
            message.sendLog("You have to install 'Vault'");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    private void setupPlaceholderAPI() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderProvider().register();
            message.sendLog("hooked to PlaceholderAPI");
        } else {
            message.sendLog("You have to install 'PlaceholderAPI'");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    private void setupCommands() {
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
    }
    public JailConfig getJailConfig() {
        return jailConfig;
    }
    public KitConfig getKitConfig() {
        return kitConfig;
    }
    public Message getMessage() {
        return message;
    }
    public MotdConfig getMotdConfig() {
        return motdConfig;
    }
    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }
    public PlayerData getPlayerData() {
        return playerData;
    }
    public SpawnConfig getSpawnConfig() {
        return spawnConfig;
    }
    public WarpConfig getWarpConfig() {
        return warpConfig;
    }
    public EconomyProvider getEconomyProvider() {
        return economyProvider;
    }
    public List<Player> getVanished() {
        return playerConfig.getVanished();
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
                FileConfiguration config = YamlConfiguration.loadConfiguration(playerFiles);
                try {
                    config.load(playerFiles);
                    config.options().copyDefaults(true);
                    config.save(playerFiles);
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