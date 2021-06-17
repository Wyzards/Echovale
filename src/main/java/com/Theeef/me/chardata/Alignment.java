package com.Theeef.me.chardata;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Alignment {

    private final String index;
    private final String name;
    private final String abbreviation;
    private final List<String> desc;
    private final String url;

    public Alignment(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.abbreviation = (String) json.get("abbreviation");
        this.desc = new ArrayList<>();
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

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public List<String> getDescription() {
        return this.desc;
    }

    public String getUrl() {
        return this.url;
    }

}
