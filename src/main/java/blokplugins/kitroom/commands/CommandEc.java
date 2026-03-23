// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.commands;

import org.bukkit.inventory.Inventory;
import org.bukkit.ChatColor;
import blokplugins.kitroom.Kitroom;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.command.CommandExecutor;

public class CommandEc implements CommandExecutor
{
    private static SerializeInventory serializeInventory;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        final Player p = (Player)sender;
        final String commandName = command.getName().toLowerCase();
        if (commandName.startsWith("ec")) {
            final int kitNumber = Integer.parseInt(commandName.substring(2));
            final String serializedInv = Kitroom.getDbManager().getKit(p, "ec" + kitNumber);
            if (serializedInv == null) {
                p.sendMessage("ec doesnt exist");
                return true;
            }
            final Inventory inv = CommandEc.serializeInventory.Deserialize(serializedInv);
            for (int i = 0; i < 27; ++i) {
                p.getEnderChest().setItem(i, inv.getItem(i));
            }
            p.sendMessage("" + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + "Loaded Ender Chest");
        }
        else {
            p.sendMessage("" + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + "Ender Chest Doesnt Exist");
        }
        return true;
    }
    
    static {
        CommandEc.serializeInventory = new SerializeInventory();
    }
}
