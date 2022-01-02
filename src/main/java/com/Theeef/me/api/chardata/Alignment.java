package com.Theeef.me.api.chardata;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Alignment {

    private final String index;
    private final String name;
    private final String abbreviation;
    private final String desc;
    private final String url;

    public Alignment(String url) {
        JSONObject json = APIRequest.request(url);

        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.abbreviation = (String) json.get("abbreviation");
        this.desc = (String) json.get("desc");
        this.url = url;
    }

    public Alignment(APIReference reference) {
        this(reference.getUrl());
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

    public String getDescription() {
        return this.desc;
    }

    public String getUrl() {
        return this.url;
    }

}
