package fr.as2treffle.inventoryPlayerInputAddon.manager;

import fr.as2treffle.inventorybuilderapi.InventoryBuilderAPI;
import fr.as2treffle.inventorybuilderapi.itemstack.ItemStackBuilder;
import fr.as2treffle.inventorybuilderapi.manager.ActionsManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AnvilManager {

    @SuppressWarnings("all")
    public static void openAnvil(Player player, YamlConfiguration file, String id) {

        String title = "AnvilGUI Input";
        String text = "Default text";

        ItemStack left = new ItemStack(Material.PAPER);
        ItemStack right = new ItemStack(Material.AIR);

        Boolean prevent = false;

        if (file.contains("anvils." + id + ".title")) {

            title = PlaceholderAPI.setPlaceholders(player, file.getString("anvils." + id + ".title"));
            title = ChatColor.translateAlternateColorCodes('&', title);
        }

        if (file.contains("anvils." + id + ".text")) {

            text = PlaceholderAPI.setPlaceholders(player, file.getString("anvils." + id + ".text"));
            text = ChatColor.translateAlternateColorCodes('&', text);
        }

        if (file.contains("anvils." + id + ".prevent")) {

            prevent = file.getBoolean("anvils." + id + ".prevent");
        }

        if (file.contains("anvils." + id + ".left")) {
            String symbol = file.getString("anvils." + id + ".left");
            left = ItemStackBuilder.buildItemStack(file, symbol, player);
        }

        if (file.contains("anvils." + id + ".right")) {
            String symbol = file.getString("anvils." + id + ".right");
            right = ItemStackBuilder.buildItemStack(file, symbol, player);
        }

        AnvilGUI builder = new AnvilGUI.Builder()
                .text(text)
                .plugin(InventoryBuilderAPI.instance)
                .title(title)
                .itemLeft(left)
                .itemRight(right)
                .onClick((slot, stateSnapshot) -> {

                    if (slot == 2) {
                        String value = stateSnapshot.getText();

                        if (file.contains("anvils." + id + ".when-complete")) {

                            ArrayList<String> actions_before = (ArrayList<String>) file.getStringList("anvils." + id + ".when-complete");
                            ArrayList<String> actions = new ArrayList<>();

                            for (String action : actions_before) {
                                action = action.replace("%text%", value);
                                actions.add(action);
                            }

                            player.closeInventory();
                            ActionsManager.performsActions(file, player, actions, null);
                        }
                    }

                    return Collections.emptyList();
                })
                .open(player);
    }
}
