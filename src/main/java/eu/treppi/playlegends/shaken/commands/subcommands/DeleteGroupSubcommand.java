package eu.treppi.playlegends.shaken.commands.subcommands;

import eu.treppi.playlegends.shaken.ShakenGroupManager;
import eu.treppi.playlegends.shaken.ShakenPlugin;
import eu.treppi.playlegends.shaken.commands.ShakenSubcommand;
import eu.treppi.playlegends.shaken.oop.ShakenGroup;
import org.bukkit.command.CommandSender;

public class DeleteGroupSubcommand implements ShakenSubcommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length > 1) {
            String groupname = args[1];
            if(groupname.equalsIgnoreCase("default")) {
                sender.sendMessage("can not delete default group!");
                return;
            }

            ShakenGroup group = ShakenGroupManager.getGroupByName(groupname);
            if(group == null) {
                sender.sendMessage("group " + groupname + " not found!");
                return;
            }

            if(ShakenPlugin.getInstance().connection.deleteGroup(group)) {
                sender.sendMessage("group deleted successfully!");
            }
            else {
                sender.sendMessage("could not delete group!");
            }

        }
    }

    @Override
    public String getName() {
        return "deletegroup";
    }

    @Override
    public String getPermission() {
        return "shaken.deletegroup";
    }

    @Override
    public String getDescription() {
        return "/shaken deletegroup <groupname>";
    }
}
