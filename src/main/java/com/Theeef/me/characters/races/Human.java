package com.Theeef.me.characters.races;

import com.Theeef.me.characters.abilities.*;
import com.Theeef.me.characters.feats.Feat;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Human extends Race {

    public Human(boolean isVariant) {
        super(isVariant ? "Variant Human" : "Human", 30, Sets.newHashSet(Language.COMMON), 1, CreatureSize.MEDIUM, "Humans are the most adaptable and ambitious people among the common races. Whatever drives them, humans are the innovators, the achievers, and the pioneers of the worlds.");
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
