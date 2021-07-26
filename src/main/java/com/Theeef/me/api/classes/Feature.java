package com.Theeef.me.api.classes;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.classes.subclasses.FeatureSpecific;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.races.Trait;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Feature {

    private final String index;
    private final String name;
    private final long level;
    private final APIReference dndclass;
    private final APIReference subclass;
    private final List<String> desc;
    private final APIReference parent;
    private final FeatureSpecific feature_specific;
    private final String url;

    public Feature(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.level = json.containsKey("level") ? (long) json.get("level") : 0;
        this.dndclass = new APIReference((JSONObject) json.get("class"));
        this.subclass = json.containsKey("subclass") ? new APIReference((JSONObject) json.get("subclass")) : null;
        this.desc = new ArrayList<>();
        this.parent = json.containsKey("parent") ? new APIReference((JSONObject) json.get("parent")) : null;
        this.feature_specific = json.containsKey("feature_specific") ? new FeatureSpecific((JSONObject) json.get("feature_specific")) : null;
        this.url = url;

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);
    }

    public Feature(APIReference reference) {
        this(reference.getUrl());
    }

    // Getter methods
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

    public Feature getParent() {
        return new Feature(this.parent);
    }

    public FeatureSpecific getFeatureSpecific() {
        return this.feature_specific;
    }

    public String getUrl() {
        return this.url;
    }

    // Static Methods
    public static Set<Feature> values() {
        Set<Feature> set = new HashSet<>();
        JSONArray results = (JSONArray) APIRequest.request("/api/features/").get("results");

        for (Object result : results)
            set.add(new Feature((String) ((JSONObject) result).get("url")));

        return set;
    }

}
