package com.Theeef.me.api.common.choice;

import java.util.List;

public abstract class OptionSet {

    public enum OptionSetType {
        RESOURCE_LIST, ARRAY;
    }

    private final OptionSetType option_set_type;

    public OptionSet(OptionSetType option_set_type) {
        this.option_set_type = option_set_type;
    }

    public abstract List<Option> getOptions();
}
