package dev.kiteditor;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KitEditorBedrock extends JavaPlugin {

    // uuid → kit number they want to edit next time the menu opens
    private final Map<UUID, Integer> pendingEdits = new HashMap<>();
    // uuid → currently selected kit slot for the selector item
    private final Map<UUID, Integer> selectedKit = new HashMap<>();

    public Map<UUID, Integer> getPendingEdits() { return pendingEdits; }
    public Map<UUID, Integer> getSelectedKit()  { return selectedKit; }

    @Override
    public void onEnable() {
        KitEditorCommand cmd = new KitEditorCommand(this);
        getCommand("kiteditor").setExecutor(cmd);
        getCommand("kitselector").setExecutor(cmd);
        getServer().getPluginManager().registerEvents(new KitMenuListener(this), this);
        getLogger().info("KitEditorBedrock enabled.");
    }
}
