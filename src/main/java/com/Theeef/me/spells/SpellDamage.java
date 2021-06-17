package com.Theeef.me.spells;

import com.Theeef.me.damage.DamageRoll;
import com.Theeef.me.damage.DamageType;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class SpellDamage {

    private final DamageType damage_type;
    private final HashMap<Integer, DamageRoll> damage_at_slot_level;

    public SpellDamage(JSONObject json) {
        this.damage_type = new DamageType((JSONObject) json.get("damage_type"));
        this.damage_at_slot_level = new HashMap<Integer, DamageRoll>();
        JSONObject damage_at_slot_level = (JSONObject) json.get("damage_at_slot_level");

        for (Object levelString : damage_at_slot_level.keySet())
            this.damage_at_slot_level.put(Integer.parseInt((String) levelString), new DamageRoll((String) damage_at_slot_level.get(levelString)));
    }

    public DamageType getDamageType() {
        return this.damage_type;
    }

    public DamageRoll getDamageAtLevel(int level) {
        return this.damage_at_slot_level.get(level);
    }
}
