package com.Theeef.me.api.classes;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.common.choice.CountedReference;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.Choice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DNDClass {

    private final String index;
    private final String name;
    private final long hit_die;
    private final List<Choice> proficiency_choices;
    private final List<APIReference> proficiencies;
    private final List<APIReference> saving_throws;
    private final List<CountedReference> starting_equipment;
    private final List<Choice> starting_equipment_options;
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
        this.starting_equipment = new ArrayList<>();
        this.starting_equipment_options = new ArrayList<>();
        this.class_levels = (String) json.get("class_levels");
        this.subclasses = new ArrayList<>();
        this.spellcasting = json.containsKey("spellcasting") ? new Spellcasting((JSONObject) json.get("spellcasting")) : null;
        this.spells = (String) json.get("spells");
        this.url = url;

        for (Object proficiencyChoice : (JSONArray) json.get("proficiency_choices"))
            this.proficiency_choices.add(new Choice((JSONObject) proficiencyChoice));

        for (Object proficiency : (JSONArray) json.get("proficiencies"))
            this.proficiencies.add(new APIReference((JSONObject) proficiency));

        for (Object savingThrow : (JSONArray) json.get("saving_throws"))
            this.saving_throws.add(new APIReference((JSONObject) savingThrow));

        for (Object equipment : (JSONArray) json.get("starting_equipment"))
            this.starting_equipment.add(CountedReference.fromJSON((JSONObject) equipment));

        for (Object equipmentChoice : (JSONArray) json.get("starting_equipment_options"))
            this.starting_equipment_options.add(new Choice((JSONObject) equipmentChoice));

        for (Object subclass : (JSONArray) json.get("subclasses"))
            this.subclasses.add(new APIReference((JSONObject) subclass));
    }

    public DNDClass(APIReference reference) {
        this(reference.getUrl());
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

    public List<CountedReference> getStartingEquipment() {
        return this.starting_equipment;
    }

    public List<Choice> getStartingEquipmentOptions() {
        return this.starting_equipment_options;
    }

    public List<Level> getClassLevels(boolean includeSubclass) {
        List<Level> list = new ArrayList<>();
        JSONArray array = (JSONArray) APIRequest.requestAware(this.class_levels);

        for (Object level : array)
            if (!((JSONObject) level).containsKey("subclass") || includeSubclass)
                list.add(new Level((JSONObject) level));

        return list;
    }

    public List<Level> getClassLevels(int level) {
        List<Level> levels = getClassLevels(true);

        for (int i = levels.size() - 1; i >= 0; i--)
            if (levels.get(i).getLevel() != level)
                levels.remove(i);

        return levels;
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

    public List<APIReference> getSpells() {
        List<APIReference> list = new ArrayList<>();
        JSONObject json = APIRequest.request(this.spells);

        for (Object spell : (JSONArray) json.get("results"))
            list.add(new APIReference((JSONObject) spell));

        return list;
    }

    public String getUrl() {
        return this.url;
    }

    // Static methods
    public static Set<DNDClass> values() {
        Set<DNDClass> set = new HashSet<>();

        for (Object classObject : (JSONArray) APIRequest.request("/api/classes/").get("results"))
            set.add(new DNDClass((String) ((JSONObject) classObject).get("url")));

        return set;
    }

    public static DNDClass fromIndex(String index) {
        return new DNDClass("/api/classes/" + index);
    }
}
