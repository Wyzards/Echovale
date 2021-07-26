package com.Theeef.me.api.common.choice;

import java.util.ArrayList;
import java.util.List;

public class ChoiceResult<T> {

    private final List<T> chosen;
    private final Choice choice;

    public ChoiceResult(Choice choice, List<T> chosen) {
        this.choice = choice;
        this.chosen = chosen;
    }

    public ChoiceResult(Choice choice) {
        this(choice, new ArrayList<>());
    }

    public boolean alreadyChosen(T reference) {
        return this.chosen.contains(reference);
    }

    public boolean choose(T reference) {
        if (alreadyChosen(reference) || isComplete())
            return false;
        else {
            this.chosen.add(reference);
            return true;
        }
    }

    public void unchoose(T reference) {
        this.chosen.remove(reference);
    }

    public int decisionsLeft() {
        return (int) (choice.getChoiceAmount() - this.chosen.size());
    }

    public boolean isComplete() {
        return this.chosen.size() == this.choice.getChoiceAmount();
    }

    public void clearChoices() {
        this.chosen.clear();
    }

    // Getter methods
    public List<T> getChosen() {
        return this.chosen;
    }

    public Choice getChoice() {
        return this.choice;
    }

}
