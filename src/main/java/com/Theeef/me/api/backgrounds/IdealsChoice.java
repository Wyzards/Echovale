package com.Theeef.me.api.backgrounds;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IdealsChoice {

    private final long choose;
    private final String type;
    private final List<Ideal> from;

    public IdealsChoice(JSONObject json) {
        this.choose = (long) json.get("choose");
        this.type = (String) json.get("type");
        this.from = new ArrayList<>();

        for (Object ideal : (JSONArray) json.get("from"))
            this.from.add(new Ideal((JSONObject) ideal));
    }

    // Getter methods
    public long getChoiceAmount() {
        return this.choose;
    }

    public String getType() {
        return this.type;
    }

    public List<Ideal> getFrom() {
        return this.from;
    }

}
