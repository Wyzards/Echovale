package com.Theeef.me.api.backgrounds;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.Choice;
import com.Theeef.me.api.equipment.Equipment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Background {

    private final String index;
    private final String name;
    private final List<APIReference> starting_proficiencies;
    private final Choice language_options;
    private final List<APIReference> starting_equipment;
    private final List<Choice> starting_equipment_options;
    private final BackgroundFeature feature;
    private final DescriptionChoice personality_traits;
    private final IdealsChoice ideals;
    private final DescriptionChoice bonds;
    private final DescriptionChoice flaws;
    private final String url;

    public Background(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.starting_proficiencies = new ArrayList<>();
        this.language_options = new Choice((JSONObject) json.get("language_options"));
        this.starting_equipment = new ArrayList<>();
        this.starting_equipment_options = new ArrayList<>();
        this.feature = new BackgroundFeature((JSONObject) json.get("feature"));
        this.personality_traits = new DescriptionChoice((JSONObject) json.get("personality_traits"));
        this.ideals = new IdealsChoice((JSONObject) json.get("ideals"));
        this.bonds = new DescriptionChoice((JSONObject) json.get("bonds"));
        this.flaws = new DescriptionChoice((JSONObject) json.get("flaws"));
        this.url = url;

        for (Object startingProficiency : (JSONArray) json.get("starting_proficiencies"))
            this.starting_proficiencies.add(new APIReference((JSONObject) startingProficiency));

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

    public DescriptionChoice getPersonalityTraits() {
        return this.personality_traits;
    }

    public IdealsChoice getIdeals() {
        return this.ideals;
    }

    public DescriptionChoice getBonds() {
        return this.bonds;
    }

    public DescriptionChoice getFlaws() {
        return this.flaws;
    }

    public String getUrl() {
        return this.url;
    }

    // Static methods
    public static Background fromIndex(String index) {
        return new Background("/api/backgrounds/" + index);
    }
}
