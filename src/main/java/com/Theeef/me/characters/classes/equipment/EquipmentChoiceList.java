package com.Theeef.me.characters.classes.equipment;

import com.Theeef.me.items.DNDItem;
import com.google.common.collect.Lists;

import java.util.List;

public class EquipmentChoiceList implements IEquipmentChoice {

    DNDItem[] items;

    // Represents an equipment choice that yields multiple items
    public EquipmentChoiceList(DNDItem... items) {
        this.items = items;
    }


    @Override
    public List<DNDItem> getChoice() {
        return Lists.newArrayList(items);
    }
}
