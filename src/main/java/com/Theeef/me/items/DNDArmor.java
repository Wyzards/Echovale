package com.Theeef.me.items;

import org.bukkit.Material;

public class DNDArmor extends DNDItem {

    private int armorClass;
    private int dexCap;
    private int strengthRequirement;
    private boolean stealthDisadvantage;
    private ArmorType type;

    public DNDArmor(String ID, String name, Material material, MoneyAmount cost, double weight, int armorClass, int dexCap, int strengthRequirement, boolean stealthDisadvantage, ArmorType type) {
        super(ID, name, material, 1, null, cost, weight);

        this.armorClass = armorClass;
        this.dexCap = dexCap;
        this.strengthRequirement = strengthRequirement;
        this.stealthDisadvantage = stealthDisadvantage;
        this.type = type;
    }

    public enum ArmorType {
        LIGHT, MEDIUM, HEAVY;
    }
}
