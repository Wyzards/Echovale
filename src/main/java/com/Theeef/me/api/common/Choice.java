package com.Theeef.me.api.common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Choice {

    private final long choose;
    private final String type;
    private final List<APIReference> from;

    public Choice(JSONObject json) {
        this.choose = (long) json.get("choose");
        this.type = (String) json.get("type");
        this.from = new ArrayList<>();

        for (Object fromObj : (JSONArray) json.get("from"))
            this.from.add(new APIReference((JSONObject) fromObj));
    }

    // Get methods
    // TODO: Generics
    public long getChoiceAmount() {
        return this.choose;
    }

    public String getType() {
        return this.type;
    }

    public List<APIReference> getChoices() {
        return this.from;
    }
}
