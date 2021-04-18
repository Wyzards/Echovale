package com.Theeef.me.items;

import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDItem {

    private String ID;
    private String name;
    private Material material;
    private String description;
    private double weight;
    private MoneyAmount cost;

    public DNDItem(String ID, String name, Material material, String description, MoneyAmount cost, double weight) {
        this.ID = ID;
        this.name = name;
        this.material = material;
        this.description = description;
        this.cost = cost;
        this.weight = weight;
    }

    public ItemStack getItem(int amount) {
        ItemStack item = new ItemStack(this.material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Cost: " + cost.amountString(), ChatColor.GRAY + "Weight: " + weight + " pounds");

        if (description != null) {
            lore.add("");
            lore.addAll(Util.fitForLore(description));
        }

        for (int i = 0; i < lore.size(); i++)
            lore.set(i, ChatColor.GRAY + lore.get(i));

        item.setItemMeta(meta);

        return NBTHandler.addString(item, "itemID", ID);
    }

    public double getWeight() {
        return weight;
    }

    public String getID() {
        return ID;
    }

    public MoneyAmount getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }
}
