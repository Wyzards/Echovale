package com.Theeef.me.api.equipment.weapons;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.api.mechanics.Damage;
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

public class Weapon extends Equipment {

    private final String weapon_category;
    private final String weapon_range;
    private final String category_range;
    private final Cost cost;
    private final WeaponRange range;
    private final Damage damage;
    private final Damage two_handed_damage;
    private final double weight;
    private final List<APIReference> properties;

    public Weapon(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);
        this.weapon_category = (String) json.get("weapon_category");
        this.weapon_range = (String) json.get("weapon_range");
        this.category_range = (String) json.get("category_range");
        this.cost = new Cost((JSONObject) json.get("cost"));
        this.range = new WeaponRange((JSONObject) json.get("range"));
        this.damage = json.containsKey("damage") ? new Damage((JSONObject) json.get("damage")) : null;
        this.two_handed_damage = json.containsKey("two_handed_damage") ? new Damage((JSONObject) json.get("two_handed_damage")) : null;
        this.weight = Equipment.parseWeight(json);
        this.properties = new ArrayList<>();

        for (Object property : (JSONArray) json.get("properties"))
            this.properties.add(new APIReference((JSONObject) property));
    }

    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();

        lore(item);

        return item;
    }

    // Helper methods
    private void lore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + this.category_range + " Weapon");
        lore.add("");
        if (getCost().getCopperValue() > 0)
            lore.add(ChatColor.GRAY + "Cost: " + getCost().amountString());
        if (getWeight() > 0)
            lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + getWeight() + " pounds");
        lore.add("");

        if (damage != null)
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.WHITE + damage.getRoll().getMin() + "-" + damage.getRoll().getMax() + " " + damage.getType().getName());

        if (two_handed_damage != null)
            lore.add(ChatColor.GRAY + "Two-Handed Damage: " + ChatColor.WHITE + two_handed_damage.getRoll().getMin() + "-" + two_handed_damage.getRoll().getMax() + " " + two_handed_damage.getType().getName());

        if (range.getLong() != 0)
            lore.add(ChatColor.GRAY + "Range: " + ChatColor.WHITE + range.getNormal() + " Normal / " + range.getLong() + " Long");
        else if (this.properties.contains("/api/weapon-properties/thrown"))
            lore.add(ChatColor.GRAY + "Thrown Range: " + ChatColor.WHITE + "20 Normal / 60 Long");

        if (this.properties.size() > 0)
            lore.addAll(Util.fitForLore(propertyString()));

        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private String propertyString() {
        StringBuilder propertyString = new StringBuilder(ChatColor.GRAY + "Properties: " + ChatColor.WHITE);

        for (APIReference property : this.properties)
            propertyString.append(ChatColor.WHITE).append(new WeaponProperty(property).getName()).append(", ");

        propertyString.setLength(propertyString.length() - 2);

        return propertyString.toString();
    }

    // Getter methods
    public String getWeaponCategory() {
        return this.weapon_category;
    }

    public String getWeaponRange() {
        return this.weapon_range;
    }

    public String getCategoryRange() {
        return this.category_range;
    }

    @Override
    public Cost getCost() {
        return this.cost.clone();
    }

    public WeaponRange getRange() {
        return this.range;
    }

    public Damage getDamage() {
        return this.damage;
    }

    public Damage getTwoHandedDamage() {
        return this.two_handed_damage;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    public List<WeaponProperty> getProperties() {
        List<WeaponProperty> list = new ArrayList<>();

        for (APIReference property : this.properties)
            list.add(new WeaponProperty(property));

        return list;
    }

    // Static methods
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
