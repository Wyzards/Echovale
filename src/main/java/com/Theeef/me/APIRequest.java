package com.Theeef.me;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class APIRequest {

    public static HashMap<String, JSONObject> cachedRequests = new HashMap<String, JSONObject>();

    public static JSONObject request(String path) throws IOException, ParseException {
        if (cachedRequests.containsKey(path))
            return cachedRequests.get(path);

        URL url = new URL("https://www.dnd5eapi.co" + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content.toString());

        cachedRequests.put(path, json);

        return json;
    }
}
