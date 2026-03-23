package dev.kiteditor;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KitEditorCommand implements CommandExecutor {

    private final KitEditorBedrock plugin;

    public KitEditorCommand(KitEditorBedrock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("kitselector")) {
            giveSelector(player);
            return true;
        }

        // /kiteditor <1-9>
        if (args.length == 0) {
            player.sendMessage("§7[§5Kits§7] §cUsage: /kiteditor <1-9>");
            return true;
        }

        int kitNum;
        try {
            kitNum = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("§7[§5Kits§7] §cPlease enter a number between 1 and 9.");
            return true;
        }

        if (kitNum < 1 || kitNum > 9) {
            player.sendMessage("§7[§5Kits§7] §cPlease enter a number between 1 and 9.");
            return true;
        }

        // Store which kit they want to edit, then open the kit menu
        plugin.getPendingEdits().put(player.getUniqueId(), kitNum);
        player.sendMessage("§7[§5Kits§7] §eOpening editor for §b§lKit " + kitNum + "§e...");
        player.performCommand("kit");
        return true;
    }

    private void giveSelector(Player player) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§5§lKit Selector");
        meta.setLore(Arrays.asList(
            "§7Sneak + Right-Click §f→ Cycle Kit Slot",
            "§7Left-Click §f→ Load Selected Kit",
            "§7Right-Click §f→ Edit Selected Kit"
        ));
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
        player.sendMessage("§7[§5Kits§7] §aKit selector added to your inventory!");
    }
}
