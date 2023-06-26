package eu.treppi.playlegends.shaken.commands.subcommands;

import eu.treppi.playlegends.shaken.ShakenPlugin;
import eu.treppi.playlegends.shaken.commands.ShakenSubcommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

public class UserinfoSubcommand implements ShakenSubcommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length > 1) {
            String username = args[1];
            Player player = Bukkit.getPlayer(username);
            if(player == null) {
                sender.sendMessage("player " + username + " not found!");
                return;
            }
            sender.sendMessage("current userinfo for: "+username);
            ShakenPlugin.getInstance().connection.getGroupsOf(player.getUniqueId()).forEach(g -> {
                sender.sendMessage("- " + g.getName() + "\texpires: " + (g.expires() ? new Timestamp(g.expiresWhen()) : "/"));
            });
        }
        else {
            sender.sendMessage(getDescription());
        }
    }

    @Override
    public String getName() {
        return "userinfo";
    }

    @Override
    public String getPermission() {
        return "shaken.userinfo";
    }

    @Override
    public String getDescription() {
        return "/shaken userinfo <username>";
    }
}
