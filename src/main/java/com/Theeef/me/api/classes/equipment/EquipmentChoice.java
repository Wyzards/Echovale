package com.Theeef.me.api.classes.equipment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EquipmentChoice {

    private final long choose;
    private final String type;
    private final List<EquipmentList> from;

    public EquipmentChoice(JSONObject json) {
        this.choose = (long) json.get("choose");
        this.type = (String) json.get("type");
        this.from = new ArrayList<>();

        for (Object choice : (JSONArray) json.get("from"))
            this.from.add(new EquipmentList((JSONObject) choice));
    }

    public long getChoiceAmount() {
        return this.choose;
    }

    public String getType() {
        return this.type;
    }

    public List<EquipmentList> getFrom() {
        return this.from;
    }

}
