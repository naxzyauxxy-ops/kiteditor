// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom;

import blokplugins.kitroom.database.MySQL;
import blokplugins.kitroom.database.SQLite;
import blokplugins.kitroom.listeners.PlayerJoinEventListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import blokplugins.kitroom.listeners.InventoryClickEventListener;
import blokplugins.kitroom.commands.CommandKitadmin;
import blokplugins.kitroom.commands.CommandEc;
import blokplugins.kitroom.commands.CommandK;
import org.bukkit.command.CommandExecutor;
import blokplugins.kitroom.commands.CommandKit;
import blokplugins.kitroom.database.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kitroom extends JavaPlugin
{
    private static DatabaseManager databaseManager;
    
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        this.saveResource("config.yml", false);
        this.saveDefaultConfig();
        this.getCommand("kit").setExecutor((CommandExecutor)new CommandKit());
        for (int i = 1; i <= 9; ++i) {
            this.getCommand("k" + i).setExecutor((CommandExecutor)new CommandK());
        }
        for (int i = 1; i <= 9; ++i) {
            this.getCommand("ec" + i).setExecutor((CommandExecutor)new CommandEc());
        }
        this.getCommand("kitadmin").setExecutor((CommandExecutor)new CommandKitadmin());
        this.getServer().getPluginManager().registerEvents((Listener)new InventoryClickEventListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerJoinEventListener(), (Plugin)this);
        final String databaseType = this.getConfig().getString("databasetype");
        final String lowerCase = databaseType.toLowerCase();
        switch (lowerCase) {
            case "sqlite": {
                Kitroom.databaseManager = new SQLite(this);
                break;
            }
            case "mysql": {
                Kitroom.databaseManager = new MySQL(this);
                break;
            }
            default: {
                this.getLogger().severe("[config] Invalid database type, plugin is disabled");
                this.getServer().getPluginManager().disablePlugin((Plugin)this);
                return;
            }
        }
        this.getServer().getScheduler().runTaskAsynchronously((Plugin)this, () -> Kitroom.databaseManager.connect());
    }
    
    public void onDisable() {
        if (Kitroom.databaseManager != null) {
            Kitroom.databaseManager.disconnect();
        }
    }
    
    public static DatabaseManager getDbManager() {
        return Kitroom.databaseManager;
    }
}
