// Modified by Claude - Separate load/edit rows, no ender chest
package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.inventories.KitEditor;
import blokplugins.kitroom.inventories.KitRoom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitMenuListener {

    public static void handleInventoryClick(final InventoryClickEvent e) {
        final int slot = e.getRawSlot();
        final Player p = (Player) e.getWhoClicked();

        // Row 1 (slots 9-17): Kit LOAD buttons
        if (slot >= 9 && slot <= 17) {
            p.performCommand("k" + (slot - 8));
            e.setCancelled(true);
            p.closeInventory();
            return;
        }

        // Row 2 (slots 18-26): Kit EDIT buttons
        if (slot >= 18 && slot <= 26) {
            new KitEditor(p, slot - 17, null);
            e.setCancelled(true);
            return;
        }

        // Bottom row
        switch (slot) {
            case 30: {
                new KitRoom(p, "equipment", false);
                e.setCancelled(true);
                break;
            }
            case 32: {
                p.getInventory().clear();
                e.setCancelled(true);
                break;
            }
            case 35: {
                if (p.hasPermission("kitroom.admin")) {
                    new KitRoom(p, "equipment", true);
                    e.setCancelled(true);
                }
                break;
            }
        }

        e.setCancelled(true);
    }
}
