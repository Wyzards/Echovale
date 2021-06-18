package com.Theeef.me.chardata;

import com.Theeef.me.common.APIReference;
import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Skill {

    private final String index;
    private final String name;
    private final List<String> desc;
    private final APIReference ability_score;
    private final String url;

    public Skill(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.desc = new ArrayList<>();
        this.ability_score = new APIReference((JSONObject) json.get("ability_score"));
        this.url = url;

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getDescription() {
        return this.desc;
    }

    public AbilityScore getAbilityScore() {
        return new AbilityScore(this.ability_score.getUrl());
    }

    public String getUrl() {
        return this.url;
    }

}
