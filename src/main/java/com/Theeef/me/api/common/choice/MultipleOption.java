package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.api.common.Indexable;

import java.util.List;
import java.util.Objects;

public class MultipleOption extends Option {

    private final List<Option> items;

    public MultipleOption(List<Prerequisite> prerequisites, List<Option> items) {
        super(Option.OptionType.MULTIPLE, prerequisites);

        this.items = items;
    }

    @Override
    public String getDescription() {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < items.size(); i++)
            if (i + 1 == items.size())
                string.append("and ").append(items.get(i).getDescription());
            else
                string.append(items.get(i).getDescription()).append(", ");

        return string.toString();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof MultipleOption && ((MultipleOption) object).getItems().equals(this.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.items);
    }

    // Getter methods
    public List<Option> getItems() {
        return this.items;
    }

}
