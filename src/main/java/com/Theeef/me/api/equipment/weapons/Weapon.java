package com.Theeef.me.api.equipment.weapons;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.mechanics.Damage;
import com.Theeef.me.api.equipment.CommonEquipment;
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

public class Weapon extends CommonEquipment {

    private final String weapon_category;
    private final String weapon_range;
    private final String category_range;
    private final WeaponRange range;
    private final Damage damage;
    private final Damage two_handed_damage;
    private final List<String> properties;

    public Weapon(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);

        assert json != null;
        this.weapon_category = (String) json.get("weapon_category");
        this.weapon_range = (String) json.get("weapon_range");
        this.category_range = (String) json.get("category_range");
        this.range = new WeaponRange((JSONObject) json.get("range"));
        this.damage = json.containsKey("damage") ? new Damage((JSONObject) json.get("damage")) : null;
        this.two_handed_damage = json.containsKey("two_handed_damage") ? new Damage((JSONObject) json.get("two_handed_damage")) : null;
        this.properties = new ArrayList<>();

        // TODO: Would be better to store index / name so as to reduce queries
        for (Object apiReference : (JSONArray) json.get("properties"))
            this.properties.add((String) ((JSONObject) apiReference).get("url"));
    }

    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        // Item Lore
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + this.category_range + " Weapon");
        lore.add("");
        lore.addAll(loreCostWeight());
        lore.add("");

        if (damage != null)
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.WHITE + damage.getMin() + "-" + damage.getMax() + " " + damage.getType().getName());

        if (two_handed_damage != null)
            lore.add(ChatColor.GRAY + "Two-Handed Damage: " + ChatColor.WHITE + two_handed_damage.getMin() + "-" + two_handed_damage.getMax() + " " + two_handed_damage.getType().getName());

        if (range.getLong() != 0)
            lore.add(ChatColor.GRAY + "Range: " + ChatColor.WHITE + range.getNormal() + " Normal / " + range.getLong() + " Long");
        else if (this.properties.contains("/api/weapon-properties/thrown"))
            lore.add(ChatColor.GRAY + "Thrown Range: " + ChatColor.WHITE + "20 Normal / 60 Long");

        if (this.properties.size() > 0)
            lore.addAll(Util.fitForLore(propertyString()));

        meta.setLore(lore);
        item.setItemMeta(meta);

        // NBT Data
        NBTHandler.addString(item, "weapon_category", this.weapon_category);
        NBTHandler.addString(item, "weapon_range", this.weapon_range);
        NBTHandler.addString(item, "category_range", this.category_range);
        NBTHandler.addString(item, "range_normal", String.valueOf(this.range.getNormal()));
        NBTHandler.addString(item, "range_long", String.valueOf(this.range.getLong()));

        if (this.damage != null) {
            NBTHandler.addString(item, "damage_min", String.valueOf(this.damage.getMin()));
            NBTHandler.addString(item, "damage_max", String.valueOf(this.damage.getMax()));
            NBTHandler.addString(item, "damage_type", this.damage.getType().getName());
        }

        if (this.two_handed_damage != null) {
            NBTHandler.addString(item, "two_handed_damage_min", String.valueOf(this.two_handed_damage.getMin()));
            NBTHandler.addString(item, "two_handed_damage_max", String.valueOf(this.two_handed_damage.getMax()));
            NBTHandler.addString(item, "two_handed_damage_type", this.two_handed_damage.getType().getName());
        }

        return item;
    }

    private String propertyString() {
        StringBuilder propertyString = new StringBuilder(ChatColor.GRAY + "Properties: " + ChatColor.WHITE);

        for (String propertyUrl : this.properties)
            propertyString.append(ChatColor.WHITE).append(new WeaponProperty(propertyUrl).getName()).append(", ");

        propertyString.setLength(propertyString.length() - 2);

        return propertyString.toString();
    }

    public static Set<Weapon> values() {
        Set<Weapon> set = Sets.newHashSet();
        JSONObject json = APIRequest.request("/api/equipment-categories/weapon");
        JSONArray equipment = (JSONArray) json.get("equipment");

        for (Object object : equipment)
            if (((String) ((JSONObject) object).get("url")).startsWith("/api/equipment/"))
                set.add(new Weapon(((String) ((JSONObject) object).get("url"))));

        return set;
    }
}
