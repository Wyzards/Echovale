package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.Echovale;
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

public class Gear extends Equipment {

    private static final Echovale plugin = Echovale.getPlugin(Echovale.class);

    private final String name;
    private final String gear_category_url;
    private final Cost cost;
    private final double weight;

    public Gear(String url) {
        super(url);

        JSONObject json = null;
        try {
            json = APIRequest.request(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        assert json != null;
        this.name = (String) json.get("name");
        this.gear_category_url = (String) ((JSONObject) json.get("gear_category")).get("url");
        this.cost = new Cost((JSONObject) json.get("cost"));

        if (json.containsKey("weight")) {
            if (json.get("weight") instanceof Long)
                this.weight = (double) (long) json.get("weight");
            else
                this.weight = (double) json.get("weight");
        } else
            this.weight = 0;
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(retrieveMaterial(), retrieveQuantity());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + this.name);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + new EquipmentCategory(this.gear_category_url).getName() + " (" + new EquipmentCategory(getEquipmentCategoryUrl()).getName() + ")");
        lore.add("");
        lore.add(ChatColor.GRAY + "Cost: " + this.cost.amountString());
        lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + this.weight + " pounds");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    public int retrieveQuantity() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(getEquipmentCategoryUrl() + "." + getIndex() + ".quantity"))
            return plugin.getConfigManager().getEquipmentConfig().getInt(getEquipmentCategoryUrl() + "." + getIndex() + ".quantity");

        return 1;
    }

    public static Set<Gear> values() {
        Set<Gear> set = Sets.newHashSet();

        try {
            JSONObject json = APIRequest.request("/api/equipment-categories/adventuring-gear");
            JSONArray equipment = (JSONArray) json.get("equipment");

            for (Object object : equipment)
                set.add(new Gear(((String) ((JSONObject) object).get("url"))));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return set;
    }
}
