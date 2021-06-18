package com.Theeef.me.backgrounds;

import com.Theeef.me.APIRequest;
import com.Theeef.me.chardata.Language;
import com.Theeef.me.chardata.Proficiency;
import com.Theeef.me.classes.Feature;
import com.Theeef.me.common.APIReference;
import com.Theeef.me.common.Choice;
import com.Theeef.me.equipment.Equipment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Background {

    private final String index;
    private final String name;
    private final List<APIReference> starting_proficiencies;
    private final List<APIReference> languages;
    private final Choice language_options;
    private final List<APIReference> starting_equipment;
    private final List<Choice> starting_equipment_options;
    private final BackgroundFeature feature;
    private final Choice personality_traits;
    private final Choice ideals;
    private final Choice bonds;
    private final Choice flaws;
    private final String url;

    public Background(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.starting_proficiencies = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.language_options = new Choice((JSONObject) json.get("language_options"));
        this.starting_equipment = new ArrayList<>();
        this.starting_equipment_options = new ArrayList<>();
        this.feature = new BackgroundFeature((JSONObject) json.get("feature"));
        this.personality_traits = new Choice((JSONObject) json.get("personality_traits"));
        this.ideals = new Choice((JSONObject) json.get("ideals"));
        this.bonds = new Choice((JSONObject) json.get("bonds"));
        this.flaws = new Choice((JSONObject) json.get("flaws"));
        this.url = url;

        for (Object startingProficiency : (JSONArray) json.get("starting_proficiencies"))
            this.starting_proficiencies.add(new APIReference((JSONObject) startingProficiency));

        if (json.containsKey("languages"))
            for (Object language : (JSONArray) json.get("languages"))
                this.languages.add(new APIReference((JSONObject) language));

        for (Object startingEquipment : (JSONArray) json.get("starting_equipment"))
            this.starting_equipment.add(new APIReference((JSONObject) startingEquipment));

        for (Object equipChoice : (JSONArray) json.get("starting_equipment_options"))
            this.starting_equipment_options.add(new Choice((JSONObject) equipChoice));
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public List<Proficiency> getProficiencies() {
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

    public List<Equipment> getStartingEquipment() {
        List<Equipment> list = new ArrayList<>();

        for (APIReference equipment : this.starting_equipment)
            list.add(Equipment.fromString(equipment.getUrl()));

        return list;
    }

    public List<Choice> getStartingEquipmentOptions() {
        return this.starting_equipment_options;
    }

    public BackgroundFeature getBackgroundFeature() {
        return this.feature;
    }

    public Choice getPersonalityTraits() {
        return this.personality_traits;
    }

    public Choice getIdeals() {
        return this.ideals;
    }

    public Choice getBonds() {
        return this.bonds;
    }

    public Choice getFlaws() {
        return this.flaws;
    }

    public String getUrl() {
        return this.url;
    }
}
