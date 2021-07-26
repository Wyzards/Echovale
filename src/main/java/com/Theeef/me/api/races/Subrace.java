package com.Theeef.me.api.races;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.AbilityBonus;
import com.Theeef.me.api.common.choice.Choice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Subrace {

    private final String index;
    private final String name;
    private final APIReference race;
    private final String desc;
    private final List<AbilityBonus> ability_bonuses;
    private final List<APIReference> starting_proficiencies;
    private final List<APIReference> languages;
    private final Choice language_options;
    private final List<APIReference> racial_traits;
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
        this.language_options = json.containsKey("language_options") ? new Choice((JSONObject) json.get("language_options")) : null;
        this.racial_traits = new ArrayList<>();
        this.url = url;

        for (Object abilityBonus : (JSONArray) json.get("ability_bonuses"))
            this.ability_bonuses.add(new AbilityBonus((JSONObject) abilityBonus));

        for (Object proficiency : (JSONArray) json.get("starting_proficiencies"))
            this.starting_proficiencies.add(new APIReference((JSONObject) proficiency));

        for (Object language : (JSONArray) json.get("languages"))
            this.languages.add(new APIReference((JSONObject) language));

        for (Object trait : (JSONArray) json.get("racial_traits"))
            this.racial_traits.add(new APIReference((JSONObject) trait));
    }

    public Subrace(APIReference reference) {
        this(reference.getUrl());
    }

    // Getter methods
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

    public Choice getLanguageOptions() {
        return this.language_options;
    }

    public List<Trait> getRacialTraits() {
        List<Trait> list = new ArrayList<>();

        for (APIReference trait : this.racial_traits)
            list.add(new Trait(trait.getUrl()));

        return list;
    }

    public String getUrl() {
        return this.url;
    }

    // Static methods
    public static Set<Subrace> values() {
        Set<Subrace> set = new HashSet<>();
        JSONArray results = (JSONArray) APIRequest.request("/api/subraces/").get("results");

        for (Object result : results)
            set.add(new Subrace((String) ((JSONObject) result).get("url")));

        return set;
    }

    public static Subrace fromIndex(String index) {
        return new Subrace("/api/subraces/" + index);
    }
}
