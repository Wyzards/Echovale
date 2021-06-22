package com.Theeef.me.api.classes.equipment;

import com.Theeef.me.api.equipment.containers.ItemQuantity;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EquipmentList {

    private final List<EquipmentQuantity> equipment;
    private final List<EquipmentOption> equipmentOptions;

    public EquipmentList(JSONObject json) {
        this.equipment = new ArrayList<>();
        this.equipmentOptions = new ArrayList<>();

        if (json.containsKey("equipment"))
            this.equipment.add(new EquipmentQuantity(json));
        else if (json.containsKey("equipment_category")) {
            this.equipmentOptions.add(new EquipmentOption((JSONObject) json.get("equipment_category")));
        } else
            for (Object numKey : json.keySet())
                if (((JSONObject) json.get(numKey)).containsKey("equipment"))
                    this.equipment.add(new EquipmentQuantity((JSONObject) json.get((String) numKey)));
                else if (((JSONObject) json.get(numKey)).containsKey("equipment_category"))
                    this.equipmentOptions.add(new EquipmentOption((JSONObject) json.get((String) numKey)));
    }

    public List<EquipmentQuantity> getEquipment() {
        return this.equipment;
    }

    public List<EquipmentOption> getEquipmentOptions() {
        return this.equipmentOptions;
    }

}
