package com.Theeef.me.common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Info {

    private final String name;
    private final List<String> desc;

    public Info(JSONObject json) {
        this.name = (String) json.get("name");
        this.desc = new ArrayList<>();

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);
    }

    // Get methods
    public String getName() {
        return this.name;
    }

    public List<String> getDescription() {
        return this.desc;
    }

}
