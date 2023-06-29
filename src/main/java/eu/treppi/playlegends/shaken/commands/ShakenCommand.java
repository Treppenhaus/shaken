package eu.treppi.playlegends.shaken.commands;

import eu.treppi.playlegends.shaken.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShakenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length > 0) {
            for(ShakenSubcommand shakenSubcommand : getSubcommands()) {
                if(shakenSubcommand.getName().equalsIgnoreCase(args[0])) {
                    if(sender.hasPermission(shakenSubcommand.getPermission())) {
                        shakenSubcommand.onCommand(sender, args);
                    }
                    else {
                        sender.sendMessage("missing permission node: " + shakenSubcommand.getPermission());
                    }
                    return false;
                }
            }
        }

        sender.sendMessage("not found, available commands are:");
        getSubcommands().forEach(sub -> sender.sendMessage("/shaken " + sub.getName()));
        return false;
    }

    public List<ShakenSubcommand> getSubcommands() {
        return List.of(
                new UserSubcommand(),
                new ListgroupsSubcommand(),
                new UserinfoSubcommand(),
                new CreateGroupSubcommand(),
                new DeleteGroupSubcommand(),
                new ListPlayerPermsSubcommand(),
                new GroupSubcommand()
        );
    }
}
