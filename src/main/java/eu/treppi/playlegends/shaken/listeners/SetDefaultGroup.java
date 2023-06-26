package eu.treppi.playlegends.shaken.listeners;

import eu.treppi.playlegends.shaken.ShakenGroupManager;
import eu.treppi.playlegends.shaken.ShakenPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.logging.Level;

public class SetDefaultGroup implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        e.getPlayer().addAttachment(ShakenPlugin.getInstance()).getPermissions().forEach((per, val) -> {
            ShakenPlugin.getInstance().getLogger().log(Level.INFO, per + " | " + val);
        });

        //todo: put permissions into player attachment from database connection.GetUserPermissions()
        // also rename method


        ShakenGroupManager.checkForDefaultGroup(e.getPlayer());
        PermissionAttachment a = e.getPlayer().addAttachment(ShakenPlugin.getInstance());
        a.setPermission("test.joined.shaken", true);
    }
}
