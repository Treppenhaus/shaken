package eu.treppi.playlegends.shaken;

import eu.treppi.playlegends.shaken.commands.ShakenCommand;
import eu.treppi.playlegends.shaken.listeners.SetDefaultGroup;
import eu.treppi.playlegends.shaken.listeners.SetPlayerPermissions;
import eu.treppi.playlegends.shaken.test.PermcheckCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShakenPlugin extends JavaPlugin {
    public Logger logger = Bukkit.getLogger();
    public ShakenDatabaseConnection connection = new ShakenDatabaseConnection();;
    private static ShakenPlugin instance = null;
    @Override
    public void onEnable() {
        connection.connect();
        connection.getAllGroups().forEach(g -> {
            logger.log(Level.INFO, g.getPrefix() + " ... " + g.getName() + " ... " + g.getId());
        });
        getCommand("shaken").setExecutor(new ShakenCommand());
        getCommand("permcheck").setExecutor(new PermcheckCommand());

        // add events
        // todo: extra method??
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new SetDefaultGroup(), this);
        manager.registerEvents(new SetPlayerPermissions(), this);

        instance = this;
    }

    public static ShakenPlugin getInstance() {
        return instance;
    }

}
