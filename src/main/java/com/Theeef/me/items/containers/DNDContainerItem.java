package com.Theeef.me.items.containers;

import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.util.NBTHandler;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDContainerItem extends DNDItem {

    private double weightCapacity;
    private double volume;
    private String containerLabel;
    private DNDItem[] items;

    public DNDContainerItem(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight, double weightCapacity, double volume, String containerLabel, DNDItem[] items, ItemType... type) {
        super(ID, name, material, amount, description, cost, weight, type);

        this.weightCapacity = weightCapacity;
        this.volume = volume;
        this.containerLabel = containerLabel;
        this.items = items;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = super.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.GRAY + "Max Weight: " + ChatColor.WHITE + getWeightCapacity() + " pounds");

        if (getContainerLabel() != null)
            meta.setDisplayName(getContainerLabel());

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

        return item;
    }

    public boolean hasItems() {
        for (DNDItem item : items)
            if (item != null)
                return true;

        return false;
    }

    public double getWeightCapacity() {
        return this.weightCapacity;
    }

    public double getVolume() {
        return this.volume;
    }

    public String getContainerLabel() {
        return this.containerLabel;
    }

    public DNDItem[] getItems() {
        return this.items;
    }
}
