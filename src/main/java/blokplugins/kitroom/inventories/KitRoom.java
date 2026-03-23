// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.inventories;

import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import blokplugins.kitroom.Kitroom;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import blokplugins.kitroom.holders.KitRoomHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import blokplugins.kitroom.utils.SerializeInventory;
import blokplugins.kitroom.utils.CreateItem;

public class KitRoom
{
    private static CreateItem createItem;
    static SerializeInventory serializeInventory;
    public Inventory inv;
    
    public KitRoom(final Player p, final String room, final boolean isAdmin) {
        this.inv = Bukkit.createInventory((InventoryHolder)new KitRoomHolder(), 54, room);
        this.InitializeItems(isAdmin);
        final String kitroompre = Kitroom.getDbManager().getKitRoom(room);
        if (kitroompre != null) {
            final Inventory kitinv = KitRoom.serializeInventory.Deserialize(kitroompre);
            for (int i = 0; i < 45; ++i) {
                if (kitinv.getItem(i) != null) {
                    this.inv.setItem(i, kitinv.getItem(i));
                }
            }
        }
        p.openInventory(this.inv);
    }
    
    public void InitializeItems(final boolean isAdmin) {
        final ItemStack filler = KitRoom.createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        this.inv.setItem(46, filler);
        this.inv.setItem(52, filler);
        this.inv.setItem(47, filler);
        this.inv.setItem(51, filler);
        final ItemStack refill = KitRoom.createItem.CreateItem(Material.BEACON, "" + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + "REFILL");
        this.inv.setItem(45, refill);
        final ItemStack back = KitRoom.createItem.CreateItem(Material.OAK_DOOR, "" + ChatColor.RED + ChatColor.BOLD + "BACK");
        this.inv.setItem(53, back);
        final ItemStack armory = KitRoom.createItem.CreateItem(Material.POTION, ChatColor.GREEN + "Potions & Arrows");
        this.inv.setItem(50, armory);
        final ItemStack potion = KitRoom.createItem.CreateItem(Material.END_CRYSTAL, ChatColor.BLUE + "Crystal PVP");
        this.inv.setItem(49, potion);
        final ItemStack equipment = KitRoom.createItem.CreateItem(Material.NETHERITE_CHESTPLATE, ChatColor.AQUA + "Equipment");
        final ItemMeta eqipmentmeta = equipment.getItemMeta();
        eqipmentmeta.addEnchant(Enchantment.LUCK, 1, true);
        eqipmentmeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        equipment.setItemMeta(eqipmentmeta);
        this.inv.setItem(48, equipment);
        if (isAdmin) {
            final List<String> saveLore = new ArrayList<String>();
            saveLore.add(ChatColor.GRAY + "- Saves current kitroom");
            final ItemStack save = KitRoom.createItem.CreateItem(Material.LIME_DYE, "" + ChatColor.GREEN + ChatColor.BOLD + "SAVE", saveLore);
            this.inv.setItem(45, save);
        }
    }
    
    static {
        KitRoom.createItem = new CreateItem();
        KitRoom.serializeInventory = new SerializeInventory();
    }
}
