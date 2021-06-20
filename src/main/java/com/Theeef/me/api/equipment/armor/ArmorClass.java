package com.Theeef.me.api.equipment.armor;

import org.json.simple.JSONObject;

public class ArmorClass {

    private final long base;
    private final boolean dex_bonus;
    private final long max_bonus;

    public ArmorClass(JSONObject json) {
        this.base = (long) json.get("base");
        this.dex_bonus = (boolean) json.get("dex_bonus");

        if (json.get("max_bonus") == null)
            this.max_bonus = 0;
        else
            this.max_bonus = (long) json.get("max_bonus");
    }

    // Getter methods
    public long getBase() {
        return this.base;
    }

    public boolean hasDexBonus() {
        return this.dex_bonus;
    }

    public long getMaxDexBonus() {
        return this.max_bonus;
    }
}