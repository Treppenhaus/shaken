package eu.treppi.playlegends.shaken;

import eu.treppi.playlegends.shaken.oop.ShakenGroup;
import org.bukkit.entity.Player;

public class ShakenGroupManager {
    public static ShakenGroup getGroupByName(String name) {
        // todo: use cache or sth
        for(ShakenGroup g : ShakenPlugin.getInstance().connection.getAllGroups()) {
            if(g.getName().equalsIgnoreCase(name)) {
                return g;
            }
        }
        return null;
    }

    public static ShakenGroup getGroupById(int groupdID) {
        for(ShakenGroup g : ShakenPlugin.getInstance().connection.getAllGroups()) {
            if(g.getId() == groupdID) return g;
        }
        return null;
    }

    /**
     * checks and adds user to default group if not already joined
     * @param p: Player
     */
    public static void checkForDefaultGroup(Player p) {
        if(ShakenPlugin.getInstance().connection.getGroupsOf(p.getUniqueId()).size() == 0) {
            ShakenPlugin.getInstance().connection.addUserToGroup(p, getDefaultGroup());
        }
    }

    public static ShakenGroup getDefaultGroup() {
        return getGroupById(1);
    }
}
