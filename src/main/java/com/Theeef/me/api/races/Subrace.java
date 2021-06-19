package com.Theeef.me.api.races;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.AbilityBonus;
import com.Theeef.me.api.common.Choice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Subrace {

    private final String index;
    private final String name;
    private final APIReference race;
    private final String desc;
    private final List<AbilityBonus> ability_bonuses;
    private final List<APIReference> starting_proficiencies;
    private final List<APIReference> languages;
    private final List<Choice> language_options;
    private final List<APIReference> racial_traits;
    private final List<Choice> racial_trait_options;
    private final String url;

    public Subrace(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.race = new APIReference((JSONObject) json.get("race"));
        this.desc = (String) json.get("desc");
        this.ability_bonuses = new ArrayList<>();
        this.starting_proficiencies = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.language_options = new ArrayList<>();
        this.racial_traits = new ArrayList<>();
        this.racial_trait_options = new ArrayList<>();
        this.url = url;

        for (Object abilityBonus : (JSONArray) json.get("ability_bonuses"))
            this.ability_bonuses.add(new AbilityBonus((JSONObject) abilityBonus));

        for (Object proficiency : (JSONArray) json.get("starting_proficiencies"))
            this.starting_proficiencies.add(new APIReference((JSONObject) proficiency));

        for (Object language : (JSONArray) json.get("languages"))
            this.languages.add(new APIReference((JSONObject) language));

        for (Object languageOption : (JSONArray) json.get("language_options"))
            this.language_options.add(new Choice((JSONObject) languageOption));

        for (Object trait : (JSONArray) json.get("racial_traits"))
            this.racial_traits.add(new APIReference((JSONObject) trait));

        for (Object traitOption : (JSONArray) json.get("racial_trait_options"))
            this.racial_trait_options.add(new Choice((JSONObject) traitOption));
    }

    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public Race getRace() {
        return new Race(this.race.getUrl());
    }

    public String getDescription() {
        return this.desc;
    }

    public List<AbilityBonus> getAbilityBonuses() {
        return this.ability_bonuses;
    }

    public List<Proficiency> getStartingProficiencies() {
        List<Proficiency> list = new ArrayList<>();

        for (APIReference proficiency : this.starting_proficiencies)
            list.add(new Proficiency(proficiency.getUrl()));

        return list;
    }

    public List<Language> getLanguages() {
        List<Language> list = new ArrayList<>();

        for (APIReference language : this.languages)
            list.add(new Language(language.getUrl()));

        return list;
    }

    public List<Choice> getLanguageOptions() {
        return this.language_options;
    }

    public List<Trait> getRacialTraits() {
        List<Trait> list = new ArrayList<>();

        for (APIReference trait : this.racial_traits)
            list.add(new Trait(trait.getUrl()));

        return list;
    }

    public List<Choice> getRacialTraitOptions() {
        return this.racial_trait_options;
    }

    public String getUrl() {
        return this.url;
    }
}
