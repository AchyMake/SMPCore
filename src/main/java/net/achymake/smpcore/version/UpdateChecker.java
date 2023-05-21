package net.achymake.smpcore.version;

import net.achymake.smpcore.SMPCore;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final SMPCore smpCore;
    private final int resourceId;
    public UpdateChecker(SMPCore smpCore, int resourceId) {
        this.smpCore = smpCore;
        this.resourceId = resourceId;
    }
    public void getVersion(Consumer<String> consumer) {
        smpCore.getServer().getScheduler().runTaskAsynchronously(smpCore, () -> {
            try {
                InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId)).openStream();
                Scanner scanner = new Scanner(inputStream);
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                    scanner.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                smpCore.getMessage().sendLog(e.getMessage());
            }
        });
    }
    public void getUpdate() {
        if (smpCore.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(smpCore, resourceId)).getVersion((latest) -> {
                if (smpCore.getDescription().getVersion().equals(latest)) {
                    smpCore.getMessage().sendLog("You are using the latest version");
                } else {
                    smpCore.getMessage().sendLog("New Update: " + latest);
                    smpCore.getMessage().sendLog("Current Version: " + smpCore.getDescription().getVersion());
                }
            });
        }
    }
    public void sendMessage(Player player) {
        if (smpCore.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(smpCore, resourceId)).getVersion((latest) -> {
                if (!smpCore.getDescription().getVersion().equalsIgnoreCase(latest)) {
                    smpCore.getMessage().send(player,"&6" + smpCore.getName() + " Update:&f "+ latest);
                    smpCore.getMessage().send(player,"&6Current Version: &f" + smpCore.getDescription().getVersion());
                }
            });
        }
    }
}