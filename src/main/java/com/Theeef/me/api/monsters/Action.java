package com.Theeef.me.api.monsters;

import com.Theeef.me.api.mechanics.Damage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Action {

    private final String name;
    private final String desc;
    private final Usage usage;
    private final DC dc;
    private final List<Damage> damage;

    public Action(JSONObject json) {
        this.name = (String) json.get("name");
        this.desc = (String) json.get("desc");
        this.usage = new Usage((JSONObject) json.get("usage"));
        this.dc = new DC((JSONObject) json.get("dc"));
        this.damage = new ArrayList<>();

        if (json.containsKey("damage"))
            for (Object damageObj : (JSONArray) json.get("damage"))
                this.damage.add(new Damage((JSONObject) damageObj));
    }

    // Getter methods
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.desc;
    }

    public Usage getUsage() {
        return this.usage;
    }

    public DC getDC() {
        return this.dc;
    }

    public List<Damage> getDamage() {
        return this.damage;
    }
}
