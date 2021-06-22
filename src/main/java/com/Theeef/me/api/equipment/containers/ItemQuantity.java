package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

public class ItemQuantity {

    private final APIReference item;
    private final long quantity;

    // TODO: Only required b/c of scuffed NBT data, fix later
    public ItemQuantity(APIReference item, long quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public ItemQuantity(String itemUrl, long quantity) {
        this(new APIReference(APIRequest.request(itemUrl)), quantity);
    }

    public ItemQuantity(JSONObject json) {
        this(new APIReference((JSONObject) json.get("item")), (long) json.get("quantity"));
    }

    public ItemQuantity(String toString) {
        this(new APIReference(APIRequest.request(toString.substring(0, toString.indexOf(",")))), Long.parseLong(toString.substring(toString.indexOf(",") + 1)));
    }

    public ItemStack getItemStack() {
        ItemStack item = getItem().getItemStack();
        item.setAmount(item.getAmount() * ((int) this.quantity));

        return item;
    }

    public Cost getCost() {
        Equipment equipment = getItem();

        return equipment.getCost() == null ? null : equipment.getCost().multiply(getQuantity());
    }

    public double getWeight() {
        Equipment equipment = getItem();

        return equipment.getWeight() == 0 ? 0 : equipment.getWeight() * this.quantity;
    }

    public String toString() {
        return this.item.getUrl() + "," + this.quantity;
    }

    // Getter methods
    public Equipment getItem() {
        return Equipment.fromString(this.item.getUrl());
    }

    public long getQuantity() {
        return this.quantity;
    }
}
