// 
// Decompiled by Procyon v0.6.0
// 

package blokplugins.kitroom.commands;

import blokplugins.kitroom.inventories.KitRoom;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandKitadmin implements CommandExecutor
{
    public boolean onCommand(@NotNull final CommandSender commandSender, @NotNull final Command command, @NotNull final String s, @NotNull final String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("you must be a player to do this");
            return true;
        }
        final Player p = (Player)commandSender;
        if (!p.hasPermission("kitroom.admin")) {
            p.sendMessage("You do not have permission for this");
            return true;
        }
        new KitRoom(p, "pvp", true);
        return true;
    }
}
