package com.Theeef.me.api.common.choice;

import java.util.List;

public class ArrayOptionSet extends OptionSet {

    private final List<Option> options;

    public ArrayOptionSet(List<Option> options) {
        super(OptionSetType.ARRAY);

        this.options = options;
    }

    public List<Option> getOptions() {
        return this.options;
    }

}
