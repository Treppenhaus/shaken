package eu.treppi.playlegends.shaken;

import eu.treppi.playlegends.shaken.listeners.SetPlayerPermissions;
import eu.treppi.playlegends.shaken.oop.ShakenGroup;
import eu.treppi.playlegends.shaken.oop.ShakenGroupInfo;
import org.bukkit.entity.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ShakenDatabaseConnection {
    private static final String mysqlHost = "localhost";
    private static final String mysqlDatabase = "shaken";
    private static final String mysqlUser = "root";
    private static final String mysqlPassword = "";
    private static final int mysqlPort = 3306;
    Connection connection = null;
    public ArrayList<ShakenGroup> getAllGroups() {
        //todo: also make use of a group cache somehow and refresh every x seconds or something
        // could make the refresh delay dependant on online player count or tps
        ArrayList<ShakenGroup> list = new ArrayList<>();

        try {
            //todo: make use of multiple connections and use a free one
            // like a connection manager or something which provides a getConnection() which gives a free one
            // instead of using the same one all the time and get it clogged up
            Statement statement = getFreeConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM groups");

            while (resultSet.next()) {
                list.add(new ShakenGroup(
                        resultSet.getString("name"),
                        resultSet.getString("prefix"),
                        resultSet.getInt("id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }
        return list;
    }

    public boolean createGroup(ShakenGroup group) {
        try {
            PreparedStatement statement = getFreeConnection().prepareStatement("INSERT INTO groups (name, prefix) VALUES (?, ?)");
            statement.setString(1, group.getName());
            statement.setString(2, group.getPrefix());
            // executeUpdate() returns rows inserted, if one row got inserted, the group was added successfully
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }
        return false;
    }

    public boolean addUserToGroup(Player player, ShakenGroup group) {
        try {
            PreparedStatement statement = getFreeConnection().prepareStatement("INSERT INTO players (uuid, rankid, expires) VALUES (?, ?, ?)");
            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, group.getId());
            statement.setLong(3, -1);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }
        return false;
    }

    public void setUserGroup(Player player, ShakenGroup group) {
        try {
            // delete all current group assignments
            PreparedStatement statement = getFreeConnection().prepareStatement("DELETE FROM players WHERE uuid = ?");
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();

            // add to new group specifically
            addUserToGroup(player, group);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }
    }

    public void removeUserFromGroup(Player player, ShakenGroup group) {
        try {
            // delete player from current group
            PreparedStatement statement = getFreeConnection().prepareStatement("DELETE FROM players WHERE uuid = ? AND rankid = ?");
            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }
    }


    public ArrayList<ShakenGroupInfo> getGroupsOf(UUID uuid) {
        ArrayList<ShakenGroupInfo> list = new ArrayList<>();
        try {
            Statement statement = getFreeConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM players WHERE uuid = \""+uuid+"\"");

            while (resultSet.next()) {
                int groupID = resultSet.getInt("rankid");
                list.add(new ShakenGroupInfo(
                    ShakenGroupManager.getGroupById(groupID),
                    UUID.fromString(resultSet.getString("uuid")),
                    resultSet.getLong("expires")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }
        return list;
    }

    public Connection getFreeConnection() {
        // todo: do not always give the same connection, make a pool or use already existing methods from jdbc
        // if existing...
        return connection;
    }

    public boolean connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+mysqlHost+":"+mysqlPort+"/"+mysqlDatabase, mysqlUser, mysqlPassword);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGroup(ShakenGroup group) {
        // delete group from groups list, also delete all entries from the users
        int c = 0;
        try {
            // delete group
            PreparedStatement statement = getFreeConnection().prepareStatement("DELETE FROM groups WHERE id = ?");
            statement.setInt(1, group.getId());
            c += statement.executeUpdate();

            // delete player assignments
            // todo: rename rankid to groupid (?)
            PreparedStatement statement2 = getFreeConnection().prepareStatement("DELETE FROM players WHERE rankid = ?");
            statement.setInt(1, group.getId());
            c +=statement.executeUpdate();

            return c != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }

        return false;
    }

    public boolean unsetUserPermission(Player player, String permission) {
        SetPlayerPermissions.attachments.get(player.getUniqueId().toString()).unsetPermission(permission);
        try {
            PreparedStatement statement = getFreeConnection().prepareStatement("DELETE FROM player_permissions WHERE uuid = ? AND permission = ?");
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, permission);
            return statement.executeUpdate() > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addPermissionToUser(Player player, String permission, boolean value) {
        try {
            SetPlayerPermissions.attachments.get(player.getUniqueId().toString()).setPermission(permission, value);
            PreparedStatement statement = getFreeConnection().prepareStatement("INSERT INTO player_permissions (uuid, permission, value) VALUES (?, ?, ?)");
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, permission);
            statement.setBoolean(3, value);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    public boolean addPermissionToGroup(ShakenGroup group, String permission, boolean value) {
         // todo: somehow refresh permissions for online users in group so the ydo not have to rejoin to take effect
        try {
            PreparedStatement statement = getFreeConnection().prepareStatement("INSERT INTO group_permissions (id, permission, value) VALUES (?, ?, ?)");
            statement.setInt(1, group.getId());
            statement.setString(2, permission);
            statement.setBoolean(3, value);
            return statement.executeUpdate() == 1;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unsetGroupPermission(ShakenGroup group, String permission) {
        // todo: same like addPermissionToGroup()
        try {
            PreparedStatement statement = getFreeConnection().prepareStatement("DELETE FROM group_permissions WHERE id = ? AND permission = ?");
            statement.setInt(1, group.getId());
            statement.setString(1, permission);
            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashMap<String, Boolean> getUserPermissions(String uuid) {
        HashMap<String, Boolean> permissions = new HashMap<>();
        try {
            Statement statement = getFreeConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM player_permissions where uuid = \""+uuid+"\"");

            while (resultSet.next()) {
                String permission = resultSet.getString("permission");
                boolean value = resultSet.getBoolean("value");
                permissions.put(permission, value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and result set
        }
        return permissions;
    }
}
