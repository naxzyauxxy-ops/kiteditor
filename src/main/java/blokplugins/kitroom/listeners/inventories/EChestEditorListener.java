// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.listeners.inventories;

import org.bukkit.inventory.Inventory;
import blokplugins.kitroom.inventories.EChestEditor;
import blokplugins.kitroom.inventories.KitMenu;
import blokplugins.kitroom.Kitroom;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import blokplugins.kitroom.utils.SerializeInventory;

public class EChestEditorListener
{
    private static SerializeInventory serializeInventory;
    
    public static void handleInventoryClick(final InventoryClickEvent e) {
        final int slot = e.getRawSlot();
        final Player p = (Player)e.getWhoClicked();
        final String title = e.getView().getTitle();
        final String lastLetter = title.substring(title.length() - 1);
        if (slot < 27) {
            return;
        }
        if (slot < 32) {
            e.setCancelled(true);
            return;
        }
        switch (slot) {
            case 32: {
                final Inventory saveinv = e.getClickedInventory();
                for (int i = 27; i < saveinv.getSize(); ++i) {
                    saveinv.setItem(i, (ItemStack)null);
                }
                Kitroom.getDbManager().saveKit(p, "ec" + lastLetter, EChestEditorListener.serializeInventory.Serialze(saveinv));
                new KitMenu(p);
                e.setCancelled(true);
                break;
            }
            case 33: {
                new EChestEditor(p, Integer.valueOf(lastLetter), p.getEnderChest());
                e.setCancelled(true);
                break;
            }
            case 34: {
                if (!e.isShiftClick()) {
                    e.setCancelled(true);
                    break;
                }
                Kitroom.getDbManager().saveKit(p, "ec" + lastLetter, (String)null);
                new EChestEditor(p, Integer.valueOf(lastLetter), null);
                e.setCancelled(true);
                break;
            }
            case 35: {
                new KitMenu(p);
                e.setCancelled(true);
                break;
            }
        }
    }
    
    static {
        EChestEditorListener.serializeInventory = new SerializeInventory();
    }
}
