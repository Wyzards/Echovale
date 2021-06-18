package com.Theeef.me.backgrounds;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BackgroundFeature {

    private final List<String> desc;
    private final String name;

    public BackgroundFeature(JSONObject json) {
        this.desc = new ArrayList<>();
        this.name = (String) json.get("name");

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);
    }

    // Get methods
    public List<String> getDesc() {
        return this.desc;
    }

    public String getName() {
        return this.name;
    }

}
