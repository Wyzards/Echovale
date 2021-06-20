package com.Theeef.me.api.spells;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Spell {

    private final String index;
    private final List<String> desc;
    private final List<String> higher_level;
    private final String range;
    private final List<String> components;
    private final String material;
    private final boolean ritual;
    private final String duration;
    private final boolean concentration;
    private final String casting_time;
    private final long level;
    private final String attack_type;
    private final SpellDamage damage;
    private final APIReference school;
    private final List<APIReference> classes;
    private final List<APIReference> subclasses;
    private final String url;

    public Spell(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.desc = new ArrayList<>();
        this.higher_level = new ArrayList<>();
        this.range = (String) json.get("range");
        this.components = new ArrayList<>();
        this.material = (String) json.get("material");
        this.ritual = (boolean) json.get("ritual");
        this.duration = (String) json.get("duration");
        this.concentration = (boolean) json.get("concentration");
        this.casting_time = (String) json.get("casting_time");
        this.level = (long) json.get("level");
        this.attack_type = (String) json.get("attack_type");
        this.damage = json.containsKey("damage") ? new SpellDamage((JSONObject) json.get("damage")) : null;
        this.school = new APIReference((JSONObject) json.get("school"));
        this.classes = new ArrayList<>();
        this.subclasses = new ArrayList<>();
        this.url = (String) json.get("url");

        for (Object desc : (JSONArray) json.get("desc"))
            this.desc.add((String) desc);

        if (json.containsKey("higher_level"))
            for (Object desc : (JSONArray) json.get("higher_level"))
                this.higher_level.add((String) desc);

        for (Object component : (JSONArray) json.get("components"))
            this.components.add((String) component);

        for (Object classObj : ((JSONArray) json.get("classes")))
            this.classes.add(new APIReference((JSONObject) classObj));

        for (Object subclassObj : ((JSONArray) json.get("subclasses")))
            this.subclasses.add(new APIReference((JSONObject) subclassObj));
    }

    public static List<Spell> values() {
        List<Spell> list = new ArrayList<>();
        JSONObject json = APIRequest.request("/api/spells");

        for (Object spellJson : (JSONArray) json.get("results"))
            list.add(new Spell((String) ((JSONObject) spellJson).get("url")));

        return list;
    }

    // Referenced getters
    public MagicSchool getSchool() {
        return new MagicSchool(this.school.getUrl());
    }

    public List<DNDClass> getClasses() {
        List<DNDClass> list = new ArrayList<>();

        for (APIReference reference : this.classes)
            list.add(new DNDClass(reference.getUrl()));

        return list;
    }

    public List<Subclass> getSubclasses() {
        List<Subclass> list = new ArrayList<>();

        for (APIReference reference : this.subclasses)
            list.add(new Subclass(reference.getUrl()));

        return list;
    }

    // Raw getter methods
    public String getIndex() {
        return this.index;
    }

    public List<String> getDescription() {
        return this.desc;
    }

    public List<String> getHigherLevelDescription() {
        return this.higher_level;
    }

    public String getRange() {
        return this.range;
    }

    public List<String> getComponents() {
        return this.components;
    }

    public String getMaterials() {
        return this.material;
    }

    public boolean canBeRitual() {
        return this.ritual;
    }

    public String getDuration() {
        return this.duration;
    }

    public boolean requiresConcentration() {
        return this.concentration;
    }

    public String getCastingTime() {
        return this.casting_time;
    }

    public long getLevel() {
        return this.level;
    }

    public String getAttackType() {
        return this.attack_type;
    }

    public SpellDamage getDamage() {
        return this.damage;
    }

    public String getUrl() {
        return this.url;
    }
}