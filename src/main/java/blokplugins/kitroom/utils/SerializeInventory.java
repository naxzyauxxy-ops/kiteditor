// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.utils;

import java.io.IOException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import java.io.InputStream;
import org.bukkit.util.io.BukkitObjectInputStream;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.io.OutputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.inventory.Inventory;

public class SerializeInventory
{
    public String Serialze(final Inventory inv) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream((OutputStream)outputStream);
            dataOutput.writeInt(inv.getSize());
            for (int i = 0; i < inv.getSize(); ++i) {
                dataOutput.writeObject((Object)inv.getItem(i));
            }
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Inventory Deserialize(final String input) {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(input));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream);
            final int size = dataInput.readInt();
            final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54);
            for (int i = 0; i < size; ++i) {
                inventory.setItem(i, (ItemStack)dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        }
        catch (final ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
