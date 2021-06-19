package com.Theeef.me.api.chardata;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Language {

    private final String index;
    private final String name;
    private final String type;
    private final List<String> typical_speakers;
    private final String script;
    private final String url;

    public Language(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.type = (String) json.get("type");
        this.typical_speakers = new ArrayList<>();
        this.script = (String) json.get("script");
        this.url = url;

        for (Object speakerObj : (JSONArray) json.get("typical_speakers"))
            this.typical_speakers.add((String) speakerObj);
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public List<String> getTypicalSpeakers() {
        return this.typical_speakers;
    }

    public String getScript() {
        return this.script;
    }

    public String getUrl() {
        return this.url;
    }

}
