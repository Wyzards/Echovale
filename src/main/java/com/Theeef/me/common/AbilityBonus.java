package com.Theeef.me.common;

import com.Theeef.me.chardata.AbilityScore;
import org.json.simple.JSONObject;

public class AbilityBonus {

    private final long bonus;
    private final APIReference ability_score;

    public AbilityBonus(JSONObject json) {
        this.bonus = (long) json.get("bonus");
        this.ability_score = new APIReference((JSONObject) json.get("ability_score"));
    }

    // Get methods
    public long getBonus() {
        return this.bonus;
    }

    public AbilityScore getAbilityScore() {
        return new AbilityScore(this.ability_score.getUrl());
    }
}
