package eu.treppi.playlegends.shaken.test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PermcheckCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length == 0) {
            sender.sendMessage("/permcheck permission.example");
            return false;
        }

        String val = sender.hasPermission(args[0])+"";
        sender.sendMessage(val);
        System.out.println(val);
        return false;
    }
}
