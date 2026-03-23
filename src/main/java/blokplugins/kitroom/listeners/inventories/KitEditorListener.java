// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.listeners.inventories;

import org.bukkit.inventory.Inventory;
import blokplugins.kitroom.inventories.KitEditor;
import blokplugins.kitroom.inventories.KitMenu;
import blokplugins.kitroom.Kitroom;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import blokplugins.kitroom.utils.SerializeInventory;

public class KitEditorListener
{
    private static SerializeInventory serializeInventory;
    
    public static void handleInventoryClick(final InventoryClickEvent e) {
        final int slot = e.getRawSlot();
        final Player p = (Player)e.getWhoClicked();
        final String title = e.getView().getTitle();
        final String lastLetter = title.substring(title.length() - 1);
        if (slot < 41) {
            return;
        }
        if (slot < 50) {
            e.setCancelled(true);
            return;
        }
        switch (slot) {
            case 50: {
                final Inventory saveinv = e.getClickedInventory();
                for (int i = 41; i < saveinv.getSize(); ++i) {
                    saveinv.setItem(i, (ItemStack)null);
                }
                Kitroom.getDbManager().saveKit(p, "k" + lastLetter, KitEditorListener.serializeInventory.Serialze(saveinv));
                new KitMenu(p);
                e.setCancelled(true);
                break;
            }
            case 51: {
                new KitEditor(p, Integer.valueOf(lastLetter), (Inventory)p.getInventory());
                e.setCancelled(true);
                break;
            }
            case 52: {
                if (!e.isShiftClick()) {
                    e.setCancelled(true);
                    break;
                }
                Kitroom.getDbManager().saveKit(p, "k" + lastLetter, (String)null);
                new KitEditor(p, Integer.valueOf(lastLetter), null);
                e.setCancelled(true);
                break;
            }
            case 53: {
                new KitMenu(p);
                e.setCancelled(true);
                break;
            }
        }
    }
    
    static {
        KitEditorListener.serializeInventory = new SerializeInventory();
    }
}
