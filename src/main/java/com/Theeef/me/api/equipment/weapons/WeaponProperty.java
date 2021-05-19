package com.Theeef.me.api.equipment.weapons;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

public class WeaponProperty {

    private final String index;
    private final String name;
    private final String[] desc;
    private final String url;

    public WeaponProperty(String url) {
        JSONObject object = APIRequest.request(url);

        this.index = (String) object.get("index");
        this.name = (String) object.get("name");

        Object[] properties = ((JSONArray) object.get("desc")).toArray();
        this.desc = Arrays.copyOf(properties, properties.length, String[].class);
        this.url = url;
    }

    public String getName() {
        return this.name;
    }
}
