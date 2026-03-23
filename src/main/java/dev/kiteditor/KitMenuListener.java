package dev.kiteditor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class KitMenuListener implements Listener {

    private final KitEditorBedrock plugin;

    public KitMenuListener(KitEditorBedrock plugin) {
        this.plugin = plugin;
    }

    // When the kit menu opens and we have a pending edit, fire a right-click on the correct slot
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        if (!plugin.getPendingEdits().containsKey(uuid)) return;

        // KitMaster menu title contains "'s kits"
        String title = event.getView().getTitle();
        if (!title.contains("'s kits")) return;

        int kitNum = plugin.getPendingEdits().remove(uuid);
        // Slot index: Kit 1 = slot 0, Kit 2 = slot 1, etc.
        int slot = kitNum - 1;

        Inventory inv = event.getInventory();

        // Wait 1 tick so the inventory is fully open before firing the click
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (!player.isOnline()) return;

            InventoryClickEvent fakeClick = new InventoryClickEvent(
                player.getOpenInventory(),
                InventoryType.SlotType.CONTAINER,
                slot,
                ClickType.RIGHT,
                InventoryAction.PICKUP_HALF
            );
            plugin.getServer().getPluginManager().callEvent(fakeClick);

        }, 1L);
    }

    // Handle clicks on the Kit Selector nether star
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() != org.bukkit.Material.NETHER_STAR) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;
        if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lKit Selector")) return;

        event.setCancelled(true);
        UUID uuid = player.getUniqueId();

        if (event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.SHIFT_RIGHT) {
            if (player.isSneaking()) {
                // Cycle kit slot 1 → 2 → ... → 9 → 1
                int cur = plugin.getSelectedKit().getOrDefault(uuid, 1);
                int next = (cur % 9) + 1;
                plugin.getSelectedKit().put(uuid, next);
                player.sendMessage("§7[§5Kits§7] §eSwitched to §b§lKit " + next);
            } else {
                // Open editor for selected kit
                int kit = plugin.getSelectedKit().getOrDefault(uuid, 1);
                plugin.getPendingEdits().put(uuid, kit);
                player.sendMessage("§7[§5Kits§7] §eOpening editor for §b§lKit " + kit + "§e...");
                player.closeInventory();
                plugin.getServer().getScheduler().runTaskLater(plugin,
                    () -> player.performCommand("kit"), 1L);
            }
        } else if (event.getClick() == ClickType.LEFT) {
            // Load selected kit
            int kit = plugin.getSelectedKit().getOrDefault(uuid, 1);
            player.sendMessage("§7[§5Kits§7] §aLoading §b§lKit " + kit + "§a...");
            player.closeInventory();
            plugin.getServer().getScheduler().runTaskLater(plugin,
                () -> player.performCommand("k" + kit), 1L);
        }
    }

    // Clean up pending edit if player closes the menu without a slot being clicked
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        plugin.getPendingEdits().remove(player.getUniqueId());
    }
}
