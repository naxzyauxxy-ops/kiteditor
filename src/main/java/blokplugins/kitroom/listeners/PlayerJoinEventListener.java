// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.listeners;

import org.bukkit.event.EventHandler;
import blokplugins.kitroom.Kitroom;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class PlayerJoinEventListener implements Listener
{
    @EventHandler
    public void OnPlayerJoin(final PlayerJoinEvent e) {
        Kitroom.getDbManager().playerJoin(e.getPlayer());
    }
}
