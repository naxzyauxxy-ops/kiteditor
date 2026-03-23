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

public class CommandK implements CommandExecutor
{
    private static SerializeInventory serializeInventory;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        final Player p = (Player)sender;
        final String commandName = command.getName().toLowerCase();
        if (commandName.startsWith("k")) {
            final int kitNumber = Integer.parseInt(commandName.substring(1));
            final String serializedInv = Kitroom.getDbManager().getKit(p, "k" + kitNumber);
            if (serializedInv == null) {
                p.sendMessage("kit doesnt exist");
                return true;
            }
            final Inventory inv = CommandK.serializeInventory.Deserialize(serializedInv);
            for (int i = 0; i < 41; ++i) {
                p.getInventory().setItem(i, inv.getItem(i));
            }
            p.sendMessage("" + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + "Loaded Kit");
        }
        else {
            p.sendMessage("" + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + "Kit Doesnt Exist");
        }
        return true;
    }
    
    static {
        CommandK.serializeInventory = new SerializeInventory();
    }
}
