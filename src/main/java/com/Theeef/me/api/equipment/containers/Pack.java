package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.equipment.Equipment;
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

    private final APIReference container_material; // The equipment the container takes the form of. Ex: backpack, sack, pouch, etc.
    private final List<EquipmentQuantity> contents;

    public Pack(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);
        JSONArray contentsArray = (JSONArray) json.get("contents");
        this.container_material = new APIReference((JSONObject) ((JSONObject) contentsArray.get(0)).get("item"));
        this.contents = new ArrayList<>();

        for (int i = 1; i < contentsArray.size(); i++)
            this.contents.add(new EquipmentQuantity((JSONObject) contentsArray.get(i)));
    }

    public ItemStack getItemStack() {
        return new Container(this.container_material.getUrl(), getName(), getCost(), this.contents).getItemStack();
    }

    // Getter methods
    public Equipment getContainerMaterial() {
        return Equipment.fromString(this.container_material.getUrl());
    }

    public List<EquipmentQuantity> getContents() {
        return this.contents;
    }

    // Static methods
    public static Set<Pack> packValues() {
        Set<Pack> set = Sets.newHashSet();
        JSONObject json = APIRequest.request("/api/equipment-categories/equipment-packs/");
        JSONArray equipment = (JSONArray) json.get("equipment");

        for (Object object : equipment)
            set.add(new Pack(((String) ((JSONObject) object).get("url"))));

        return set;
    }
}
