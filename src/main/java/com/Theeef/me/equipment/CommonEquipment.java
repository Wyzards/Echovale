package com.Theeef.me.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.util.NBTHandler;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.List;

public class CommonEquipment extends Equipment {

    private final Cost cost;
    private final double weight;

    public CommonEquipment(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);

        assert json != null;
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
        ItemStack item = super.getItemStack();

        // NBT Data
        NBTHandler.addString(item, "weight", String.valueOf(weight));

        for (Cost.MoneyUnit moneyUnit : Cost.MoneyUnit.values())
            NBTHandler.addString(item, "cost_" + moneyUnit.name().toLowerCase(), String.valueOf(this.cost.getQuantity(moneyUnit)));

        return item;
    }

    public List<String> loreCostWeight() {
        return Lists.newArrayList(ChatColor.GRAY + "Cost: " + this.cost.amountString(), ChatColor.GRAY + "Weight: " + ChatColor.WHITE + this.weight + " pounds");
    }

    public double getWeight() {
        return this.weight;
    }

    public Cost getCost() {
        return this.cost;
    }
}
