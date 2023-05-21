package net.achymake.smpcore.api;

import net.achymake.smpcore.SMPCore;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class EconomyProvider implements Economy {
    private final SMPCore smpCore;
    public EconomyProvider(SMPCore plugin) {
        this.smpCore = plugin;
    }
    public boolean isEnabled() {
        return this.smpCore.isEnabled();
    }
    public String getName() {
        return this.smpCore.getName();
    }
    public boolean hasBankSupport() {
        return false;
    }
    public int fractionalDigits() {
        return -1;
    }
    public String format(double amount) {
        return new DecimalFormat(smpCore.getConfig().getString("economy.format")).format(amount);
    }
    public String currencyNamePlural() {
        return this.currencyNameSingular();
    }
    public String currencyNameSingular() {
        return smpCore.getConfig().getString("economy.currency");
    }
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        if (smpCore.getPlayerConfig().exist(offlinePlayer)){
            return true;
        }else{
            smpCore.getPlayerConfig().setup(offlinePlayer);
        }
        return true;
    }
    public boolean hasAccount(String playerName) {
        if (smpCore.getPlayerConfig().exist(smpCore.getServer().getOfflinePlayer(playerName))){
            return true;
        }else{
            smpCore.getPlayerConfig().setup(smpCore.getServer().getOfflinePlayer(playerName));
        }
        return true;
    }
    public boolean hasAccount(String playerName, String worldName) {
        return this.hasAccount(playerName);
    }
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return this.hasAccount(player);
    }
    public double getBalance(OfflinePlayer offlinePlayer) {
        return smpCore.getPlayerConfig().get(offlinePlayer).getDouble("account");
    }
    public double getBalance(String playerName) {
        return smpCore.getPlayerConfig().get(smpCore.getServer().getOfflinePlayer(playerName)).getDouble("account");
    }
    public double getBalance(String playerName, String world) {
        return this.getBalance(playerName);
    }
    public double getBalance(OfflinePlayer player, String world) {
        return this.getBalance(player);
    }
    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        return smpCore.getPlayerConfig().get(offlinePlayer).getDouble("account") >= amount;
    }
    public boolean has(String playerName, double amount) {
        return smpCore.getPlayerConfig().get(smpCore.getServer().getOfflinePlayer(playerName)).getDouble("account") >= amount;
    }
    public boolean has(String playerName, String worldName, double amount) {
        return this.has(playerName, amount);
    }

    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return this.has(player, amount);
    }
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (offlinePlayer == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player cannot be null!");
        } else if (amount < 0.0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        } else {
            smpCore.getPlayerConfig().removeEconomy(offlinePlayer,amount);
            return new EconomyResponse(amount, this.getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
        }
    }
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (playerName == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        } else if (amount < 0.0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        } else {
            smpCore.getPlayerConfig().removeEconomy(smpCore.getServer().getOfflinePlayer(playerName),amount);
            return new EconomyResponse(amount, this.getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
        }
    }

    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return this.withdrawPlayer(playerName, amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return this.withdrawPlayer(player, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (offlinePlayer == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player can not be null.");
        } else if (amount < 0.0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        } else {
            smpCore.getPlayerConfig().addEconomy(offlinePlayer,amount);
            return new EconomyResponse(amount, this.getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
        }
    }

    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (playerName == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
        } else if (amount < 0.0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        } else {
            smpCore.getPlayerConfig().addEconomy(smpCore.getServer().getOfflinePlayer(playerName),amount);
            return new EconomyResponse(amount, this.getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
        }
    }

    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return this.depositPlayer(playerName, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return this.depositPlayer(player, amount);
    }
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        smpCore.getPlayerConfig().setup(offlinePlayer);
        return true;
    }
    public boolean createPlayerAccount(String playerName) {
        smpCore.getPlayerConfig().setup(smpCore.getServer().getOfflinePlayer(playerName));
        return true;
    }
    public boolean createPlayerAccount(String playerName, String worldName) {
        return this.createPlayerAccount(playerName);
    }
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return this.createPlayerAccount(player);
    }
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
    }
    public List<String> getBanks() {
        return Collections.emptyList();
    }
}