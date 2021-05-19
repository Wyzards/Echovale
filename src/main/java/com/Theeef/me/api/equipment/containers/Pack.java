package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.equipment.Gear;
import com.google.common.collect.Sets;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Pack extends Gear {

    private final String container_url;
    private final HashMap<String, Long> contents_urls; // Hashmap of equipment URL w/ quantity, excluding container item

    public Pack(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);

        assert json != null;
        this.contents_urls = new HashMap<>();
        JSONArray contents = (JSONArray) json.get("contents");
        this.container_url = (String) ((JSONObject) ((JSONObject) contents.get(0)).get("item")).get("url");

        for (int i = 1; i < contents.size(); i++)
            this.contents_urls.put((String) ((JSONObject) ((JSONObject) contents.get(i)).get("item")).get("url"), (long) ((JSONObject) contents.get(i)).get("quantity"));
    }

    public ItemStack getItemStack() {
        List<ContainerEquipment> contents = new ArrayList<>();

        for (String equipment_url : this.contents_urls.keySet())
            contents.add(new ContainerEquipment(equipment_url, this.contents_urls.get(equipment_url)));

        Container container = new Container(this.container_url, getName(), contents);
        return container.getItemStack();
    }

    public static Set<Pack> packValues() {
        Set<Pack> set = Sets.newHashSet();
        JSONObject json = APIRequest.request("/api/equipment-categories/equipment-packs/");
        JSONArray equipment = (JSONArray) json.get("equipment");

        for (Object object : equipment)
            set.add(new Pack(((String) ((JSONObject) object).get("url"))));

        return set;
    }
}
