package eu.treppi.playlegends.shaken.commands.subcommands;

import eu.treppi.playlegends.shaken.ShakenPlugin;
import eu.treppi.playlegends.shaken.commands.ShakenSubcommand;
import org.bukkit.command.CommandSender;

public class ListgroupsSubcommand implements ShakenSubcommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        sender.sendMessage("current list of all available groups:");
        ShakenPlugin.getInstance().connection.getAllGroups().forEach(g -> {
            sender.sendMessage(g.getId() + "\t" + g.getName());
        });
    }

    @Override
    public String getName() {
        return "listgroups";
    }

    @Override
    public String getPermission() {
        return "shaken.listgroups";
    }

    @Override
    public String getDescription() {
        return "lists all groups";
    }
}
