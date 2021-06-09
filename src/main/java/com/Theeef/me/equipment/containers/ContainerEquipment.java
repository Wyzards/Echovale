package com.Theeef.me.equipment.containers;

import com.Theeef.me.equipment.CommonEquipment;
import com.Theeef.me.equipment.Cost;
import com.Theeef.me.equipment.Equipment;
import org.bukkit.inventory.ItemStack;

public class ContainerEquipment {

    private final String equipmentUrl;
    private final long quantity;

    public ContainerEquipment(String equipmentUrl, long quantity) {
        this.equipmentUrl = equipmentUrl;
        this.quantity = quantity;
    }

    public ContainerEquipment(String toString) {
        this(toString.substring(0, toString.indexOf(",")), Long.parseLong(toString.substring(toString.indexOf(",") + 1)));
    }

    public long getQuantity() {
        return this.quantity;
    }

    public ItemStack getItemStack() {
        ItemStack item = getEquipment().getItemStack();
        item.setAmount(item.getAmount() * ((int) this.quantity));

        return item;
    }

    public Equipment getEquipment() {
        return Equipment.fromString(this.equipmentUrl);
    }

    public Cost getCost() {
        Equipment equipment = getEquipment();

        return equipment instanceof CommonEquipment ? ((CommonEquipment) equipment).getCost().multiply(this.quantity, false) : new Cost(Cost.MoneyUnit.CP, 0);
    }

    public double getWeight() {
        Equipment equipment = getEquipment();

        return equipment instanceof CommonEquipment ? ((CommonEquipment) equipment).getWeight() * this.quantity : 0;
    }

    public String toString() {
        return this.equipmentUrl + "," + this.quantity;
    }
}
