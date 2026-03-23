// Modified by Claude - Added Bedrock shift-click hint in lore
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

    public KitMenu(final Player p) {
        this.inv = Bukkit.createInventory((InventoryHolder) new KitMenuHolder(), 45,
                ChatColor.LIGHT_PURPLE + p.getDisplayName() + "'s kits");
        this.InitializeItems(p.hasPermission("kitroom.admin"));
        p.openInventory(this.inv);
    }

    public void InitializeItems(final Boolean isAdmin) {
        final ItemStack filler = KitMenu.createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 45; ++i) {
            this.inv.setItem(i, filler);
        }

        // Kit slots
        final ItemStack chest = new ItemStack(Material.CHEST);
        final ItemMeta metachest = chest.getItemMeta();
        for (int j = 9; j < 18; ++j) {
            metachest.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "KIT " + (j - 8));
            final List<String> chestlore = new ArrayList<>();
            chestlore.add(ChatColor.GRAY + "- Left click to load kit");
            chestlore.add(ChatColor.GRAY + "- Right click to edit kit");
            chestlore.add(ChatColor.GRAY + "- Shift click to edit kit (Bedrock)");
            metachest.setLore(chestlore);
            chest.setItemMeta(metachest);
            this.inv.setItem(j, new ItemStack(chest));
        }

        // Ender chest slots
        final ItemStack echest = new ItemStack(Material.ENDER_CHEST);
        final ItemMeta metaechest = echest.getItemMeta();
        for (int k = 18; k < 27; ++k) {
            metaechest.setDisplayName("" + ChatColor.DARK_GRAY + ChatColor.BOLD + "ENDERCHEST " + (k - 17));
            final List<String> echestlore = new ArrayList<>();
            echestlore.add(ChatColor.GRAY + "- Left click to load ender chest");
            echestlore.add(ChatColor.GRAY + "- Right click to edit ender chest");
            echestlore.add(ChatColor.GRAY + "- Shift click to edit ender chest (Bedrock)");
            metaechest.setLore(echestlore);
            echest.setItemMeta(metaechest);
            this.inv.setItem(k, new ItemStack(echest));
        }

        final ItemStack kitroom = KitMenu.createItem.CreateItem(Material.NETHER_STAR,
                "" + ChatColor.GREEN + ChatColor.BOLD + "KIT ROOM");
        this.inv.setItem(30, kitroom);

        final List<String> infolore = new ArrayList<>();
        infolore.add(ChatColor.GRAY + "- Click a kit slot to load your kit");
        infolore.add(ChatColor.GRAY + "- Right click / Shift click (Bedrock) to edit");
        final ItemStack info = KitMenu.createItem.CreateItem(Material.OAK_SIGN,
                "" + ChatColor.GREEN + ChatColor.BOLD + "INFO", infolore);
        this.inv.setItem(31, info);

        final List<String> clearlore = new ArrayList<>();
        clearlore.add(ChatColor.GRAY + "- Shift Click");
        final ItemStack clear = KitMenu.createItem.CreateItem(Material.REDSTONE_BLOCK,
                "" + ChatColor.RED + ChatColor.BOLD + "CLEAR INVENTORY", clearlore);
        this.inv.setItem(32, clear);

        if (isAdmin) {
            final ItemStack admin = KitMenu.createItem.CreateItem(Material.ENCHANTED_GOLDEN_APPLE,
                    "" + ChatColor.RED + ChatColor.BOLD + "Admin Tools");
            this.inv.setItem(40, admin);
        }
    }

    static {
        KitMenu.createItem = new CreateItem();
    }
}
