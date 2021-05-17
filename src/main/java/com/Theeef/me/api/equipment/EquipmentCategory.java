package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class EquipmentCategory {

    private String index;
    private String name;
    // private Equipment[] equipment; Leaving this out temporarily

    public EquipmentCategory(String url) {
        if (!url.substring("/api/equipment-categories/".length()).equals("/api/equipment-categories/"))
            throw new IllegalArgumentException("URL used for EquipmentCategory constructor was not an equipment category");

        try {
            JSONObject json = APIRequest.request(url);
            this.name = (String) json.get("name");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
