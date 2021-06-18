package com.Theeef.me.chardata;

import com.Theeef.me.common.APIReference;
import com.Theeef.me.APIRequest;
import com.Theeef.me.classes.DNDClass;
import com.Theeef.me.equipment.EquipmentCategory;
import com.Theeef.me.races.Race;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Proficiency {

    private final String index;
    private final String type;
    private final String name;
    private final List<APIReference> classes;
    private final List<APIReference> races;
    private final String url;
    private final List<APIReference> references;

    public Proficiency(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.type = (String) json.get("type");
        this.name = (String) json.get("name");
        this.classes = new ArrayList<>();
        this.races = new ArrayList<>();
        this.url = url;
        this.references = new ArrayList<>();

        for (Object classObject : (JSONArray) json.get("classes"))
            this.classes.add(new APIReference((JSONObject) classObject));

        for (Object raceObject : (JSONArray) json.get("races"))
            this.races.add(new APIReference((JSONObject) raceObject));

        for (Object referenceObject : (JSONArray) json.get("references"))
            this.references.add(new APIReference((JSONObject) referenceObject));
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

    public List<EquipmentCategory> getReferences() {
        List<EquipmentCategory> list = new ArrayList<>();

        for (APIReference reference : this.references)
            list.add(new EquipmentCategory(reference.getUrl()));

        return list;
    }

}
