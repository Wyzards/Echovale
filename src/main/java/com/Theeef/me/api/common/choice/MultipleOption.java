package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;

import java.util.List;

public class MultipleOption extends Option {

    private final List<Option> items;

    public MultipleOption(List<Prerequisite> prerequisites, List<Option> items) {
        super(Option.OptionType.MULTIPLE, prerequisites);

        this.items = items;
    }

}
