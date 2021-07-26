package com.Theeef.me.api.monsters;

import org.json.simple.JSONObject;

public class Usage {

    private final String type;
    private final long times;

    public Usage(JSONObject json) {
        this.type = (String) json.get("type");
        this.times = (long) json.get("times");
    }

    // Getter methods
    public String getType() {
        return this.type;
    }

    public long getTimes() {
        return this.times;
    }

}
