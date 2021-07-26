package com.Theeef.me.api.chardata;

import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.APIRequest;
import com.google.common.collect.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AbilityScore {

    public static AbilityScore CHA = new AbilityScore("/api/ability-scores/cha");
    public static AbilityScore CON = new AbilityScore("/api/ability-scores/con");
    public static AbilityScore DEX = new AbilityScore("/api/ability-scores/dex");
    public static AbilityScore INT = new AbilityScore("/api/ability-scores/int");
    public static AbilityScore STR = new AbilityScore("/api/ability-scores/str");
    public static AbilityScore WIS = new AbilityScore("/api/ability-scores/wis");

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

    public AbilityScore(APIReference reference) {
        this(reference.getUrl());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof AbilityScore && ((AbilityScore) object).getIndex().equals(getIndex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), getIndex());
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

    public List<Skill> getSkills() {
        List<Skill> list = new ArrayList<>();

        for (APIReference skillReference : this.skills)
            list.add(new Skill(skillReference.getUrl()));

        return list;
    }

    public String getUrl() {
        return this.url;
    }

    // Static methods
    public static List<AbilityScore> values() {
        return Lists.newArrayList(CHA, CON, DEX, INT, STR, WIS);
    }

    public static AbilityScore fromIndex(String index) {
        return new AbilityScore("/api/ability-scores/" + index);
    }
}
