// Modified by Claude - Shift+Left-Click to edit (works for Java, Bedrock, and mobile)
package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.inventories.EChestEditor;
import blokplugins.kitroom.inventories.KitEditor;
import blokplugins.kitroom.inventories.KitRoom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitMenuListener {

    public static void handleInventoryClick(final InventoryClickEvent e) {
        final int slot = e.getRawSlot();
        final Player p = (Player) e.getWhoClicked();

        switch (slot) {
            // Kit slots 1-9 are at raw slots 9-17
            case 9: case 10: case 11: case 12: case 13:
            case 14: case 15: case 16: case 17: {
                // Shift+click (any button) = edit — works for Java, Bedrock, and mobile
                // Plain left-click = load
                // Right-click = edit (Java only)
                if (e.isRightClick() || e.isShiftClick()) {
                    new KitEditor(p, e.getRawSlot() - 8, null);
                    e.setCancelled(true);
                    break;
                }
                if (e.isLeftClick()) {
                    p.performCommand("k" + (e.getRawSlot() - 8));
                    e.setCancelled(true);
                    p.closeInventory();
                    break;
                }
                break;
            }
            // Ender chest slots 1-9 are at raw slots 18-26
            case 18: case 19: case 20: case 21: case 22:
            case 23: case 24: case 25: case 26: {
                if (e.isRightClick() || e.isShiftClick()) {
                    new EChestEditor(p, e.getRawSlot() - 17, null);
                    e.setCancelled(true);
                    break;
                }
                if (e.isLeftClick()) {
                    p.performCommand("ec" + (e.getRawSlot() - 17));
                    e.setCancelled(true);
                    p.closeInventory();
                    break;
                }
                break;
            }
            case 30: {
                new KitRoom(p, "equipment", false);
                e.setCancelled(true);
                break;
            }
            case 32: {
                // Shift+click on clear button still clears inventory
                if (e.isShiftClick() && !isKitOrEchestSlot(slot)) {
                    p.getInventory().clear();
                }
                break;
            }
            case 40: {
                if (p.hasPermission("kitroom.admin")) {
                    new KitRoom(p, "equipment", true);
                    e.setCancelled(true);
                }
                break;
            }
        }
        e.setCancelled(true);
    }

    private static boolean isKitOrEchestSlot(int slot) {
        return (slot >= 9 && slot <= 26);
    }
}
