package fr.as2treffle.inventoryPlayerInputAddon.manager;

import de.rapha149.signgui.SignGUI;
import fr.as2treffle.inventorybuilderapi.InventoryBuilderAPI;
import fr.as2treffle.inventorybuilderapi.manager.ActionsManager;
import fr.as2treffle.inventorybuilderapi.manager.ErrorManager;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class SignManager {

    @SuppressWarnings("all")
    public static void openSign(Player player, YamlConfiguration file, String id) {

        Material type = Material.OAK_SIGN;

        if (file.contains("signs." + id + ".type")) {
            String sign_type = file.getString("signs." + id + ".type");

            if (isASign(sign_type.toUpperCase()) && ErrorManager.isAnMaterial(sign_type.toUpperCase())) {
                type = Material.valueOf(sign_type);
            }
        }

        DyeColor color = DyeColor.BLACK;

        if (file.contains("signs." + id + ".text-color")) {
            String c = file.getString("signs." + id + ".text-color");

            if (isADyeColor(c.toUpperCase())) {
                color = DyeColor.valueOf(c.toUpperCase());
            }
        }

        ArrayList<String> lines = new ArrayList<>();

        if (file.contains("signs." + id + ".lines")) {
            lines = (ArrayList<String>) file.getStringList("signs." + id + ".lines");
        }
        else {
            for (int i = 0; i < 4; i++) {
                lines.add(" ");
            }
        }

        SignGUI sign = SignGUI.builder()
                .setColor(color)
                .setLines(lines.get(0), lines.get(1), lines.get(2), lines.get(3))
                .setType(type)
                .callHandlerSynchronously(InventoryBuilderAPI.instance)
                .setHandler((p, result) -> {

                    String[] lines_result = result.getLinesWithoutColor();

                    if (file.contains("signs." + id + ".when-complete")) {

                        ArrayList<String> actions_before = (ArrayList<String>) file.getStringList("signs." + id + ".when-complete");
                        ArrayList<String> actions = new ArrayList<>();

                        for (String action : actions_before) {
                            action = action.replace("%line-0%", lines_result[0]);
                            action = action.replace("%line-1%", lines_result[1]);
                            action = action.replace("%line-2%", lines_result[2]);
                            action = action.replace("%line-3%", lines_result[3]);
                            actions.add(action);
                        }

                        ActionsManager.performsActions(file, player, actions, null);

                    }

                    return Collections.emptyList();
                })
                .build();

        sign.open(player);
    }

    private static Boolean isASign(String s) {
        return s.endsWith("_SIGN");
    }

    private static Boolean isADyeColor(String s) {
        for (DyeColor color : DyeColor.values()) {
            if (Objects.equals(color.toString(), s)) {
                return true;
            }
        }

        return false;
    }
}
