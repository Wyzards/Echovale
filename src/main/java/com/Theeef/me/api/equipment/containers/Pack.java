package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.EquipmentCategory;
import com.Theeef.me.util.NBTHandler;
import com.google.common.collect.Sets;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Pack extends Container {

    private final String index;
    private final String name;
    private final APIReference equipment_category;
    private final APIReference gear_category;
    private final Cost cost;
    private final List<ItemQuantity> contents; // Default contents, current contents are handled by Container superclass
    private final String url;


    public Pack(String url, UUID uuid, List<ItemQuantity> contents) {
        super(getPackMaterial(url), uuid, contents);

        JSONObject json = APIRequest.request(url);
        JSONArray contentsArray = (JSONArray) json.get("contents");
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.equipment_category = new APIReference((JSONObject) json.get("equipment_category"));
        this.gear_category = new APIReference((JSONObject) json.get("gear_category"));
        this.cost = new Cost((JSONObject) json.get("cost"));
        this.contents = new ArrayList<>();
        this.url = url;

        for (int i = 1; i < contentsArray.size(); i++)
            this.contents.add(new ItemQuantity((JSONObject) contentsArray.get(i)));
    }

    public Pack(String url) {
        this(url, UUID.randomUUID(), new ArrayList<>());

        setContents(this.contents);
    }

    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();

        NBTHandler.addString(item, "packUrl", this.url);

        return item;
    }

    // Getter methods
    public String getPackIndex() {
        return this.index;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public EquipmentCategory getEquipmentCategory() {
        return new EquipmentCategory(this.equipment_category);
    }

    public EquipmentCategory getGearCategory() {
        return new EquipmentCategory(this.gear_category);
    }

    @Override
    public Cost getCost() {
        if (this.contents.equals(super.getContents()))
            return this.cost;
        else
            return super.getCost();
    }

    public List<ItemQuantity> getDefaultContents() {
        return this.contents;
    }

    public String getPackUrl() {
        return this.url;
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

    public static boolean isPack(ItemStack item) {
        return NBTHandler.hasString(item, "packUrl");
    }

    public static Pack getPackFromItem(ItemStack item) {
        String url = NBTHandler.getString(item, "packUrl");
        UUID uuid = Container.getContainerUUID(item);
        List<ItemQuantity> contents = Container.getItemContents(item);

        return new Pack(url, uuid, contents);
    }

    private static String getPackMaterial(String packUrl) {
        return new ItemQuantity((JSONObject) ((JSONArray) APIRequest.request(packUrl).get("contents")).get(0)).getItem().getUrl();
    }
}
