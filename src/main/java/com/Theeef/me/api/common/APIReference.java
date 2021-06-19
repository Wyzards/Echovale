package com.Theeef.me.api.common;

import org.json.simple.JSONObject;

public class APIReference {

    private final String index;
    private final String name;
    private final String url;

    public APIReference(JSONObject json) {
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.url = (String) json.get("url");
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }
}
