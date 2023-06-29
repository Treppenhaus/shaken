package eu.treppi.playlegends.shaken.commands.subcommands;

import eu.treppi.playlegends.shaken.ShakenGroupManager;
import eu.treppi.playlegends.shaken.ShakenPlugin;
import eu.treppi.playlegends.shaken.commands.ShakenSubcommand;
import eu.treppi.playlegends.shaken.oop.ShakenGroup;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserSubcommand implements ShakenSubcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length > 4) {
            String username = args[1];
            Player target = Bukkit.getPlayer(username);
            if(target == null) {
                sender.sendMessage("player "+username+" not found!");
                return;
            }

            if(args[2].equalsIgnoreCase("group")) {
                String groupname = args[4];
                ShakenGroup targetGroup = ShakenGroupManager.getGroupByName(groupname);
                if(targetGroup == null) {
                    sender.sendMessage("group " + groupname + " not found! ):");
                    return;
                }

                if(args[3].equalsIgnoreCase("add")) {
                    if(ShakenPlugin.getInstance().connection.addUserToGroup(target, targetGroup)) {
                        sender.sendMessage("group added successfully!");
                    }
                }
                else if(args[3].equalsIgnoreCase("set")) {
                    ShakenPlugin.getInstance().connection.setUserGroup(target, targetGroup);
                    // todo: maybe check if it was really successful instead of just forwarding wrong info
                    sender.sendMessage("group set successfully");

                }
                else if(args[3].equalsIgnoreCase("remove")) {
                    //todo: here as well
                    ShakenPlugin.getInstance().connection.removeUserFromGroup(target, targetGroup);
                    ShakenGroupManager.checkForDefaultGroup(target);
                    sender.sendMessage("group set successfully");
                }

            }
            else if(args[2].equalsIgnoreCase("permission")) {
                if(args[3].equalsIgnoreCase("set") || args[3].equalsIgnoreCase("add")) {
                    String permission = args[4];
                    boolean value = true;
                    if(args.length > 5) {
                        try {
                            value = Boolean.parseBoolean(args[5]);
                        } catch (Exception e) {
                            sender.sendMessage("please provide true/false value");
                            return;
                        }
                    }
                    if(ShakenPlugin.getInstance().connection.addPermissionToUser(target, permission, value)) {
                        sender.sendMessage("permission "+permission+" set to "+value);
                    }
                }
                else if(args[3].equalsIgnoreCase("unset")) {
                    String permission = args[4];
                    if(ShakenPlugin.getInstance().connection.unsetUserPermission(target, permission)) {
                        sender.sendMessage("unset permission "+permission);
                    }
                }
            }
            else {
                sender.sendMessage(getDescription());
            }
        }
        else {
            sender.sendMessage(getDescription());
        }
    }
    @Override
    public String getName() {
        return "user";
    }

    @Override
    public String getPermission() {
        return "shaken.user";
    }

    @Override
    public String getDescription() {
        return "/shaken user username group set/add/remove groupname\n" +
                "/shaken user username permission add example.permission\n" +
                "/shaken user username permission set example.permission true/false\n" +
                "/shaken user username permission unset example.permission";
    }
}
