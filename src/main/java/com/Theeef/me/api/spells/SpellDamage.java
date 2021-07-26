package com.Theeef.me.api.spells;

import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.mechanics.DamageRoll;
import com.Theeef.me.api.mechanics.DamageType;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class SpellDamage {

    private final APIReference damage_type;
    private final HashMap<Integer, DamageRoll> damage_at_slot_level;
    private final HashMap<Integer, DamageRoll> damage_at_character_level;

    public SpellDamage(JSONObject json) {
        this.damage_type = json.containsKey("damage_type") ? new APIReference((JSONObject) json.get("damage_type")) : null;
        this.damage_at_slot_level = json.containsKey("damage_at_slot_level") ? new HashMap<Integer, DamageRoll>() : null;
        this.damage_at_character_level = json.containsKey("damage_at_character_level") ? new HashMap<Integer, DamageRoll>() : null;

        if (this.damage_at_slot_level != null) {
            JSONObject damage_at_slot_level = (JSONObject) json.get("damage_at_slot_level");

            for (Object levelString : damage_at_slot_level.keySet())
                this.damage_at_slot_level.put(Integer.parseInt((String) levelString), new DamageRoll((String) damage_at_slot_level.get(levelString)));
        }

        if (this.damage_at_character_level != null) {
            JSONObject damage_at_character_level = (JSONObject) json.get("damage_at_character_level");

            for (Object levelString : damage_at_character_level.keySet())
                this.damage_at_character_level.put(Integer.parseInt((String) levelString), new DamageRoll((String) damage_at_character_level.get(levelString)));
        }
    }

    // Getter methods
    public DamageType getDamageType() {
        return new DamageType(this.damage_type);
    }

    public DamageRoll getDamageAtSlotLevel(int level) {
        return this.damage_at_slot_level.get(level);
    }

    public DamageRoll getDamageAtCharacterLevel(int level) {
        DamageRoll damage = null;
        int highestLevel = 0;

        for (int characterLevel : this.damage_at_character_level.keySet())
            if (damage == null || (characterLevel > highestLevel && characterLevel <= level)) {
                damage = this.damage_at_character_level.get(characterLevel);
                highestLevel = characterLevel;
            }

        return damage;
    }
}
