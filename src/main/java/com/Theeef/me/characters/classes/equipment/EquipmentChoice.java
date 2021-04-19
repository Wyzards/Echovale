package com.Theeef.me.characters.classes.equipment;

import com.Theeef.me.items.DNDItem;

import java.util.List;

public class EquipmentChoice {

    IEquipmentChoice[] items;
    List<EquipmentChoice> moreChoices;

    public EquipmentChoice(List<EquipmentChoice> moreChoices, IEquipmentChoice... items) {
        this.items = items;
        this.moreChoices = moreChoices;
    }
}
