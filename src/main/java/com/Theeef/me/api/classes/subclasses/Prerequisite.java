package com.Theeef.me.api.classes.subclasses;

import org.json.simple.JSONObject;

public class Prerequisite {

    private final String index;
    private final String type;
    private final String name;
    private final String url;

    public Prerequisite(JSONObject json) {
        this.index = (String) json.get("index");
        this.type = (String) json.get("type");
        this.name = (String) json.get("name");
        this.url = (String) json.get("url");
    }


    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

}
