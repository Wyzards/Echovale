package com.Theeef.me.api.races;

import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.Choice;
import com.Theeef.me.api.mechanics.DamageType;
import com.Theeef.me.api.monsters.Action;
import org.json.simple.JSONObject;

public class TraitSpecific {

    private final Choice subtrait_options;
    private final Choice spell_options;
    private final APIReference damage_type;
    private final Action breath_weapon;

    public TraitSpecific(JSONObject json) {
        this.subtrait_options = json.containsKey("subtrait_options") ? new Choice((JSONObject) json.get("subtrait_options")) : null;
        this.spell_options = json.containsKey("spell_options") ? new Choice((JSONObject) json.get("spell_options")) : null;
        this.damage_type = json.containsKey("damage_type") ? new APIReference((JSONObject) json.get("damage_type")) : null;
        this.breath_weapon = json.containsKey("breath_weapon") ? new Action((JSONObject) json.get("breath_weapon")) : null;
    }

    // Getter methods
    public Choice getSubtraitOptions() {
        return this.subtrait_options;
    }

    public Choice getSpellOptions() {
        return this.spell_options;
    }

    public DamageType getDamageType() {
        return this.damage_type == null ? null : new DamageType(this.damage_type);
    }

    public Action getBreathWeapon() {
        return this.breath_weapon;
    }

}
