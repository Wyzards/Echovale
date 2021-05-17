package com.Theeef.me.items.weapons;

import com.Theeef.me.api.equipment.DamageType;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDRangedWeapon extends DNDWeapon {

    private int longRange;
    private int closeRange;

    public DNDRangedWeapon(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight, int minDamage, int maxDamage, DamageType damageType, List<ItemProperty> properties, int closeRange, int longRange, WeaponType weaponType) {
        super(ID, name, material, amount, description, cost, weight, minDamage, maxDamage, damageType, properties, weaponType);

        if (!properties.contains(ItemProperty.AMMUNITION) && !properties.contains(ItemProperty.THROWN))
            throw new IllegalArgumentException("All Ranged weapons must have the ItemProperty: AMMUNITION or THROWN");

        this.longRange = longRange;
        this.closeRange = closeRange;
    }

    @Override
    public ItemStack getItem() {
        if (this instanceof DNDRangedVersatileWeapon)
            return super.getItem();

        // super.getItem() gets item from DNDItem, not DNDWeapon
        ItemStack item = super.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        lore.add("");
        lore.add(ChatColor.GRAY + "Type: " + ChatColor.WHITE + Util.cleanEnumName(getWeaponType().name()));
        lore.add(ChatColor.GRAY + "Range: " + ChatColor.WHITE + closeRange + "ft Normal - " + longRange + "ft Long");

        if (getMaxDamage() > 0 && getDamageType() != null)
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.WHITE + getMinDamage() + "-" + getMaxDamage() + " " + getDamageType().getName().toLowerCase());

        if (getProperties().size() > 0) {
            List<String> propertyList = Util.fitForLore(propertyList());

            if (propertyList.size() > 1)
                for (int i = 1; i < propertyList.size(); i++)
                    propertyList.set(i, ChatColor.WHITE + propertyList.get(i));

            lore.addAll(propertyList);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public int getCloseRange() {
        return closeRange;
    }

    public int getLongRange() {
        return longRange;
    }
}
