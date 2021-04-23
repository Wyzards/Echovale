package com.Theeef.me.items.weapons;

import com.Theeef.me.combat.damage.DamageType;
import com.Theeef.me.items.*;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDWeapon extends DNDItem {

    private int minDamage;
    private int maxDamage;
    private List<ItemProperty> properties;
    private DamageType damageType;
    private WeaponType weaponType;

    public DNDWeapon(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight, int minDamage, int maxDamage, DamageType damageType, List<ItemProperty> properties, WeaponType weaponType) {
        super(ID, name, material, amount, description, cost, weight, ItemType.WEAPON);

        if ((properties.contains(ItemProperty.AMMUNITION) || properties.contains(ItemProperty.THROWN)) && !(this instanceof DNDRangedWeapon))
            throw new IllegalArgumentException("Non-ranged weapons cannot have the ItemProperty AMMUNITION or THROWN");

        if (properties.contains(ItemProperty.VERSATILE) && !(this instanceof DNDVersatileWeapon) && !(this instanceof DNDRangedVersatileWeapon))
            throw new IllegalArgumentException("Non-versatile weapons cannot have the ItemProperty: VERSATILE");

        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.damageType = damageType;
        this.properties = properties;
        this.weaponType = weaponType;
    }

    @Override
    public ItemStack getItem() {
        if (this instanceof DNDRangedWeapon || this instanceof DNDVersatileWeapon)
            return super.getItem();

        ItemStack item = super.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        lore.add("");
        lore.add(ChatColor.GRAY + "Type: " + ChatColor.WHITE + Util.cleanEnumName(getWeaponType().name()));

        if (getMaxDamage() > 0 && getDamageType() != null)
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.WHITE + getMinDamage() + "-" + getMaxDamage() + " " + getDamageType().name().toLowerCase());

        if (properties.size() > 0) {
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

    public String propertyList() {
        String string = ChatColor.GRAY + "Properties: " + ChatColor.WHITE;

        for (int i = 0; i < properties.size(); i++)
            if (i < properties.size() - 1)
                string += Util.cleanEnumName(properties.get(i).name()) + ", ";
            else
                string += Util.cleanEnumName(properties.get(i).name());

        return string;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public List<ItemProperty> getProperties() {
        return properties;
    }

    public enum ItemProperty {
        AMMUNITION, FINESSE, HEAVY, LIGHT, LOADING, REACH, SPECIAL, THROWN, TWO_HANDED, VERSATILE, IMPROVISED, SILVERED;
    }

    public enum WeaponType {
        SIMPLE_MELEE, SIMPLE_RANGED, MARTIAL_MELEE, MARTIAL_RANGED;
    }
}
