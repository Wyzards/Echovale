package com.Theeef.me.api.classes;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Feature {

    private final String index;
    private final String name;
    private final long level;
    private final APIReference dndclass;
    private final APIReference subclass;
    private final List<String> desc;
    private final String url;

    public Feature(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.level = (long) json.get("level");
        this.dndclass = new APIReference((JSONObject) json.get("class"));
        this.subclass = json.containsKey("subclass") ? new APIReference((JSONObject) json.get("subclass")) : null;
        this.desc = new ArrayList<>();
        this.url = url;

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);
    }

    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public long getLevel() {
        return this.level;
    }

    public DNDClass getDNDClass() {
        return new DNDClass(this.dndclass.getUrl());
    }

    public Subclass getSubclass() {
        return new Subclass(this.subclass.getUrl());
    }

    public List<String> getDescription() {
        return this.desc;
    }

    public String getUrl() {
        return this.url;
    }

}
