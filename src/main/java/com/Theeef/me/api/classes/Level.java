package com.Theeef.me.api.classes;

import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final long level;
    private final long ability_score_bonuses;
    private final long prof_bonus;
    private final List<APIReference> feature_choices;
    private final List<APIReference> features;
    private final SpellcastingLevel spellcasting;
    private final JSONObject class_specific;
    private final String index;
    private final APIReference dndclass;
    private final APIReference subclass;
    private final String url;

    public Level(JSONObject json) {
        this.level = (long) json.get("level");
        this.ability_score_bonuses = json.containsKey("ability_score_bonuses") ? (long) json.get("ability_score_bonuses") : -1;
        this.prof_bonus = json.containsKey("prof_bonus") ? (long) json.get("prof_bonus") : -1;
        this.feature_choices = new ArrayList<>();
        this.features = new ArrayList<>();
        this.spellcasting = json.containsKey("spellcasting") ? new SpellcastingLevel((JSONObject) json.get("spellcasting")) : null;
        this.class_specific = json.containsKey("class_specific") ? (JSONObject) json.get("class_specific") : null;
        this.index = (String) json.get("index");
        this.dndclass = new APIReference((JSONObject) json.get("class"));
        this.subclass = json.containsKey("subclass") ? new APIReference((JSONObject) json.get("subclass")) : null;
        this.url = (String) json.get("url");

        for (Object featureChoice : (JSONArray) json.get("feature_choices"))
            this.feature_choices.add(new APIReference((JSONObject) featureChoice));

        for (Object feature : (JSONArray) json.get("features"))
            this.features.add(new APIReference((JSONObject) feature));
    }

    // Getter methods
    public long getLevel() {
        return this.level;
    }

    public long getAbilityScoreBonuses() {
        return this.ability_score_bonuses;
    }

    public long getProficiencyBonus() {
        return this.prof_bonus;
    }

    public List<Feature> getFeatureChoices() {
        List<Feature> list = new ArrayList<>();

        for (APIReference featureChoice : this.feature_choices)
            list.add(new Feature(featureChoice));

        return list;
    }

    public List<Feature> getFeatures() {
        List<Feature> list = new ArrayList<>();

        for (APIReference feature : this.features)
            list.add(new Feature(feature));

        return list;
    }

    public SpellcastingLevel getSpellcasting() {
        return this.spellcasting;
    }

    public JSONObject getClassSpecific() {
        return this.class_specific;
    }

    public String getIndex() {
        return this.index;
    }

    public DNDClass getDNDClass() {
        return new DNDClass(this.dndclass);
    }

    public Subclass getSubclass() {
        return this.subclass == null ? null : new Subclass(this.subclass);
    }

    public String getUrl() {
        return this.url;
    }

}
