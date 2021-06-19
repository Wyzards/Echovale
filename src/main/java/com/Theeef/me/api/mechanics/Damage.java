package com.Theeef.me.api.mechanics;

import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONObject;

public class Damage {

    private final APIReference damage_type;
    private final DamageRoll roll;

    public Damage(JSONObject damage) {
        this.roll = new DamageRoll((String) damage.get("damage_dice"));
        this.damage_type = new APIReference((JSONObject) damage.get("damage_type"));
    }

    public int getMin() {
        return this.roll.getMin();
    }

    public int getMax() {
        return this.roll.getMax();
    }

    // Get methods
    public DamageType getType() {
        return new DamageType(this.damage_type.getUrl());
    }

    public DamageRoll getRoll() {
        return this.roll;
    }
}
