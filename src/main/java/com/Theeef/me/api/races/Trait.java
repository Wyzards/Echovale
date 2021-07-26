package com.Theeef.me.api.races;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.Choice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Trait {

    private final String index;
    private final List<APIReference> races;
    private final List<APIReference> subraces;
    private final String name;
    private final List<String> desc;
    private final List<APIReference> proficiencies;
    private final Choice proficiency_choices;
    private final APIReference parent;
    private final TraitSpecific trait_specific;
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
        this.parent = json.containsKey("parent") ? new APIReference((JSONObject) json.get("parent")) : null;
        this.trait_specific = json.containsKey("trait_specific") ? new TraitSpecific((JSONObject) json.get("trait_specific")) : null;
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

    public Trait(APIReference reference) {
        this(reference.getUrl());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Trait && ((Trait) object).getIndex().equals(this.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), this.index);
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

    public Trait getParent() {
        return this.parent == null ? null : new Trait(this.parent);
    }

    public TraitSpecific getTraitSpecific() {
        return this.trait_specific;
    }

    public String getUrl() {
        return this.url;
    }

    // Static Methods
    public static Set<Trait> values() {
        Set<Trait> set = new HashSet<>();
        JSONArray results = (JSONArray) APIRequest.request("/api/traits/").get("results");

        for (Object result : results)
            set.add(new Trait((String) ((JSONObject) result).get("url")));

        return set;
    }

}
