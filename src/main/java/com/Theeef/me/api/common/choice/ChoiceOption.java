package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;

import java.util.List;
import java.util.Objects;

public class ChoiceOption extends Option {

    private final Choice choice;

    public ChoiceOption(List<Prerequisite> prerequisites, Choice choice) {
        super(OptionType.CHOICE, prerequisites);

        this.choice = choice;
    }

    @Override
    public String getDescription() {
        if (this.choice.getOptionSet().getType() == OptionSet.OptionSetType.RESOURCE_LIST)
            return this.choice.getChoiceAmount() + " " + this.choice.getType() + (this.choice.getChoiceAmount() > 1 ? "s" : "") + " from the " + ((ResourceListOptionSet) this.choice.getOptionSet()).getReferenceList().get("name") + " list";

        return this.choice.getChoiceAmount() + " chosen option" + (this.choice.getChoiceAmount() > 1 ? "s" : "");
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ChoiceOption && ((ChoiceOption) object).getChoice().equals(this.choice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.choice);
    }

    // Getter methods
    public Choice getChoice() {
        return this.choice;
    }
}
