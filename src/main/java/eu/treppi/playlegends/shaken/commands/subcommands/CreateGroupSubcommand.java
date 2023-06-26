package eu.treppi.playlegends.shaken.commands.subcommands;

import eu.treppi.playlegends.shaken.ShakenGroupManager;
import eu.treppi.playlegends.shaken.ShakenPlugin;
import eu.treppi.playlegends.shaken.commands.ShakenSubcommand;
import eu.treppi.playlegends.shaken.oop.ShakenGroup;
import org.bukkit.command.CommandSender;

public class CreateGroupSubcommand implements ShakenSubcommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length > 2) {
            String groupname = args[1];
            StringBuilder prefix = new StringBuilder();
            for(int i = 2; i < args.length; i++) {
                prefix.append(args[i]).append(" ");
            }

            // check if group already exists
            if(ShakenGroupManager.getGroupByName(groupname) != null) {
                sender.sendMessage("a group with that name already exists. delete it first pls");
                return;
            }

            ShakenGroup newGroup = new ShakenGroup(groupname, prefix.toString());
            if(ShakenPlugin.getInstance().connection.createGroup(newGroup)) {
                sender.sendMessage("group was created successfully!");
                sender.sendMessage("Name: " + newGroup.getName() + " | prefix: " +newGroup.getPrefix());
            }
            else {
                sender.sendMessage("something went wrong while creating group " + groupname);
            }
        }
        else {
            sender.sendMessage(getDescription());
        }
    }

    @Override
    public String getName() {
        return "creategroup";
    }

    @Override
    public String getPermission() {
        return "shaken.creategroup";
    }

    @Override
    public String getDescription() {
        return "/shaken addgroup <groupname> <prefix .. .. >";
    }
}
