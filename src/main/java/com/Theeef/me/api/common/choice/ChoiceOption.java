package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;

import java.util.List;

public class ChoiceOption extends Option {

    private final Choice choice;

    public ChoiceOption(List<Prerequisite> prerequisites, Choice choice) {
        super(OptionType.CHOICE, prerequisites);

        this.choice = choice;
    }
}
