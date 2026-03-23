// Modified by Claude - Separate load/edit rows, no ender chest
package blokplugins.kitroom.inventories;

import blokplugins.kitroom.holders.KitMenuHolder;
import blokplugins.kitroom.utils.CreateItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitMenu {

    private static CreateItem createItem;
    public Inventory inv;

    // Layout (36 slots, 4 rows):
    // Row 0 (0-8):   filler
    // Row 1 (9-17):  KIT 1-9 load buttons (CHEST)
    // Row 2 (18-26): KIT 1-9 edit buttons (ANVIL)
    // Row 3 (27-35): filler + kit room (30) + info (31) + clear (32) + admin (35)

    public KitMenu(final Player p) {
        this.inv = Bukkit.createInventory((InventoryHolder) new KitMenuHolder(), 36,
                ChatColor.LIGHT_PURPLE + p.getDisplayName() + "'s kits");
        this.InitializeItems(p.hasPermission("kitroom.admin"));
        p.openInventory(this.inv);
    }

    public void InitializeItems(final Boolean isAdmin) {
        final ItemStack filler = KitMenu.createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 36; ++i) {
            this.inv.setItem(i, filler);
        }

        // Kit LOAD buttons — row 1 (slots 9-17)
        for (int j = 9; j < 18; ++j) {
            int kitNum = j - 8;
            ItemStack chest = new ItemStack(Material.CHEST);
            ItemMeta meta = chest.getItemMeta();
            meta.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "KIT " + kitNum);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "▶ Click to LOAD kit");
            meta.setLore(lore);
            chest.setItemMeta(meta);
            this.inv.setItem(j, chest);
        }

        // Kit EDIT buttons — row 2 (slots 18-26)
        for (int j = 18; j < 27; ++j) {
            int kitNum = j - 17;
            ItemStack anvil = new ItemStack(Material.ANVIL);
            ItemMeta meta = anvil.getItemMeta();
            meta.setDisplayName("" + ChatColor.YELLOW + ChatColor.BOLD + "EDIT KIT " + kitNum);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "✎ Click to EDIT kit");
            meta.setLore(lore);
            anvil.setItemMeta(meta);
            this.inv.setItem(j, anvil);
        }

        // Bottom row
        final ItemStack kitroom = KitMenu.createItem.CreateItem(Material.NETHER_STAR,
                "" + ChatColor.GREEN + ChatColor.BOLD + "KIT ROOM");
        this.inv.setItem(30, kitroom);

        final List<String> infolore = new ArrayList<>();
        infolore.add(ChatColor.GRAY + "- Top row: click to load kit");
        infolore.add(ChatColor.GRAY + "- Bottom row: click to edit kit");
        final ItemStack info = KitMenu.createItem.CreateItem(Material.OAK_SIGN,
                "" + ChatColor.GREEN + ChatColor.BOLD + "INFO", infolore);
        this.inv.setItem(31, info);

        final List<String> clearlore = new ArrayList<>();
        clearlore.add(ChatColor.GRAY + "- Click to clear inventory");
        final ItemStack clear = KitMenu.createItem.CreateItem(Material.REDSTONE_BLOCK,
                "" + ChatColor.RED + ChatColor.BOLD + "CLEAR INVENTORY", clearlore);
        this.inv.setItem(32, clear);

        if (isAdmin) {
            final ItemStack admin = KitMenu.createItem.CreateItem(Material.ENCHANTED_GOLDEN_APPLE,
                    "" + ChatColor.RED + ChatColor.BOLD + "Admin Tools");
            this.inv.setItem(35, admin);
        }
    }

    static {
        KitMenu.createItem = new CreateItem();
    }
}
