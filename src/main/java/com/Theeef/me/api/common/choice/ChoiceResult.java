package com.Theeef.me.api.common.choice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChoiceResult {

    private final HashMap<Option, ChoiceResult> choiceOptionResults;
    private final List<Option> chosen;
    private final Choice choice;

    public ChoiceResult(Choice choice, List<Option> chosen) {
        this.choice = choice;
        this.chosen = chosen;
        this.choiceOptionResults = new HashMap<>();
    }

    public ChoiceResult(MultipleOption multiOption) {
        this.choice = null;
        this.chosen = null;
        this.choiceOptionResults = new HashMap<>();

        for (Option option : multiOption.getItems())
            if (option.getOptionType() == Option.OptionType.CHOICE)
                this.choiceOptionResults.put(option, new ChoiceResult(((ChoiceOption) option).getChoice()));
    }

    public ChoiceResult(Choice choice) {
        this(choice, new ArrayList<>());
    }

    public boolean alreadyChosen(Option option) {
        return this.chosen.contains(option);
    }

    public boolean choose(Option option) {
        if (alreadyChosen(option) || isComplete())
            return false;
        else {
            this.chosen.add(option);

            if (option.getOptionType() == Option.OptionType.CHOICE)
                this.choiceOptionResults.put(option, new ChoiceResult(((ChoiceOption) option).getChoice()));
            else if (option.getOptionType() == Option.OptionType.MULTIPLE)
                this.choiceOptionResults.put(option, new ChoiceResult((MultipleOption) option));

            return true;
        }
    }

    public void unchoose(Option option) {
        this.chosen.remove(option);

        if (this.choiceOptionResults.containsKey(option))
            this.choiceOptionResults.remove(option);
    }

    public boolean isComplete() {
        for (Option option : this.choiceOptionResults.keySet())
            if (!this.choiceOptionResults.get(option).isComplete())
                return false;

        return this.choice == null || this.chosen.size() == this.choice.getChoiceAmount();
    }

    public void clearChoices() {
        this.chosen.clear();
    }

    // Getter methods
    public ChoiceResult getChoiceOptionResult(Option option) {
        return this.choiceOptionResults.getOrDefault(option, null);
    }

    public List<Option> getChosen() {
        return this.chosen;
    }

    public Choice getChoice() {
        return this.choice;
    }

}
