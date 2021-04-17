package com.Theeef.me.items;

import com.Theeef.me.combat.damage.DamageType;
import org.bukkit.Material;

import java.util.List;

public class DNDRangedWeapon extends DNDWeapon {

    private int longRange;
    private int closeRange;

    public DNDRangedWeapon(String ID, String name, Material material, String description, MoneyAmount cost, double weight, int minDamage, int maxDamage, DamageType damageType, List<ItemProperty> properties, int longRange, int closeRange) {
        super(ID, name, material, description, cost, weight, minDamage, maxDamage, damageType, properties);

        this.longRange = longRange;
        this.closeRange = closeRange;
    }
}
