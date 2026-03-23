package blokplugins.kitroom.database;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;

public class SQLite extends DatabaseManager {

    private Connection connection;
    private final JavaPlugin plugin;

    public SQLite(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void connect() {
        try {
            File dataFolder = plugin.getDataFolder();
            String dbPath = dataFolder.getPath() + File.separator + "kitmaster.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            Statement query = connection.createStatement();
            query.execute("CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, k1 TEXT DEFAULT NULL, k2 TEXT DEFAULT NULL, k3 TEXT DEFAULT NULL, k4 TEXT DEFAULT NULL, k5 TEXT DEFAULT NULL, k6 TEXT DEFAULT NULL, k7 TEXT DEFAULT NULL, k8 TEXT DEFAULT NULL, k9 TEXT DEFAULT NULL, ec1 TEXT DEFAULT NULL, ec2 TEXT DEFAULT NULL, ec3 TEXT DEFAULT NULL, ec4 TEXT DEFAULT NULL, ec5 TEXT DEFAULT NULL, ec6 TEXT DEFAULT NULL, ec7 TEXT DEFAULT NULL, ec8 TEXT DEFAULT NULL, ec9 TEXT DEFAULT NULL)");
            query.execute("CREATE TABLE IF NOT EXISTS kitroom (name TEXT PRIMARY KEY UNIQUE, kit TEXT)");
            query.close();
            for (String kit : new String[]{"equipment", "pvp", "potions"}) {
                PreparedStatement stmt = connection.prepareStatement("INSERT OR IGNORE INTO kitroom (name, kit) VALUES (?, ?)");
                stmt.setString(1, kit);
                stmt.setString(2, null);
                stmt.executeUpdate();
                stmt.close();
            }
            plugin.getLogger().info("[SQLite] Connected to SQLite database");
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] SQLite connection has failed >> " + e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.getLogger().info("[SQLite] Disconnected from sqlite db");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }

    @Override
    public void playerJoin(Player player) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT OR IGNORE INTO players (uuid) VALUES (?)");
            stmt.setString(1, player.getUniqueId().toString());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to insert player: " + e.getMessage());
        }
    }

    @Override
    public String getKit(Player player, String kitType) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT " + kitType + " FROM players WHERE uuid = ?");
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString(kitType);
            stmt.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to fetch kit: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void saveKit(Player player, String kitType, String kitData) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE players SET " + kitType + " = ? WHERE uuid = ?");
            stmt.setString(1, kitData);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to save kit: " + e.getMessage());
        }
    }

    @Override
    public void saveKitRoom(String name, String kitData) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO kitroom (name, kit) VALUES (?, ?) ON CONFLICT(name) DO UPDATE SET kit = excluded.kit");
            stmt.setString(1, name);
            stmt.setString(2, kitData);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to save kitroom: " + e.getMessage());
        }
    }

    @Override
    public String getKitRoom(String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT kit FROM kitroom WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("kit");
            stmt.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to get kitroom: " + e.getMessage());
        }
        return null;
    }
}
