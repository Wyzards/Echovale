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

    private ContainerType containerType;
    private String containerLabel;
    private DNDItem[] items;

    public DNDContainerItem(ContainerType type, int amount, String description, MoneyAmount cost, String containerLabel, DNDItem... items) {
        super(type.getID(), type.getName(), type.getAppearance(), amount, description, cost, type.getWeight(), type.getItemType());

        if (items.length > containerType.getSlots())
            throw new IllegalArgumentException("More items provided in ContainerItem constructor than ContainerType can hold");
    }

    public DNDContainerItem(ContainerType type, String containerLabel, int goldCost, DNDItem... items) {
        this(type, 1, null, MoneyAmount.fromGold(goldCost), containerLabel, items);
    }

    public DNDContainerItem(ContainerType type, String containerLabel, String description, DNDItem... items) {
        this(type, 1, description, type.getCost(), containerLabel, items);
    }

    public DNDContainerItem(ContainerType type, String containerLabel, DNDItem... items) {
        this(type, 1, null, type.getCost(), containerLabel, items);
    }

    public DNDContainerItem(ContainerType type, DNDItem... items) {
        this(type, null, items);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = super.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.GRAY + "Max Weight: " + ChatColor.WHITE + this.containerType.getMaxWeight() + " pounds");

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

    public String getContainerLabel() {
        return this.containerLabel;
    }

    public DNDItem[] getItems() {
        return this.items;
    }
}
