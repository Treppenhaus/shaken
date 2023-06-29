package eu.treppi.playlegends.shaken.commands.subcommands;

import eu.treppi.playlegends.shaken.ShakenDatabaseConnection;
import eu.treppi.playlegends.shaken.ShakenGroupManager;
import eu.treppi.playlegends.shaken.ShakenPlugin;
import eu.treppi.playlegends.shaken.commands.ShakenSubcommand;
import eu.treppi.playlegends.shaken.oop.ShakenGroup;
import org.bukkit.command.CommandSender;

public class GroupSubcommand implements ShakenSubcommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length > 4) {
            String groupname = args[1];
            ShakenGroup group = ShakenGroupManager.getGroupByName(groupname);
            if(group == null) {
                sender.sendMessage("group " + groupname + " not found!");
                return;
            }

            ShakenDatabaseConnection conn = ShakenPlugin.getInstance().connection;
            if(args[2].equalsIgnoreCase("permission")) {
                String permission = args[4];
                if(args[3].equalsIgnoreCase("add")) {
                    if(conn.addPermissionToGroup(group, permission, true)) {
                        sender.sendMessage("permission " + permission + " added to group " + group.getName());
                    }
                }
                else if(args[3].equalsIgnoreCase("set")) {
                    boolean value;
                    try {
                        value = Boolean.parseBoolean(args[5]);
                    }catch (Exception e) {
                        sender.sendMessage("please provide true/false");
                        return;
                    }
                    if(conn.addPermissionToGroup(group, permission, value)) {
                        sender.sendMessage("permission " + permission + " for group " +group.getName() + " set to " + value);
                    }
                }
                else if(args[3].equalsIgnoreCase("unset")) {
                    if(conn.unsetGroupPermission(group, permission)) {
                        sender.sendMessage("permission " + permission + " was unset for group " + group.getName());
                    }
                }
                else sender.sendMessage(getDescription());
            }
            else sender.sendMessage(getDescription());
        }
        else sender.sendMessage(getDescription());
    }

    @Override
    public String getName() {
        return "group";
    }

    @Override
    public String getPermission() {
        return "shaken.group";
    }

    @Override
    public String getDescription() {
        return """
                /shaken group groupname permission add example.permission
                /shaken group groupname permission set example.permission true/false
                /shaken group groupname permission unset example.permission""";
    }
}
