package fr.as2treffle.inventoryPlayerInputAddon.addon;

import fr.as2treffle.inventoryPlayerInputAddon.manager.AnvilManager;
import fr.as2treffle.inventoryPlayerInputAddon.manager.SignManager;
import fr.as2treffle.inventorybuilderapi.InventoryBuilderAPI;
import fr.as2treffle.inventorybuilderapi.manager.Addon;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class InventoryBuilderPLAddon implements Addon {
    @Override
    public void performAction(Player player, Inventory inventory, ClickType clickType, ItemStack itemStack, String action, String s, Integer slot) {

        if (action.equals("openSign")) {

            YamlConfiguration config = InventoryBuilderAPI.inv_opened.get(player.getUniqueId());
            String[] args = s.split(", ");

            if (config.contains("signs." + args[0])) {
                player.closeInventory();
                SignManager.openSign(player, config, args[0]);
            }
        }

        if (action.equals("openAnvil")) {

            YamlConfiguration config = InventoryBuilderAPI.inv_opened.get(player.getUniqueId());
            String[] args = s.split(", ");

            if (config.contains("anvils." + args[0])) {
                player.closeInventory();
                AnvilManager.openAnvil(player, config, args[0]);
            }
        }
    }

    @Override
    public boolean checkCondition(Player player, Inventory inventory, String s, String s1, Integer integer) {
        return false;
    }

    @Override
    public ItemStack getCustomItemStack(Player player, Inventory inventory, ClickType clickType, ItemStack itemStack, String s, String s1) {
        return null;
    }

    @Override
    public ArrayList<HashMap<String, Object>> getCustomList(Player player, Inventory inventory, String s, String s1) {
        return null;
    }

    @Override
    public List<String> getActions() {
        java.util.List<String> actions = new ArrayList<>(java.util.List.of(
                "openSign=<id>",
                "openAnvil=<id>"
        ));

        Collections.sort(actions);
        return actions;
    }

    @Override
    public List<String> getConditions() {
        return List.of();
    }

    @Override
    public List<String> getItemStackMethods() {
        return List.of();
    }

    @Override
    public List<String> getListMethods() {
        return List.of();
    }
}
