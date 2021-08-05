package com.Theeef.me.interaction.character;

import com.Theeef.me.api.common.choice.ChoiceResult;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChoiceMenuItem {

    private final ItemStack item;
    private final List<ChoiceResult> results; // The results the choice item is waiting to be completed

    public ChoiceMenuItem(ItemStack item, List<ChoiceResult> results) {
        this.item = item;
        this.results = results;
    }

    public ChoiceMenuItem(ItemStack item, ChoiceResult result) {
        this(item, Lists.newArrayList(result));
    }

    public ItemStack getItem() {
        ItemStack item = this.item.clone();

        if (!isComplete()) {
            ItemMeta meta = item.getItemMeta();

            if ((System.currentTimeMillis() / 1000) % 2 == 0) {
                item.setType(Material.BARRIER);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + ChatColor.stripColor(meta.getDisplayName()));
            }

            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

            if (lore.size() > 0)
                lore.add("");

            lore.add(ChatColor.RED + "A choice still needs to be made here");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    private boolean isComplete() {
        for (ChoiceResult result : this.results)
            if (!result.isComplete())
                return false;

        return true;
    }
}
