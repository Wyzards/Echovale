package com.Theeef.me.api.common.choice;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Choice {

    private final long choose;
    private String type;
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

            for (Object option : (JSONArray) json.get("from")) {
                if (option instanceof JSONObject) {
                    if (((JSONObject) option).containsKey("equipment_category")) {
                        this.from = new ResourceListOptionSet((String) ((JSONObject) ((JSONObject) option).get("equipment_category")).get("url"));
                        return;
                    }

                    options.add(Option.fromJSON((JSONObject) option));
                } else if (option instanceof String)
                    options.add(new SingleStringOption(new ArrayList<>(), (String) option));
            }

            this.from = new ArrayOptionSet(options);
        } else if (((JSONObject) json.get("from")).containsKey("equipment_category"))
            this.from = new ResourceListOptionSet((String) ((JSONObject) ((JSONObject) json.get("from")).get("equipment_category")).get("url"));
        else
            throw new IllegalArgumentException("Could not find a valid OptionSet type for choice " + json);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Choice && ((Choice) object).getChoiceAmount() == this.choose && ((Choice) object).getType().equals(this.type) && ((Choice) object).getOptionSet().equals(this.getOptionSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.choose, this.type, this.from);
    }

    // Geter methods
    public long getChoiceAmount() {
        return this.choose;
    }

    public String getType() {
        return this.type;
    }

    public List<Option> getOptions() {
        return this.from.getOptions();
    }

    public OptionSet getOptionSet() {
        return this.from;
    }

    public void setType(String type) {
        this.type = type;
    }
}
