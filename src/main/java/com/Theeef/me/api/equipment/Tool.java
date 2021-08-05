package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tool extends Equipment {

    private String tool_category;
    private Cost cost;
    private double weight;
    private List<String> desc;

    public Tool(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);
        this.tool_category = (String) json.get("tool_category");
        this.cost = new Cost((JSONObject) json.get("cost"));
        this.weight = Equipment.parseWeight(json);
        this.desc = new ArrayList<>();

        for (Object line : (JSONArray) json.get("desc"))
            this.desc.add((String) line);
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();

        lore(item);

        return item;
    }

    // Helper methods
    private void lore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + getToolCategory() + " (" + getEquipmentCategory().getName() + ")");

        if (this.desc != null)
            for (String string : this.desc) {
                lore.add("");
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
    @Override
    public Cost getCost() {
        return this.cost;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    public String getToolCategory() {
        return this.tool_category;
    }

    public List<String> getDescription() {
        return this.desc;
    }
}
