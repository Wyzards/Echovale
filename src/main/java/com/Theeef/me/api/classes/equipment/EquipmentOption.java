package com.Theeef.me.api.classes.equipment;

import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.equipment.EquipmentCategory;
import org.json.simple.JSONObject;

public class EquipmentOption {

    private final long choose;
    private final String type;
    private final APIReference equipment_category;

    public EquipmentOption(JSONObject outerJSON) {
        JSONObject json = (JSONObject) outerJSON.get("equipment_option");

        this.choose = (long) json.get("choose");
        this.type = (String) json.get("type");
        this.equipment_category = new APIReference((JSONObject) ((JSONObject) json.get("from")).get("equipment_category"));
    }

    public long getChoiceAmount() {
        return this.choose;
    }

    public String getType() {
        return this.type;
    }

    public EquipmentCategory getEquipmentCategory() {
        return new EquipmentCategory(this.equipment_category);
    }

}
