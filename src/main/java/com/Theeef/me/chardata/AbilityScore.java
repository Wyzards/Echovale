package com.Theeef.me.chardata;

import com.Theeef.me.APIReference;
import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AbilityScore {

    private final String index;
    private final String name;
    private final String full_name;
    private final List<String> desc;
    private final List<APIReference> skills;
    private final String url;

    public AbilityScore(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.full_name = (String) json.get("full_name");
        this.desc = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.url = url;

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);

        for (Object skillReference : (JSONArray) json.get("skills"))
            this.skills.add(new APIReference((JSONObject) skillReference));
    }

    // APIReference get methods
    public List<Skill> getSkills() {
        List<Skill> list = new ArrayList<>();

        for (APIReference skillReference : this.skills)
            list.add(new Skill(skillReference.getUrl()));

        return list;
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.full_name;
    }

    public List<String> getDescription() {
        return this.desc;
    }

    public String getUrl() {
        return this.url;
    }

}