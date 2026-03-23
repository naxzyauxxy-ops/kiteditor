package dev.kiteditor;

import org.bukkit.Material;
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
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.UUID;

public class KitMenuListener implements Listener {

    private final KitEditorBedrock plugin;

    public KitMenuListener(KitEditorBedrock plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        if (!plugin.getPendingEdits().containsKey(uuid)) return;
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        if (!title.contains("'s kits")) return;

        int kitNum = plugin.getPendingEdits().remove(uuid);
        int slot = kitNum - 1;
        Inventory inv = event.getInventory();

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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() != Material.NETHER_STAR) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;
        if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lKit Selector")) return;

        event.setCancelled(true);
        UUID uuid = player.getUniqueId();

        if (event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.SHIFT_RIGHT) {
            if (player.isSneaking()) {
                int cur  = plugin.getSelectedKit().getOrDefault(uuid, 1);
                int next = (cur % 9) + 1;
                plugin.getSelectedKit().put(uuid, next);
                player.sendMessage("§7[§5Kits§7] §eSwitched to §b§lKit " + next);
            } else {
                int kit = plugin.getSelectedKit().getOrDefault(uuid, 1);
                plugin.getPendingEdits().put(uuid, kit);
                player.sendMessage("§7[§5Kits§7] §eOpening editor for §b§lKit " + kit + "§e...");
                player.closeInventory();
                plugin.getServer().getScheduler().runTaskLater(plugin,
                    () -> player.performCommand("kit"), 1L);
            }
        } else if (event.getClick() == ClickType.LEFT) {
            int kit = plugin.getSelectedKit().getOrDefault(uuid, 1);
            player.sendMessage("§7[§5Kits§7] §aLoading §b§lKit " + kit + "§a...");
            player.closeInventory();
            plugin.getServer().getScheduler().runTaskLater(plugin,
                () -> player.performCommand("k" + kit), 1L);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        plugin.getPendingEdits().remove(player.getUniqueId());
    }
}
