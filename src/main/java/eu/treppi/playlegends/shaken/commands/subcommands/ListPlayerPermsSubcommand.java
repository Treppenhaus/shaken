package eu.treppi.playlegends.shaken.commands.subcommands;

import eu.treppi.playlegends.shaken.ShakenPlugin;
import eu.treppi.playlegends.shaken.commands.ShakenSubcommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class ListPlayerPermsSubcommand implements ShakenSubcommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length > 1) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage("player not found");
                return;
            }

            sender.sendMessage("permissions for : " + target.getName());
            PermissionAttachment att = target.addAttachment(ShakenPlugin.getInstance());
            att.getPermissions().forEach((perm, val) -> {
                sender.sendMessage(perm + "\t" + val);
            });
        }
        else {
            sender.sendMessage(getDescription());
        }
    }

    @Override
    public String getName() {
        return "listplayerperms";
    }

    @Override
    public String getPermission() {
        return "shaken.listperms.player";
    }

    @Override
    public String getDescription() {
        return "/shaken listplayerperms <username>";
    }
}
