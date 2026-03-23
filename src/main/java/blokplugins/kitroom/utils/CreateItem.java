// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.utils;

import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class CreateItem
{
    public ItemStack CreateItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;
    }
    
    public ItemStack CreateItem(final Material material, final String name, final List<String> lore) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore((List)lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
