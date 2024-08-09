package fr.as2treffle.inventoryPlayerInputAddon;

import fr.as2treffle.inventoryPlayerInputAddon.addon.InventoryBuilderPLAddon;
import fr.as2treffle.inventorybuilderapi.manager.AddonManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class InventoryPlayerInputAddon extends JavaPlugin {

    @Override
    public void onEnable() {
        AddonManager.registerAddon(this, new InventoryBuilderPLAddon());
    }

    @Override
    public void onDisable() {

    }
}
