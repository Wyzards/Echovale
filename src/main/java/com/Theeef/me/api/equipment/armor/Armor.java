package com.Theeef.me.api.equipment.armor;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Armor extends Equipment {

    private final String armor_category;
    private final ArmorClass armor_class;
    private final long str_minimum;
    private final boolean stealth_disadvantage;
    private final double weight;
    private final Cost cost;

    public Armor(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);

        this.armor_category = (String) json.get("armor_category");
        this.armor_class = new ArmorClass((JSONObject) json.get("armor_class"));
        this.str_minimum = (long) json.get("str_minimum");
        this.stealth_disadvantage = (boolean) json.get("stealth_disadvantage");
        this.weight = Equipment.parseWeight(json);
        this.cost = new Cost((JSONObject) json.get("cost"));
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        item.setItemMeta(meta);

        lore(item);

        return item;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public Cost getCost() {
        return this.cost;
    }

    // Helper methods
    private void lore(ItemStack item) {
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();

        lore.add(ChatColor.GRAY + this.getArmorCategory() + " (" + this.getEquipmentCategory().getName() + ")");
        lore.add("");
        lore.add(ChatColor.GRAY + "Cost: " + getCost().amountString());
        lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + getWeight() + " pounds");
        lore.add("");

        if (getIndex().equals("shield"))
            lore.add(ChatColor.GRAY + "Armor Class: " + ChatColor.WHITE + "+" + this.armor_class.getBase() + " while holding");
        else {
            lore.add(ChatColor.GRAY + "Armor Class: " + ChatColor.WHITE + this.armor_class.getBase() + (this.armor_class.hasDexBonus() ? " + DEX Modifier" + (this.armor_class.getMaxDexBonus() > 0 ? " (Max of " + this.armor_class.getMaxDexBonus() + ")" : "") : ""));
            if (this.str_minimum > 0)
                lore.add(ChatColor.GRAY + "Strength Requirement: " + ChatColor.WHITE + this.str_minimum);

            if (this.stealth_disadvantage) {
                lore.add("");
                lore.add(ChatColor.RED + "Wearing this item gives disadvantage on Stealth");
            }
        }

        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    // Getter methods
    public String getArmorCategory() {
        return this.armor_category;
    }

    public ArmorClass getArmorClass() {
        return this.armor_class;
    }

    public long getStrengthRequirement() {
        return this.str_minimum;
    }

    public boolean givesStealthDisadvantage() {
        return this.stealth_disadvantage;
    }

    public static List<Armor> values() {
        List<Armor> list = new ArrayList<>();
        JSONArray armor = (JSONArray) APIRequest.request("/api/equipment-categories/armor").get("equipment");

        for (Object armorSetObj : armor)
            if (!((String) ((JSONObject) armorSetObj).get("url")).startsWith("/api/magic-items/"))
                list.add((Armor) Equipment.fromString((String) ((JSONObject) armorSetObj).get("url")));

        return list;
    }
}
