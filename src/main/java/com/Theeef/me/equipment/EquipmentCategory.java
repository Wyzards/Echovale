package com.Theeef.me.equipment;

import com.Theeef.me.common.APIReference;
import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EquipmentCategory {

    private final String index;
    private final String name;
    private final List<APIReference> equipment;
    private final String url;

    public EquipmentCategory(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.equipment = new ArrayList<>();
        this.url = url;

        for (Object equipmentReference : ((JSONArray) json.get("equipment")))
            this.equipment.add(new APIReference((JSONObject) equipmentReference));
    }

    // APIReference get methods
    public List<Equipment> getEquipment() {
        List<Equipment> list = new ArrayList<>();

        for (APIReference equipmentReference : this.equipment)
            list.add(Equipment.fromString(equipmentReference.getUrl()));

        return list;
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }
}
