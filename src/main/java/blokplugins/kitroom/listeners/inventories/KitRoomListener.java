// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.listeners.inventories;

import org.bukkit.inventory.Inventory;
import blokplugins.kitroom.inventories.KitMenu;
import blokplugins.kitroom.Kitroom;
import org.bukkit.inventory.ItemStack;
import blokplugins.kitroom.inventories.KitRoom;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import blokplugins.kitroom.utils.SerializeInventory;

public class KitRoomListener
{
    private static SerializeInventory serializeInventory;
    
    public static void handleInventoryClick(final InventoryClickEvent e) {
        final int slot = e.getRawSlot();
        final Player p = (Player)e.getWhoClicked();
        final ItemStack adminItem = e.getClickedInventory().getItem(45);
        switch (slot) {
            case 46:
            case 47:
            case 51:
            case 52: {
                e.setCancelled(true);
                break;
            }
            case 45: {
                final ItemStack clickedItem = e.getCurrentItem();
                if (clickedItem.getType() == Material.BEACON) {
                    new KitRoom(p, e.getView().getTitle(), false);
                }
                if (clickedItem.getType() == Material.LIME_DYE) {
                    final Inventory saveinv = e.getClickedInventory();
                    for (int i = 45; i < saveinv.getSize(); ++i) {
                        saveinv.setItem(i, (ItemStack)null);
                    }
                    if (p.hasPermission("kitroom.adminkitroom.admin")) {
                        Kitroom.getDbManager().saveKitRoom(e.getView().getTitle(), KitRoomListener.serializeInventory.Serialze(saveinv));
                    }
                    new KitRoom(p, e.getView().getTitle(), true);
                }
                e.setCancelled(true);
                break;
            }
            case 48: {
                if (adminItem.getType() == Material.LIME_DYE) {
                    new KitRoom(p, "equipment", true);
                }
                else {
                    new KitRoom(p, "equipment", false);
                }
                e.setCancelled(true);
                break;
            }
            case 49: {
                if (adminItem.getType() == Material.LIME_DYE) {
                    new KitRoom(p, "pvp", true);
                }
                else {
                    new KitRoom(p, "pvp", false);
                }
                e.setCancelled(true);
                break;
            }
            case 50: {
                if (adminItem.getType() == Material.LIME_DYE) {
                    new KitRoom(p, "potions", true);
                }
                else {
                    new KitRoom(p, "potions", false);
                }
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
        KitRoomListener.serializeInventory = new SerializeInventory();
    }
}
