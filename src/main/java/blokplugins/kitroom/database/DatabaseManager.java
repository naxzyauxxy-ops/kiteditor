// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.database;

import org.bukkit.entity.Player;

public interface DatabaseManager
{
    void connect();
    
    void disconnect();
    
    void playerJoin(final Player p0);
    
    String getKit(final Player p0, final String p1);
    
    void saveKit(final Player p0, final String p1, final String p2);
    
    void saveKitRoom(final String p0, final String p1);
    
    String getKitRoom(final String p0);
}
