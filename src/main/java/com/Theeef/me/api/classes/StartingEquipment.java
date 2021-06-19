package com.Theeef.me.api.classes;

import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.Choice;
import com.Theeef.me.api.equipment.Equipment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StartingEquipment {

    private final String index;
    private final APIReference dndclass;
    private final List<APIReference> starting_equipment;
    private final List<Choice> starting_equipment_options;
    private final String url;

    public StartingEquipment(JSONObject json) {
        this.index = (String) json.get("index");
        this.dndclass = new APIReference((JSONObject) json.get("class"));
        this.starting_equipment = new ArrayList<>();
        this.starting_equipment_options = new ArrayList<>();
        this.url = (String) json.get("url");

        for (Object equipment : (JSONArray) json.get("starting_equipment"))
            this.starting_equipment.add(new APIReference((JSONObject) equipment));

        for (Object equipmentChoice : (JSONArray) json.get("starting_equipment_options"))
            this.starting_equipment_options.add(new Choice((JSONObject) equipmentChoice));
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public DNDClass getDNDClass() {
        return new DNDClass(this.dndclass.getUrl());
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

    public String getUrl() {
        return this.url;
    }

}
