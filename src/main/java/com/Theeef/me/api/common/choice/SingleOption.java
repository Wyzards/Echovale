package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;

import java.util.List;

public class SingleOption extends Option {

    private final CountedReference item;

    public SingleOption(List<Prerequisite> prerequisites, CountedReference item) {
        super(OptionType.SINGLE, prerequisites);

        this.item = item;
    }

    // Getter methods
    public CountedReference getItem() {
        return this.item;
    }

}
