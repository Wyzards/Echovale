package com.Theeef.me.api.classes.subclasses;

import com.Theeef.me.api.common.choice.Choice;
import org.json.simple.JSONObject;

public class FeatureSpecific {

    private final Choice subfeature_options;
    private final Choice expertise_options;

    public FeatureSpecific(JSONObject json) {
        this.subfeature_options = json.containsKey("subfeature_options") ? new Choice((JSONObject) json.get("subfeature_options")) : null;
        this.expertise_options = json.containsKey("expertise_options") ? new Choice((JSONObject) json.get("expertise_options")) : null;
    }

    // Getter methods
    public Choice getSubfeatureOptions() {
        return this.subfeature_options;
    }

    public Choice getExpertiseOptions() {
        return this.expertise_options;
    }

}
