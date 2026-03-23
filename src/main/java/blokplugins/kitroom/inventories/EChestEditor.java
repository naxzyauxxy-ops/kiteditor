// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.inventories;

import java.util.List;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.Material;
import blokplugins.kitroom.Kitroom;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import blokplugins.kitroom.holders.EChestEditorHolder;
import org.bukkit.entity.Player;
import blokplugins.kitroom.utils.CreateItem;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.inventory.Inventory;

public class EChestEditor
{
    public Inventory inv;
    static SerializeInventory serializeInventory;
    private static CreateItem createItem;
    
    public EChestEditor(final Player p, final Integer echest, final Inventory prekit) {
        this.inv = Bukkit.createInventory((InventoryHolder)new EChestEditorHolder(), 36, ChatColor.LIGHT_PURPLE + "Ender Chest: " + String.valueOf(echest));
        this.InitializeItems();
        final String userkit = Kitroom.getDbManager().getKit(p, "ec" + echest);
        if (userkit != null) {
            this.InitializeItemsKit(userkit);
        }
        if (prekit != null) {
            for (int i = 0; i < 27; ++i) {
                this.inv.setItem(i, prekit.getItem(i));
            }
        }
        p.openInventory(this.inv);
    }
    
    public void InitializeItems() {
        final ItemStack filler = EChestEditor.createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        for (int i = 27; i <= 31; ++i) {
            this.inv.setItem(i, filler);
        }
        final List<String> chestlore = new ArrayList<String>();
        chestlore.add(ChatColor.GRAY + "- Imports current ender chest");
        final ItemStack chest = EChestEditor.createItem.CreateItem(Material.CHEST, "" + ChatColor.GREEN + ChatColor.BOLD + "IMPORT", chestlore);
        this.inv.setItem(33, chest);
        final List<String> savelore = new ArrayList<String>();
        savelore.add(ChatColor.GRAY + "- Saves current ender chest");
        final ItemStack save = EChestEditor.createItem.CreateItem(Material.LIME_DYE, "" + ChatColor.GREEN + ChatColor.BOLD + "SAVE", savelore);
        this.inv.setItem(32, save);
        final List<String> clearlore = new ArrayList<String>();
        clearlore.add(ChatColor.GRAY + "- Shift Click to clear");
        final ItemStack clear = EChestEditor.createItem.CreateItem(Material.BARRIER, "" + ChatColor.RED + ChatColor.BOLD + "CLEAR ENDER CHEST", clearlore);
        this.inv.setItem(34, clear);
        final ItemStack back = EChestEditor.createItem.CreateItem(Material.OAK_DOOR, "" + ChatColor.RED + ChatColor.BOLD + "BACK");
        this.inv.setItem(35, back);
    }
    
    public void InitializeItemsKit(final String userkit) {
        final Inventory kitinv = EChestEditor.serializeInventory.Deserialize(userkit);
        for (int i = 0; i < kitinv.getSize(); ++i) {
            if (kitinv.getItem(i) != null) {
                this.inv.setItem(i, kitinv.getItem(i));
            }
        }
    }
    
    static {
        EChestEditor.serializeInventory = new SerializeInventory();
        EChestEditor.createItem = new CreateItem();
    }
}
