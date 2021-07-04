package com.Theeef.me.api.backgrounds;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DescriptionChoice {

    private final long choose;
    private final String type;
    private final List<String> from;

    public DescriptionChoice(JSONObject json) {
        this.choose = (long) json.get("choose");
        this.type = (String) json.get("type");
        this.from = new ArrayList<>();

        for (Object string : (JSONArray) json.get("from"))
            this.from.add((String) string);
    }

    public long getChoiceAmount() {
        return this.choose;
    }

    public String getType() {
        return this.type;
    }

    public List<String> getFrom() {
        return this.from;
    }

}
