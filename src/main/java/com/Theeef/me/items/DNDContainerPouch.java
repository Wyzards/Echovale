package com.Theeef.me.items;

import com.Theeef.me.util.NBTHandler;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDContainerPouch extends DNDContainerItem {
    public DNDContainerPouch(int amount, String containerLabel, DNDItem... item) {
        super("POUCH", "Pouch", Material.FLOWER_POT, amount, null, MoneyAmount.fromSilver(5), 1, 6, 1 / 5., containerLabel, item);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial(), getAmount());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getContainerLabel() == null ? getName() : getContainerLabel());
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Max Weight: " + ChatColor.WHITE + getWeightCapacity() + " pounds");

        if (hasItems()) {
            lore.add("");
            lore.add(ChatColor.GRAY + "Contains:");

            DNDItem[] items = getItems();

            for (int i = 0; i < Math.min(i + 10, items.length); i++) {
                if (items[i] != null)
                    lore.add(ChatColor.GRAY + "- " + items[i].getName() + " x" + items[i].getAmount());
                if (i == 9 && items.length > 9)
                    lore.add(ChatColor.GRAY + "- " + (items.length - 9) + " more items...");
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        DNDItem[] items = getItems();
        for (int i = 0; i < items.length; i++)
            NBTHandler.addString(item, "container_" + i, items[i] == null ? "" : items[i].getID() + ":" + items[i].getAmount());

        return NBTHandler.addString(item, "itemID", getID());
    }
}
