package com.Theeef.me.classes.subclasses;

import com.Theeef.me.common.APIReference;
import com.Theeef.me.spells.Spell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubclassSpell {

    private final List<Prerequisite> prerequisites;
    private final APIReference spell;

    public SubclassSpell(JSONObject json) {
        this.prerequisites = new ArrayList<>();
        this.spell = new APIReference((JSONObject) json.get("spell"));

        for (Object prerequisite : (JSONArray) json.get("prerequisites"))
            this.prerequisites.add(new Prerequisite((JSONObject) prerequisite));
    }

    // Get methods
    public List<Prerequisite> getPrerequisites() {
        return this.prerequisites;
    }

    public Spell getSpell() {
        return new Spell(this.spell.getUrl());
    }

}
