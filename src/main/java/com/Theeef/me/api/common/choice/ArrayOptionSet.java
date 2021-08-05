package com.Theeef.me.api.common.choice;

import java.util.List;
import java.util.Objects;

public class ArrayOptionSet extends OptionSet {

    private final List<Option> options;

    public ArrayOptionSet(List<Option> options) {
        super(OptionSetType.ARRAY);

        this.options = options;
    }

    public List<Option> getOptions() {
        return this.options;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ArrayOptionSet && ((ArrayOptionSet) object).getOptions().equals(this.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.options);
    }

}
