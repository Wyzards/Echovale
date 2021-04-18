package com.Theeef.me.items;

import com.Theeef.me.combat.damage.DamageType;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDRangedWeapon extends DNDWeapon {

    private int longRange;
    private int closeRange;

    public DNDRangedWeapon(String ID, String name, Material material, String description, MoneyAmount cost, double weight, int minDamage, int maxDamage, DamageType damageType, List<ItemProperty> properties, int closeRange, int longRange, WeaponType weaponType) {
        super(ID, name, material, description, cost, weight, minDamage, maxDamage, damageType, properties, weaponType);

        if (!properties.contains(ItemProperty.AMMUNITION) && !properties.contains(ItemProperty.THROWN))
            throw new IllegalArgumentException("All Ranged weapons must have the ItemProperty: AMMUNITION or THROWN");

        this.longRange = longRange;
        this.closeRange = closeRange;
    }

    @Override
    public ItemStack getItem(int amount) {
        ItemStack item = new ItemStack(getMaterial(), amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Damage: " + ChatColor.WHITE + getMinDamage() + "-" + getMaxDamage() + " " + getDamageType().name().toLowerCase(), ChatColor.GRAY + "Range: " + ChatColor.WHITE + closeRange + "ft Normal Range - " + longRange + "ft Long Range", ChatColor.GRAY + "Type: " + ChatColor.RESET + Util.cleanEnumName(getWeaponType().name()), "", ChatColor.GRAY + "Cost: " + ChatColor.WHITE + getCost().amountString(), ChatColor.GRAY + "Weight: " + getWeight() + " pounds");

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

        item.setItemMeta(meta);

        return NBTHandler.addString(item, "itemID", getID());
    }

    public int getCloseRange() {
        return closeRange;
    }

    public int getLongRange() {
        return longRange;
    }
}
