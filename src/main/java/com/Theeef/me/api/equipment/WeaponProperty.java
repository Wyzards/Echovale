package com.Theeef.me.api.equipment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WeaponProperty {

    private String name;
    private String desc;
    private String url;

    public WeaponProperty(String name, String desc, String url) {
        this.name = name;
        this.desc = desc;
        this.url = url;
    }

    public static WeaponProperty fromIndex(String index) {
        try {
            URL url = new URL("https://www.dnd5eapi.co/api/weapon-properties/" + index);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Map<String, String> parameters = new HashMap<>();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            System.out.println("CONTENT: " + content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
