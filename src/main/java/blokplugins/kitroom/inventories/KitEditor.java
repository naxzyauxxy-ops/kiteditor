// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.inventories;

import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.Material;
import blokplugins.kitroom.Kitroom;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import blokplugins.kitroom.holders.KitEditorHolder;
import org.bukkit.entity.Player;
import blokplugins.kitroom.utils.CreateItem;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.inventory.Inventory;

public class KitEditor
{
    public Inventory inv;
    static SerializeInventory serializeInventory;
    private static CreateItem createItem;
    
    public KitEditor(final Player p, final int kit, final Inventory prekit) {
        this.inv = Bukkit.createInventory((InventoryHolder)new KitEditorHolder(), 54, ChatColor.LIGHT_PURPLE + "Kit: " + String.valueOf(kit));
        final String userkit = Kitroom.getDbManager().getKit(p, "k" + kit);
        if (userkit != null) {
            this.InitializeItemsKit(userkit);
        }
        if (prekit != null) {
            for (int i = 0; i < 41; ++i) {
                this.inv.setItem(i, prekit.getItem(i));
            }
        }
        this.InitializeItems();
        p.openInventory(this.inv);
    }
    
    public void InitializeItems() {
        final ItemStack filler = KitEditor.createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        this.inv.setItem(41, filler);
        this.inv.setItem(42, filler);
        this.inv.setItem(43, filler);
        this.inv.setItem(44, filler);
        final ItemStack helmet = KitEditor.createItem.CreateItem(Material.CHAINMAIL_HELMET, "" + ChatColor.GRAY + ChatColor.BOLD + "HELMET");
        final ItemStack chestplate = KitEditor.createItem.CreateItem(Material.CHAINMAIL_CHESTPLATE, "" + ChatColor.GRAY + ChatColor.BOLD + "CHESTPLATE");
        final ItemStack leggings = KitEditor.createItem.CreateItem(Material.CHAINMAIL_LEGGINGS, "" + ChatColor.GRAY + ChatColor.BOLD + "LEGGINGS");
        final ItemStack boots = KitEditor.createItem.CreateItem(Material.CHAINMAIL_BOOTS, "" + ChatColor.GRAY + ChatColor.BOLD + "BOOTS");
        final ItemStack shield = KitEditor.createItem.CreateItem(Material.SHIELD, "" + ChatColor.GRAY + ChatColor.BOLD + "SHIELD");
        this.inv.setItem(49, shield);
        this.inv.setItem(48, helmet);
        this.inv.setItem(47, chestplate);
        this.inv.setItem(46, leggings);
        this.inv.setItem(45, boots);
        final List<String> saveLore = new ArrayList<String>();
        saveLore.add(ChatColor.GRAY + "- Saves current kit");
        final ItemStack save = KitEditor.createItem.CreateItem(Material.LIME_DYE, "" + ChatColor.GREEN + ChatColor.BOLD + "SAVE", saveLore);
        this.inv.setItem(50, save);
        final List<String> chestlore = new ArrayList<String>();
        chestlore.add(ChatColor.GRAY + "- Imports current inventory");
        final ItemStack chest = KitEditor.createItem.CreateItem(Material.CHEST, "" + ChatColor.GREEN + ChatColor.BOLD + "IMPORT", chestlore);
        this.inv.setItem(51, chest);
        final List<String> clearLore = new ArrayList<String>();
        clearLore.add(ChatColor.GRAY + "- Shift Click to clear");
        final ItemStack clear = KitEditor.createItem.CreateItem(Material.BARRIER, "" + ChatColor.RED + ChatColor.BOLD + "CLEAR KIT", clearLore);
        this.inv.setItem(52, clear);
        final ItemStack back = KitEditor.createItem.CreateItem(Material.OAK_DOOR, "" + ChatColor.RED + ChatColor.BOLD + "BACK", (List<String>)new ArrayList<String>());
        this.inv.setItem(53, back);
    }
    
    public void InitializeItemsKit(final String userkit) {
        final Inventory kitinv = KitEditor.serializeInventory.Deserialize(userkit);
        for (int i = 0; i < kitinv.getSize(); ++i) {
            if (kitinv.getItem(i) != null) {
                this.inv.setItem(i, kitinv.getItem(i));
            }
        }
    }
    
    static {
        KitEditor.serializeInventory = new SerializeInventory();
        KitEditor.createItem = new CreateItem();
    }
}
