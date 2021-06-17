package com.Theeef.me.spells;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONObject;

public class MagicSchool {

    private final String index;
    private final String name;
    private final String desc;
    private final String url;

    public MagicSchool(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.desc = (String) json.get("desc");
        this.url = (String) json.get("url");
    }

    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getUrl() {
        return this.url;
    }
}
