package net.achymake.smpcore.version;

import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
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
    private final Message message = SMPCore.getMessage();
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
                message.sendLog(e.getMessage());
            }
        });
    }
    public void getUpdate() {
        if (smpCore.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(smpCore, resourceId)).getVersion((latest) -> {
                if (smpCore.getDescription().getVersion().equals(latest)) {
                    message.sendLog("You are using the latest version");
                } else {
                    message.sendLog("New Update: " + latest);
                    message.sendLog("Current Version: " + smpCore.getDescription().getVersion());
                }
            });
        }
    }
    public void sendMessage(Player player) {
        if (smpCore.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(smpCore, resourceId)).getVersion((latest) -> {
                if (!smpCore.getDescription().getVersion().equals(latest)) {
                    message.send(player,"&6" + smpCore.getName() + " Update:&f " + latest);
                    message.send(player,"&6Current Version: &f" + smpCore.getDescription().getVersion());
                }
            });
        }
    }
}