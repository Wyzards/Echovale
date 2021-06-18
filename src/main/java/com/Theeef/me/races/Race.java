package com.Theeef.me.races;

import com.Theeef.me.APIRequest;
import com.Theeef.me.chardata.Language;
import com.Theeef.me.chardata.Proficiency;
import com.Theeef.me.common.APIReference;
import com.Theeef.me.common.AbilityBonus;
import com.Theeef.me.common.Choice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Race {

    private final String index;
    private final String name;
    private final long speed;
    private final List<AbilityBonus> ability_bonuses;
    private final String alignment;
    private final String age;
    private final String size;
    private final String size_description;
    private final List<APIReference> starting_proficiencies;
    private final List<Choice> starting_proficiency_options;
    private final List<APIReference> languages;
    private final String language_desc;
    private final List<APIReference> traits;
    private final List<APIReference> subraces;
    private final String url;

    public Race(String url) {
        // TODO: VARIANT HUMAN ABILITY BONUSES

        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.speed = (long) json.get("speed");
        this.ability_bonuses = new ArrayList<>();
        this.alignment = (String) json.get("alignment");
        this.age = (String) json.get("age");
        this.size = (String) json.get("size");
        this.size_description = (String) json.get("size_description");
        this.starting_proficiencies = new ArrayList<>();
        this.starting_proficiency_options = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.language_desc = (String) json.get("language_desc");
        this.traits = new ArrayList<>();
        this.subraces = new ArrayList<>();
        this.url = url;

        for (Object abilityBonus : (JSONArray) json.get("ability_bonuses"))
            this.ability_bonuses.add(new AbilityBonus((JSONObject) abilityBonus));

        for (Object proficiency : (JSONArray) json.get("starting_proficiencies"))
            this.starting_proficiencies.add(new APIReference((JSONObject) json.get(proficiency)));

        if (json.containsKey("starting_proficiency_options"))
            for (Object proficiencyOption : (JSONArray) json.get("starting_proficiency_options"))
                this.starting_proficiency_options.add(new Choice((JSONObject) proficiencyOption));

        for (Object language : (JSONArray) json.get("languages"))
            this.languages.add(new APIReference((JSONObject) language));

        for (Object trait : (JSONArray) json.get("traits"))
            this.traits.add(new APIReference((JSONObject) trait));

        for (Object subrace : (JSONArray) json.get("subraces"))
            this.subraces.add(new APIReference((JSONObject) subrace));
    }

    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public long getSpeed() {
        return this.speed;
    }

    public List<AbilityBonus> getAbilityBonuses() {
        return this.ability_bonuses;
    }

    public String getAlignmentDescription() {
        return this.alignment;
    }

    public String getAgeDescription() {
        return this.age;
    }

    public String getSize() {
        return this.size;
    }

    public String getSizeDescription() {
        return this.size_description;
    }

    public List<Proficiency> getStartingProficiencies() {
        List<Proficiency> list = new ArrayList<>();

        for (APIReference proficiency : this.starting_proficiencies)
            list.add(new Proficiency(proficiency.getUrl()));

        return list;
    }

    public List<Choice> getStartingProficiencyOptions() {
        return this.starting_proficiency_options;
    }

    public List<Language> getLanguages() {
        List<Language> list = new ArrayList<>();

        for (APIReference language : this.languages)
            list.add(new Language(language.getUrl()));

        return list;
    }

    public String getLanguageDescription() {
        return this.language_desc;
    }

    public List<Trait> getTraits() {
        List<Trait> list = new ArrayList<>();

        for (APIReference trait : this.traits)
            list.add(new Trait(trait.getUrl()));

        return list;
    }

    public List<Subrace> getSubraces() {
        List<Subrace> list = new ArrayList<>();

        for (APIReference subrace : this.subraces)
            list.add(new Subrace(subrace.getUrl()));

        return list;
    }

    public String getUrl() {
        return this.url;
    }

}
