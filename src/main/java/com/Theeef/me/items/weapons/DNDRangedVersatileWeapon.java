package com.Theeef.me.items.weapons;

import com.Theeef.me.api.equipment.DamageType;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDRangedVersatileWeapon extends DNDRangedWeapon {

    private final int twoHandedMinDamage;
    private final int twoHandedMaxDamage;

    public DNDRangedVersatileWeapon(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight, int minDamage, int maxDamage, DamageType damageType, List<ItemProperty> properties, int closeRange, int longRange, int twoHandedMinDamage, int twoHandedMaxDamage, WeaponType weaponType) {
        super(ID, name, material, amount, description, cost, weight, minDamage, maxDamage, damageType, properties, closeRange, longRange, weaponType);

        this.twoHandedMinDamage = twoHandedMinDamage;
        this.twoHandedMaxDamage = twoHandedMaxDamage;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = super.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        lore.add("");
        lore.add(ChatColor.GRAY + "Type: " + ChatColor.WHITE + Util.cleanEnumName(getWeaponType().name()));
        if (getMaxDamage() > 0 && getDamageType() != null)
            lore.add(ChatColor.GRAY + "One-Handed/Thrown Damage: " + ChatColor.WHITE + getMinDamage() + "-" + getMaxDamage() + " " + getDamageType().getName().toLowerCase());
        if (twoHandedMaxDamage > 0 && getDamageType() != null)
            lore.add(ChatColor.GRAY + "Two-Handed Damage: " + ChatColor.WHITE + twoHandedMinDamage + "-" + twoHandedMaxDamage + " " + getDamageType().getName().toLowerCase());

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
}
