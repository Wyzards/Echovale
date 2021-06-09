package com.Theeef.me.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Sets;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Gear extends CommonEquipment {

    private final String gear_category_url;
    private final List<String> description;

    public Gear(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);
        assert json != null;
        this.gear_category_url = (String) ((JSONObject) json.get("gear_category")).get("url");

        if (json.containsKey("desc")) {
            this.description = new ArrayList<>();

            for (Object object : ((JSONArray) json.get("desc")))
                this.description.add((String) object);
        } else
            this.description = null;
    }

    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();

        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        // Lore
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + getGearCategory().getName() + " (" + getEquipmentCategory().getName() + ")");
        lore.add("");

        if (this.description != null) {
            for (String string : this.description)
                lore.addAll(Util.fitForLore(ChatColor.GRAY + string));
            lore.add("");
        }

        lore.addAll(loreCostWeight());
        meta.setLore(lore);
        item.setItemMeta(meta);

        // NBT Data
        NBTHandler.addString(item, "gear_category_url", this.gear_category_url);

        return item;
    }

    public static Set<Gear> values() {
        Set<Gear> set = Sets.newHashSet();
        JSONObject json = APIRequest.request("/api/equipment-categories/adventuring-gear");
        JSONArray equipment = (JSONArray) json.get("equipment");

        for (Object object : equipment)
            set.add((Gear) fromString(((String) ((JSONObject) object).get("url"))));

        return set;
    }

    public EquipmentCategory getGearCategory() {
        return new EquipmentCategory(this.gear_category_url);
    }
}
