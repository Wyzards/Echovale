package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.equipment.CommonEquipment;
import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

public class EquipmentQuantity {

    private final APIReference item;
    private final long quantity;

    // TODO: Only required b/c of scuffed NBT data, fix later
    public EquipmentQuantity(String itemUrl, long quantity) {
        this.item = new APIReference(APIRequest.request(itemUrl));
        this.quantity = quantity;
    }

    public EquipmentQuantity(JSONObject json) {
        this.item = new APIReference((JSONObject) json.get("item"));
        this.quantity = (long) json.get("quantity");
    }

    public EquipmentQuantity(String toString) {
        this.item = new APIReference(APIRequest.request(toString.substring(0, toString.indexOf(","))));
        this.quantity = Long.parseLong(toString.substring(toString.indexOf(",") + 1));
    }

    public ItemStack getItemStack() {
        ItemStack item = getItem().getItemStack();
        item.setAmount(item.getAmount() * ((int) this.quantity));

        return item;
    }

    public Cost getCost() {
        Equipment equipment = getItem();

        return equipment instanceof CommonEquipment ? ((CommonEquipment) equipment).getCost().multiply(this.quantity, false) : new Cost(Cost.MoneyUnit.CP, 0);
    }

    public double getWeight() {
        Equipment equipment = getItem();

        return equipment instanceof CommonEquipment ? ((CommonEquipment) equipment).getWeight() * this.quantity : 0;
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
