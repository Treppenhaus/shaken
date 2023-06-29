package eu.treppi.playlegends.shaken.listeners;

import eu.treppi.playlegends.shaken.ShakenPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;

public class SetPlayerPermissions implements Listener {
    public static final HashMap<String, PermissionAttachment> attachments = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        setPermissions(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PermissionAttachment att = attachments.get(e.getPlayer().getUniqueId().toString());
        e.getPlayer().removeAttachment(att);
    }

    public static void setPermissions(Player p) {
        PermissionAttachment att = p.addAttachment(ShakenPlugin.getInstance());
        ShakenPlugin.getInstance().connection.getUserPermissions(p.getUniqueId().toString()).forEach(att::setPermission);
        attachments.put(p.getUniqueId().toString(), att);
    }
}
