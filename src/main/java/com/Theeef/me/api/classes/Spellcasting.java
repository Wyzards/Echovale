package com.Theeef.me.api.classes;

import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.Info;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Spellcasting {

    private final long level;
    private final APIReference spellcasting_ability;
    private final List<Info> info;

    public Spellcasting(JSONObject json) {
        this.level = (long) json.get("level");
        this.spellcasting_ability = new APIReference((JSONObject) json.get("spellcasting_ability"));
        this.info = new ArrayList<>();

        for (Object info : (JSONArray) json.get("info"))
            this.info.add(new Info((JSONObject) info));
    }

    // Getter methods
    public long getLevel() {
        return this.level;
    }

    public AbilityScore getSpellcastingAbility() {
        return new AbilityScore(this.spellcasting_ability.getUrl());
    }

    public List<Info> getInfo() {
        return this.info;
    }
}
