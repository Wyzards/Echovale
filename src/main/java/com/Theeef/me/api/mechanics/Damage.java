package com.Theeef.me.api.mechanics;

import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Damage {

    private final APIReference damage_type;
    private final String damage_dice;
    private final HashMap<Integer, String> damage_at_character_level;

    public Damage(JSONObject json) {
        this.damage_dice = (String) json.get("damage_dice");
        this.damage_type = new APIReference((JSONObject) json.get("damage_type"));
        this.damage_at_character_level = json.containsKey("damage_at_character_level") ? new HashMap<>() : null;

        if (this.damage_at_character_level != null)
            for (Object levelNumKey : ((JSONObject) json.get("damage_at_character_level")).keySet())
                this.damage_at_character_level.put(Integer.parseInt((String) levelNumKey), (String) ((JSONObject) json.get("damage_at_character_level")).get(levelNumKey));
    }

    // Getter methods
    public DamageType getType() {
        return new DamageType(this.damage_type.getUrl());
    }

    public DamageRoll getDamageDice() {
        return new DamageRoll(this.damage_dice);
    }

    public DamageRoll getDamageAtCharacterLevel(int level) {
        if (this.damage_at_character_level == null)
            return null;

        int highestLevel = 0;

        for (int charLevel : this.damage_at_character_level.keySet())
            if (charLevel > highestLevel && charLevel <= level)
                highestLevel = charLevel;

        return new DamageRoll(this.damage_at_character_level.get(highestLevel));
    }
}
