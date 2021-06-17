package com.Theeef.me;

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
