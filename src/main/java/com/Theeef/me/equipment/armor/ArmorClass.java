package com.Theeef.me.equipment.armor;

import org.json.simple.JSONObject;

public class ArmorClass {

    private final int base;
    private final boolean dex_bonus;
    private final int max_bonus;

    public ArmorClass(JSONObject json) {
        this.base = (int) json.get("base");
        this.dex_bonus = (boolean) json.get("dex_bonus");

        if (json.get("max_bonus") == null)
            this.max_bonus = 0;
        else
            this.max_bonus = (int) json.get("max_bonus");
    }

    public int getBase() {
        return this.base;
    }

    public boolean hasDexBonus() {
        return this.dex_bonus;
    }

    public int getMaxDexBonus() {
        return this.max_bonus;
    }
}
