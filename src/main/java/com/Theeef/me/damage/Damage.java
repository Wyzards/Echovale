package com.Theeef.me.damage;

import org.json.simple.JSONObject;

public class Damage {

    private final String damage_type_url;
    private final DamageRoll roll;

    public Damage(JSONObject damage) {
        this.roll = new DamageRoll((String) damage.get("damage_dice"));
        this.damage_type_url = (String) ((JSONObject) damage.get("damage_type")).get("url");
    }

    public int getMin() {
        return this.roll.getMin();
    }

    public int getMax() {
        return this.roll.getMax();
    }

    public DamageType getType() {
        return new DamageType(this.damage_type_url);
    }
}
