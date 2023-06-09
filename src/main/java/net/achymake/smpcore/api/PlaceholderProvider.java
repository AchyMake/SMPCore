package net.achymake.smpcore.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.achymake.smpcore.SMPCore;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PlaceholderProvider extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "smpcore";
    }
    @Override
    public String getAuthor() {
        return "AchyMake";
    }
    @Override
    public String getVersion() {
        return SMPCore.getInstance().getDescription().getVersion();
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public boolean register() {
        return super.register();
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }
        if (params.equals("name")) {
            if (SMPCore.getPlayerConfig().get(player).getKeys(false).contains("display-name")) {
                return SMPCore.getMessage().color(SMPCore.getPlayerConfig().get(player).getString("display-name"));
            } else {
                return SMPCore.getMessage().color(SMPCore.getPlayerConfig().get(player).getString("name"));
            }
        }
        if (params.equals("vanished")) {
            return String.valueOf(SMPCore.getPlayerConfig().get(player).getBoolean("is-Vanished"));
        }
        if (params.equals("online_players")) {
            return String.valueOf(player.getServer().getOnlinePlayers().size() - SMPCore.getPlayerConfig().getVanished().size());
        }
        if (params.equals("account")) {
            return SMPCore.getEconomyProvider().format(SMPCore.getPlayerConfig().getEconomy(player));
        }
        return super.onPlaceholderRequest(player, params);
    }
}