package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.api.common.Indexable;

import java.util.List;
import java.util.Objects;

public class SingleOption extends Option {

    private final CountedReference item;

    public SingleOption(List<Prerequisite> prerequisites, CountedReference item) {
        super(OptionType.SINGLE, prerequisites);

        this.item = item;
    }

    @Override
    public String getDescription() {
        return getItem().getReference().getName() + (item.getCount() > 1 ? " x" + item.getCount() : "");
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof SingleOption && ((SingleOption) object).getItem().equals(this.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.item);
    }

    // Getter methods
    public CountedReference getItem() {
        return this.item;
    }

}
