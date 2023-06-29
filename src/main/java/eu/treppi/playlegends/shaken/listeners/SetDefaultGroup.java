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

        //todo: put permissions into player attachment from database connection.GetUserPermissions()
        // also rename method

        ShakenGroupManager.checkForDefaultGroup(e.getPlayer());
    }
}
