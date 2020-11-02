package com.Theeef.me.characters.races;

import com.Theeef.me.characters.abilities.*;
import com.Theeef.me.characters.feats.Feat;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Human extends Race {

    public Human() {
        super("Human", 30, Sets.newHashSet(Language.COMMON), 1, CreatureSize.MEDIUM, "Humans are the most adaptable and ambitious people among the common races. They have widely varying tastes, morals, and customs in the many different lands where they have settled. When they settle, though, they stay: they build cities to last for the ages, and great kingdoms that can persist for long centuries. An individual human might have a relatively short life span, but a human nation or culture preserves traditions with origins far beyond the reach of any single human’s memory. They live fully in the present—making them well suited to the adventuring life—but also plan for the future, striving to leave a lasting legacy. Individually and as a group, humans are adaptable opportunists, and they stay alert to changing political and social dynamics.");
    }

    /**
     * Get a set of AbilityAlterations the source provides
     *
     * @return the set of ability alterations
     */
    @Override
    public Set<AbilityAlteration> getAlterations() {
        Set<AbilityAlteration> alterations = new HashSet<AbilityAlteration>();

        for (Ability ability : Ability.values())
            alterations.add(new AbilityAlteration(ability, 1, this));

        return alterations;
    }
}
