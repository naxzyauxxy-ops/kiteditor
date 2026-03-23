// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.commands;

import blokplugins.kitroom.inventories.KitMenu;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandKit implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        final Player p = (Player)sender;
        new KitMenu(p);
        return true;
    }
}
