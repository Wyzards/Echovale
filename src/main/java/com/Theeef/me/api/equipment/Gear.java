package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
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

public class Gear extends Equipment {

    private final APIReference gear_category;
    private final List<String> description;
    private final long quantity;
    private final Cost cost;
    private final double weight;

    public Gear(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);
        this.gear_category = new APIReference((JSONObject) json.get("gear_category"));

        if (json.containsKey("desc")) {
            this.description = new ArrayList<>();

            for (Object object : ((JSONArray) json.get("desc")))
                this.description.add((String) object);
        } else
            this.description = null;

        this.quantity = json.containsKey("quantity") ? (long) json.get("quantity") : 1;
        this.cost = new Cost((JSONObject) json.get("cost"));
        this.weight = Equipment.parseWeight(json);
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

        lore.add(ChatColor.GRAY + getGearCategory().getName() + " (" + getEquipmentCategory().getName() + ")");

        if (this.description != null) {
            lore.add("");
            for (String string : this.description)
                lore.addAll(Util.fitForLore(ChatColor.GRAY + string));
        }

        if (getCost().getCopperValue() > 0 || getWeight() > 0)
            lore.add("");
        if (getCost().getCopperValue() > 0)
            lore.add(ChatColor.GRAY + "Cost: " + getCost().amountString());
        if (getWeight() > 0)
            lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + getWeight() + " pounds");

        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    // Getter methods
    public EquipmentCategory getGearCategory() {
        return new EquipmentCategory(this.gear_category);
    }

    public List<String> getDescription() {
        return this.description;
    }

    @Override
    public Cost getCost() {
        return this.cost.clone().multiply(1.0 / this.quantity);
    }

    @Override
    public double getWeight() {
        return this.weight / this.quantity;
    }

    // Static methods
    public static Set<Gear> values() {
        Set<Gear> set = Sets.newHashSet();
        JSONObject json = APIRequest.request("/api/equipment-categories/adventuring-gear");
        JSONArray equipment = (JSONArray) json.get("equipment");

        for (Object object : equipment)
            set.add((Gear) fromString(((String) ((JSONObject) object).get("url"))));

        return set;
    }
}
