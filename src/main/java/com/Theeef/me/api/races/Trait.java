package com.Theeef.me.api.races;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.Choice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Trait {

    private final String index;
    private final List<APIReference> races;
    private final List<APIReference> subraces;
    private final String name;
    private final List<String> desc;
    private final List<APIReference> proficiencies;
    private final Choice proficiency_choices;
    private final String url;

    public Trait(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.races = new ArrayList<>();
        this.subraces = new ArrayList<>();
        this.name = (String) json.get("name");
        this.desc = new ArrayList<>();
        this.proficiencies = new ArrayList<>();
        this.proficiency_choices = json.containsKey("proficiency_options") ? new Choice((JSONObject) json.get("proficiency_options")) : null;
        this.url = url;

        for (Object race : (JSONArray) json.get("races"))
            this.races.add(new APIReference((JSONObject) race));

        for (Object subrace : (JSONArray) json.get("subraces"))
            this.subraces.add(new APIReference((JSONObject) subrace));

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);

        for (Object proficiency : (JSONArray) json.get("proficiencies"))
            this.proficiencies.add(new APIReference((JSONObject) proficiency));
    }

    // Getter methods
    public String getIndex() {
        return this.index;
    }

    public List<Race> getRaces() {
        List<Race> list = new ArrayList<>();

        for (APIReference race : this.races)
            list.add(new Race(race.getUrl()));

        return list;
    }

    public List<Subrace> getSubraces() {
        List<Subrace> list = new ArrayList<>();

        for (APIReference subrace : this.subraces)
            list.add(new Subrace(subrace.getUrl()));

        return list;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getDescription() {
        return this.desc;
    }

    public List<Proficiency> getProficiencies() {
        List<Proficiency> list = new ArrayList<>();

        for (APIReference proficiency : this.proficiencies)
            list.add(new Proficiency(proficiency.getUrl()));

        return list;
    }

    public Choice getProficiencyChoices() {
        return this.proficiency_choices;
    }

    public String getUrl() {
        return this.url;
    }

}
