package com.Theeef.me.interaction.character;

import com.Theeef.me.characters.races.Race;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CharacterCreator {

    public static void newCharacter(Player player) {
        Inventory inventory = player.getServer().createInventory(null, (1 + (Race.values().size() / 9)) * 9, "Select A Race");

        for (Race race : Race.values())
            inventory.addItem(itemRacePreview(race));

        player.openInventory(inventory);
    }

    public static ItemStack itemRacePreview(Race race) {
        ItemStack item = new ItemStack(race.getDisplayMaterial(), 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + race.getName());
        List<String> lore = Util.fitForLore(race.getDescription());

        for (int i = 0; i < lore.size(); i++)
            lore.set(i, ChatColor.GRAY + lore.get(i));

        lore.add("");
        lore.add(ChatColor.RESET + "Left Click to select");
        lore.add(ChatColor.RESET + "Right Click for more info");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
