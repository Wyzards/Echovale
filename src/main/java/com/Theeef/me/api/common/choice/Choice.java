package com.Theeef.me.api.common.choice;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Choice {

    private final long choose;
    private final String type;
    private final OptionSet from;

    public Choice(String type, long choose, OptionSet from) {
        this.type = type;
        this.choose = choose;
        this.from = from;
    }

    public Choice(JSONObject json) {
        this.choose = (long) json.get("choose");
        this.type = (String) json.get("type");

        if (json.get("from") instanceof JSONArray) {
            List<Option> options = new ArrayList<>();

            for (Object option : (JSONArray) json.get("from"))
                options.add(Option.fromJSON((JSONObject) option));

            this.from = new ArrayOptionSet(options);
        } else if (((JSONObject) json.get("from")).containsKey("equipment_category"))
            this.from = new ResourceListOptionSet((String) ((JSONObject) ((JSONObject) json.get("from")).get("equipment_category")).get("url"));
        else
            throw new IllegalArgumentException("Could not find a valid OptionSet type for choice " + json);
    }

    // Geter methods
    public long getChoiceAmount() {
        return this.choose;
    }

    public String getType() {
        return this.type;
    }

    public List<Option> getChoices() {
        return this.from.getOptions();
    }
}
