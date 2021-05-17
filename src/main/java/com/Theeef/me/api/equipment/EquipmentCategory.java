package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentCategory {

    private final String index;
    private final String name;
    private final List<String> equipment;

    public EquipmentCategory(String url) {
        JSONObject json = null;
        try {
            json = APIRequest.request(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        assert json != null;
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.equipment = new ArrayList<>();

        for (Object arrayObj : ((JSONArray) json.get("equipment")))
            this.equipment.add((String) ((JSONObject) arrayObj).get("url"));
    }

    public String getName() {
        return this.name;
    }
}