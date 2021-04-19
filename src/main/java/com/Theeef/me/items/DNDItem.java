package com.Theeef.me.items;

import com.Theeef.me.characters.classes.equipment.EquipmentChoice;
import com.Theeef.me.characters.classes.equipment.IEquipmentChoice;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDItem implements Cloneable, IEquipmentChoice {

    private String ID;
    private String name;
    private Material material;
    private String description;
    private double weight;
    private MoneyAmount cost;
    private int amount;

    public DNDItem(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight) {
        this.ID = ID;
        this.name = name;
        this.material = material;
        this.amount = amount;
        this.description = description;
        this.cost = cost;
        this.weight = weight;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(this.material, this.amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + name);
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Cost: " + cost.amountString(), ChatColor.GRAY + "Weight: " + weight + " pounds");

        if (description != null) {
            lore.add("");
            lore.addAll(Util.fitForLore(description));
        }

        for (int i = 0; i < lore.size(); i++)
            lore.set(i, ChatColor.GRAY + lore.get(i));

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "itemID", ID);
    }

    public int getAmount() {
        return this.amount;
    }

    public DNDItem getAmount(int amount) {
        try {
            DNDItem item = (DNDItem) this.clone();
            item.amount = amount;

            return item;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Was not able to modify DNDItem's amount");
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

    @Override
    public List<DNDItem> getChoice() {
        return Lists.newArrayList(this);
    }
}
