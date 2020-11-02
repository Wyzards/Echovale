package com.Theeef.me.characters.races;

import com.Theeef.me.characters.abilities.Ability;
import com.Theeef.me.characters.abilities.AbilityAlteration;
import com.Theeef.me.characters.abilities.Skill;
import com.Theeef.me.characters.feats.Feat;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class VariantHuman extends Human {

    public VariantHuman() {
        super(true);
    }

    /**
     * Replaces the +1 to all abilities Human (non-variant) receives
     */
    @Override
    public Set<AbilityAlteration> getAlterations() {
        return null;
    }

    /**
     * Returns all Skills, which variant human can increase 2 by 1
     *
     * @return the skills
     */
    @Override
    public Set<Ability> abilityOptions() {
        return Ability.values();
    }

    @Override
    /**
     * A specified amount of abilities can be increased by 1
     */
    public int skillChoiceAmount() {
        return 1;
    }

    @Override
    public int abilityChoiceAmount() {
        return 2;
    }

    /**
     * The feats that variant humans may take
     *
     * @return the available feats
     */
    @Override
    public Set<Feat> featOptions() {
        return Feat.values();
    }

    /**
     * Human variants may select 1 feat
     *
     * @return the amount of feats to be chosen
     */
    @Override
    public int featChoiceAmount() {
        return 1;
    }
}
