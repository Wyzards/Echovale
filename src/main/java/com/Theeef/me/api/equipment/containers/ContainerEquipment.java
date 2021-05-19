package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;

public class ContainerEquipment {

    private final String equipmentUrl;
    private final long quantity;

    public ContainerEquipment(String equipmentUrl, long quantity) {
        this.equipmentUrl = equipmentUrl;
        this.quantity = quantity;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public Equipment getEquipment() {
        return new Equipment(this.equipmentUrl);
    }

    public Cost getCost() {
        Equipment equipment = getEquipment();

        return equipment instanceof CommonEquipment ? ((CommonEquipment) equipment).getCost().multiply(this.quantity, false) : new Cost(Cost.MoneyUnit.CP, 0);
    }

    public double getWeight() {
        Equipment equipment = getEquipment();

        return equipment instanceof CommonEquipment ? ((CommonEquipment) equipment).getWeight() * this.quantity : 0;
    }
}
