package com.Theeef.me.characters.features;

import java.util.Set;

/**
 * Represents a feature where you must pick one option from a list of options
 */
public abstract class ChoiceFeature extends Feature {

    public ChoiceFeature(String name, String description) {
        super(name, description);
    }

    public abstract Set<? extends FeatureChoice> getChoices();
}
