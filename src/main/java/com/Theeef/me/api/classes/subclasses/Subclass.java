package com.Theeef.me.api.classes.subclasses;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.Level;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.spells.Spell;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Subclass {

    private final String index;
    private final APIReference dndclass;
    private final String name;
    private final String subclass_flavor;
    private final List<String> desc;
    private final List<SubclassSpell> spells;
    private final String subclass_levels;
    private final String url;

    public Subclass(String url) {
        JSONObject json = APIRequest.request(url);
        this.index = (String) json.get("index");
        this.dndclass = new APIReference((JSONObject) json.get("class"));
        this.name = (String) json.get("name");
        this.subclass_flavor = (String) json.get("subclass_flavor");
        this.desc = new ArrayList<String>();
        this.spells = new ArrayList<>();
        this.subclass_levels = (String) json.get("subclass_levels");
        this.url = url;

        for (Object descLine : (JSONArray) json.get("desc"))
            this.desc.add((String) descLine);

        if (json.containsKey("spells"))
            for (Object subclassSpell : (JSONArray) json.get("spells"))
                this.spells.add(new SubclassSpell((JSONObject) subclassSpell));
    }

    public Subclass(APIReference reference) {
        this(reference.getUrl());
    }

    // Getter methods
    public String getIndex() {
        return this.index;
    }

    public DNDClass getParentClass() {
        return new DNDClass(this.dndclass.getUrl());
    }

    public String getName() {
        return this.name;
    }

    public String getFlavorText() {
        return this.subclass_flavor;
    }

    public List<String> getDescription() {
        return this.desc;
    }

    public List<SubclassSpell> getSpells() {
        return this.spells;
    }

    public HashMap<Integer, List<Spell>> getSpellsByLevel() {
        HashMap<Integer, List<Spell>> spells = Maps.newHashMap();

        for (SubclassSpell spell : getSpells())
            if (spells.containsKey((int) spell.getSpell().getLevel()))
                spells.get((int) spell.getSpell().getLevel()).add(spell.getSpell());
            else
                spells.put((int) spell.getSpell().getLevel(), Lists.newArrayList(spell.getSpell()));

        return spells;
    }

    public List<Level> getSublassLevels() {
        List<Level> list = new ArrayList<>();
        JSONArray array = (JSONArray) APIRequest.requestAware(this.subclass_levels);

        for (Object level : array)
            list.add(new Level((JSONObject) level));

        return list;
    }

    public String getUrl() {
        return this.url;
    }

    // Static methods
    public static Subclass fromIndex(String index) {
        return new Subclass("/api/subclasses/" + index);
    }

}
