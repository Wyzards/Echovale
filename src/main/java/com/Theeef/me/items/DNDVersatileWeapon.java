package com.Theeef.me.items;

import com.Theeef.me.combat.damage.DamageType;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDVersatileWeapon extends DNDWeapon {

    private int twoHandedMinDamage;
    private int twoHandedMaxDamage;

    public DNDVersatileWeapon(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight, int minDamage, int maxDamage, DamageType damageType, List<ItemProperty> properties, int twoHandedMinDamage, int twoHandedMaxDamage, WeaponType weaponType) {
        super(ID, name, material, amount, description, cost, weight, minDamage, maxDamage, damageType, properties, weaponType);

        if (!properties.contains(ItemProperty.VERSATILE))
            throw new IllegalArgumentException("All Versatile weapons must have the ItemProperty: VERSATILE");

        this.twoHandedMinDamage = twoHandedMinDamage;
        this.twoHandedMaxDamage = twoHandedMaxDamage;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial(), getAmount());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + getName());
        List<String> lore = Lists.newArrayList();

        if (getMaxDamage() > 0 && getDamageType() != null)
            lore.add(ChatColor.GRAY + "One-Handed Damage: " + ChatColor.WHITE + getMinDamage() + "-" + getMaxDamage() + " " + getDamageType().name().toLowerCase());
        if (twoHandedMaxDamage > 0 && getDamageType() != null)
            lore.add(ChatColor.GRAY + "Two-Handed Damage: " + ChatColor.WHITE + twoHandedMinDamage + "-" + twoHandedMaxDamage + " " + getDamageType().name().toLowerCase());
        lore.add(ChatColor.GRAY + "Type: " + ChatColor.WHITE + Util.cleanEnumName(getWeaponType().name()));
        lore.add("");
        lore.add(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + getCost().amountString());
        lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + getWeight() + " pounds");

        if (getDescription() != null) {
            lore.add("");
            lore.addAll(Util.fitForLore(getDescription()));
        }

        for (int i = 0; i < lore.size(); i++)
            lore.set(i, ChatColor.GRAY + lore.get(i));

        if (getProperties().size() > 0) {
            lore.add("");
            List<String> propertyList = Util.fitForLore(propertyList());
            if (propertyList.size() > 1)
                for (int i = 1; i < propertyList.size(); i++)
                    propertyList.set(i, ChatColor.WHITE + propertyList.get(i));
        }

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "itemID", getID());
    }
}