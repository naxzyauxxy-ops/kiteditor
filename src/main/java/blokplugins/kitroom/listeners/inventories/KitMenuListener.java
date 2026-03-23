// Modified by Claude - Added Bedrock (Floodgate) support
package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.inventories.EChestEditor;
import blokplugins.kitroom.inventories.KitEditor;
import blokplugins.kitroom.inventories.KitRoom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitMenuListener {

    /**
     * Returns true if this player is a Bedrock player connected via Geyser/Floodgate.
     * Floodgate prefixes Bedrock UUIDs with zeros in the most significant bits.
     * We also check via the Floodgate API if it's available.
     */
    private static boolean isBedrockPlayer(Player p) {
        // Check Floodgate API if present
        try {
            Class<?> floodgateApi = Class.forName("org.geysermc.floodgate.api.FloodgateApi");
            Object api = floodgateApi.getMethod("getInstance").invoke(null);
            return (boolean) floodgateApi.getMethod("isFloodgatePlayer", java.util.UUID.class)
                    .invoke(api, p.getUniqueId());
        } catch (Exception ignored) {
            // Floodgate not installed or error - fall back to UUID check
            // Floodgate Bedrock players have UUIDs starting with 00000000-0000-0000-
            return p.getUniqueId().toString().startsWith("00000000-0000-0000-");
        }
    }

    public static void handleInventoryClick(final InventoryClickEvent e) {
        final int slot = e.getRawSlot();
        final Player p = (Player) e.getWhoClicked();
        final boolean bedrock = isBedrockPlayer(p);

        switch (slot) {
            // Kit slots 1-9 are at raw slots 9-17
            case 9: case 10: case 11: case 12: case 13:
            case 14: case 15: case 16: case 17: {
                // Bedrock players can only left-click, so we need a way to distinguish
                // load vs edit. We use shift+click = edit, normal click = load.
                if (e.isRightClick() || (bedrock && e.isShiftClick())) {
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
                if (e.isRightClick() || (bedrock && e.isShiftClick())) {
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
                if (e.isShiftClick()) {
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
}
