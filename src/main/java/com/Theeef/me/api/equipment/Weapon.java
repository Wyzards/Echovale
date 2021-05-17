package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.Echovale;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Weapon implements Equipment {

    private static Echovale plugin = Echovale.getPlugin(Echovale.class);

    private final String index;
    private final String name;
    private final String equipment_category_url;
    private final String weapon_category;
    private final String weapon_range;
    private final String category_range;
    private final Cost cost;
    private final Damage damage;
    private final Damage two_handed_damage;
    private final WeaponRange range;
    private final double weight;
    private final List<String> properties;
    private final String url;

    public Weapon(String url) {
        JSONObject json = null;

        try {
            json = APIRequest.request(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.equipment_category_url = (String) ((JSONObject) json.get("equipment_category")).get("url");
        this.weapon_category = (String) json.get("weapon_category");
        this.weapon_range = (String) json.get("weapon_range");
        this.category_range = (String) json.get("category_range");
        this.cost = new Cost((JSONObject) json.get("cost"));

        if (json.containsKey("damage"))
            this.damage = new Damage((JSONObject) json.get("damage"));
        else
            this.damage = null;

        this.two_handed_damage = json.containsKey("two_handed_damage") ? new Damage((JSONObject) json.get("two_handed_damage")) : null;
        this.range = new WeaponRange((JSONObject) json.get("range"));

        if (json.get("weight") instanceof Long)
            this.weight = Double.valueOf((long) json.get("weight"));
        else
            this.weight = (double) json.get("weight");

        this.properties = Lists.newArrayList();

        // Would be better to store index / name so as to reduce queries
        for (Object apiReference : (JSONArray) json.get("properties"))
            this.properties.add((String) ((JSONObject) apiReference).get("url"));

        this.url = url;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(retrieveMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + name);

        // Property Parsing
        String propertyString = ChatColor.GRAY + "Properties: " + ChatColor.WHITE;
        List<String> properties;

        for (String propertyUrl : this.properties)
            propertyString += new WeaponProperty(propertyUrl).getName() + ", ";

        properties = Util.fitForLore(propertyString.substring(0, propertyString.length() - 2));

        for (int i = 1; i < properties.size(); i++)
            properties.set(i, ChatColor.WHITE + properties.get(i));

        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + category_range + " Weapon");
        lore.add("");
        lore.add(ChatColor.GRAY + "Cost: " + cost.amountString());

        if (damage != null)
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.WHITE + damage.getMin() + "-" + damage.getMax() + " " + damage.getType().getName());

        if (two_handed_damage != null)
            lore.add(ChatColor.GRAY + "Two-Handed Damage: " + ChatColor.WHITE + two_handed_damage.getMin() + "-" + two_handed_damage.getMax() + " " + two_handed_damage.getType().getName());

        if (range.getLong() != 0)
            lore.add(ChatColor.GRAY + "Range: " + ChatColor.WHITE + range.getNormal() + " Normal / " + range.getLong() + " Long");
        else if (this.properties.contains("/api/weapon-properties/thrown"))
            lore.add(ChatColor.GRAY + "Thrown Range: " + ChatColor.WHITE + "20 Normal / 60 Long");


        lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + weight + " pounds");

        lore.addAll(properties);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    public Material retrieveMaterial() {

        TWEAK THIS SO FORMAT IS category.index.material
                then allow for .durability as well

        if (plugin.getConfigManager().getEquipmentConfig().contains(this.equipment_category_url + "." + this.index))
            return Material.valueOf(plugin.getConfigManager().getEquipmentConfig().getString(this.equipment_category_url + "." + this.index));
        else {
            plugin.getConfigManager().getEquipmentConfig().set(this.equipment_category_url + "." + this.index, "BARRIER");
            plugin.getConfigManager().saveEquipmentConfig();
        }

        return Material.BARRIER;
    }

    public static Set<Weapon> values() {
        Set<Weapon> set = Sets.newHashSet();

        try {
            JSONObject json = APIRequest.request("/api/equipment-categories/weapon");
            JSONArray equipment = (JSONArray) json.get("equipment");

            for (Object object : equipment)
                if (((String) ((JSONObject) object).get("url")).startsWith("/api/equipment/"))
                    set.add(new Weapon(((String) ((JSONObject) object).get("url"))));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return set;
    }
}
