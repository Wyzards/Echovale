package com.Theeef.me.items;

import com.Theeef.me.combat.damage.DamageType;
import org.bukkit.Material;

import java.util.List;

public class DNDWeapon extends DNDItem {

    private int minDamage;
    private int maxDamage;
    private List<ItemProperty> properties;
    private DamageType damageType;

    public DNDWeapon(String ID, String name, Material material, String description, MoneyAmount cost, double weight, int minDamage, int maxDamage, DamageType damageType, List<ItemProperty> properties) {
        super(ID, name, material, description, cost, weight);

        if ((properties.contains(ItemProperty.AMMUNITION) || properties.contains(ItemProperty.THROWN)) && !(this instanceof DNDRangedWeapon))
            throw new IllegalArgumentException("Non-ranged weapons cannot have the ItemProperty AMMUNITION or THROWN");

        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.damageType = damageType;
        this.properties = properties;
    }

    public enum ItemProperty {
        AMMUNITION, FINESSE, HEAVY, LIGHT, LOADING, REACH, SPECIAL, THROWN, TWO_HANDED, VERSATILE, IMPROVISED, SILVERED;
    }

}
