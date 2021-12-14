package com.Theeef.me.api.chardata;

import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.APIRequest;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.races.Race;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Proficiency {

    private final String index;
    private final String type;
    private final String name;
    private final List<APIReference> classes;
    private final List<APIReference> races;
    private final String url;
    private final APIReference reference;

    public Proficiency(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.type = (String) json.get("type");
        this.name = (String) json.get("name");
        this.classes = new ArrayList<>();
        this.races = new ArrayList<>();
        this.url = url;
        this.reference = new APIReference((JSONObject) json.get("reference"));

        for (Object classObject : (JSONArray) json.get("classes"))
            this.classes.add(new APIReference((JSONObject) classObject));

        for (Object raceObject : (JSONArray) json.get("races"))
            this.races.add(new APIReference((JSONObject) raceObject));
    }

    public Proficiency(APIReference reference) {
        this(reference.getUrl());
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public List<DNDClass> getClasses() {
        List<DNDClass> list = new ArrayList<>();

        for (APIReference classReferece : this.classes)
            list.add(new DNDClass(classReferece.getUrl()));

        return list;
    }

    public List<Race> getRaces() {
        List<Race> list = new ArrayList<>();

        for (APIReference raceReference : this.races)
            list.add(new Race(raceReference.getUrl()));

        return list;
    }

    public String getUrl() {
        return this.url;
    }

    public APIReference getReference() {
        return this.reference;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Proficiency && ((Proficiency) object).getIndex().equals(this.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), this.index);
    }

    // Static methods
    public static Proficiency fromIndex(String index) {
        return new Proficiency("/api/proficiencies/" + index);
    }

}
