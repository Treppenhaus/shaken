package eu.treppi.playlegends.shaken.commands;

import org.bukkit.command.CommandSender;

public interface ShakenSubcommand {
    default void onCommand(CommandSender sender, String[] args) {

    }
    String getName();
    String getPermission();
    String getDescription();
}
