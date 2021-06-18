package com.Theeef.me.classes;

import com.Theeef.me.APIRequest;
import com.Theeef.me.chardata.AbilityScore;
import com.Theeef.me.chardata.Proficiency;
import com.Theeef.me.common.APIReference;
import com.Theeef.me.common.Choice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DNDClass {

    private final String index;
    private final String name;
    private final long hit_die;
    private final List<Choice> proficiency_choices;
    private final List<APIReference> proficiencies;
    private final List<APIReference> saving_throws;
    private final StartingEquipment starting_equipment;
    private final String class_levels;
    private final List<APIReference> subclasses;
    private final Spellcasting spellcasting;
    private final String spells;
    private final String url;

    public DNDClass(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.hit_die = (long) json.get("hit_die");
        this.proficiency_choices = new ArrayList<>();
        this.proficiencies = new ArrayList<>();
        this.saving_throws = new ArrayList<>();
        this.starting_equipment = new StartingEquipment((JSONObject) json.get("starting_equipment"));
        this.class_levels = (String) json.get("class_levels");
        this.subclasses = new ArrayList<>();
        this.spellcasting = new Spellcasting((JSONObject) json.get("spellcasting"));
        this.spells = (String) json.get("spells");
        this.url = url;

        for (Object proficiency : (JSONArray) json.get("proficiencies"))
            this.proficiencies.add(new APIReference((JSONObject) proficiency));

        for (Object savingThrow : (JSONArray) json.get("saving_throws"))
            this.saving_throws.add(new APIReference((JSONObject) savingThrow));

        for (Object subclass : (JSONArray) json.get("subclasses"))
            this.subclasses.add(new APIReference((JSONObject) subclass));
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public long getHitDie() {
        return this.hit_die;
    }

    public List<Choice> getProficiencyChoices() {
        return this.proficiency_choices;
    }

    public List<Proficiency> getProficiencies() {
        List<Proficiency> list = new ArrayList<>();

        for (APIReference proficiency : this.proficiencies)
            list.add(new Proficiency(proficiency.getUrl()));

        return list;
    }

    public List<AbilityScore> getSavingThrows() {
        List<AbilityScore> list = new ArrayList<>();

        for (APIReference savingThrow : this.saving_throws)
            list.add(new AbilityScore(savingThrow.getUrl()));

        return list;
    }

    public StartingEquipment getStartingEquipment() {
        return this.starting_equipment;
    }

    public String getClassLevelsUrl() {
        return this.class_levels;
    }

    // TODO: Add sourcebook targeters
    public List<Subclass> getSubclasses() {
        List<Subclass> list = new ArrayList<>();

        for (APIReference subclass : this.subclasses)
            list.add(new Subclass(subclass.getUrl()));

        return list;
    }

    public Spellcasting getSpellcasting() {
        return this.spellcasting;
    }

    public String getSpellListUrl() {
        return this.spells;
    }

    public String getUrl() {
        return this.url;
    }
}
