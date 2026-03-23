// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.InventoryHolder;
import blokplugins.kitroom.listeners.inventories.KitRoomListener;
import blokplugins.kitroom.holders.KitRoomHolder;
import blokplugins.kitroom.listeners.inventories.EChestEditorListener;
import blokplugins.kitroom.holders.EChestEditorHolder;
import blokplugins.kitroom.listeners.inventories.KitEditorListener;
import blokplugins.kitroom.holders.KitEditorHolder;
import blokplugins.kitroom.listeners.inventories.KitMenuListener;
import blokplugins.kitroom.holders.KitMenuHolder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class InventoryClickEventListener implements Listener
{
    @EventHandler
    public void onInventoryCLick(final InventoryClickEvent e) {
        final InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof KitMenuHolder) {
            KitMenuListener.handleInventoryClick(e);
        }
        if (holder instanceof KitEditorHolder) {
            KitEditorListener.handleInventoryClick(e);
        }
        if (holder instanceof EChestEditorHolder) {
            EChestEditorListener.handleInventoryClick(e);
        }
        if (holder instanceof KitRoomHolder) {
            KitRoomListener.handleInventoryClick(e);
        }
    }
}
