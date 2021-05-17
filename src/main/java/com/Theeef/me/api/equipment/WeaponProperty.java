package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WeaponProperty {

    private final String index;
    private final String name;
    private final String[] desc;
    private final String url;

    public WeaponProperty(String url) {
        JSONObject object = null;
        try {
            object = APIRequest.request(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

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
