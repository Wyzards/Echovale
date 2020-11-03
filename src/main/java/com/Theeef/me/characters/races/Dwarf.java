package com.Theeef.me.characters.races;

import com.Theeef.me.characters.abilities.Ability;
import com.Theeef.me.characters.abilities.AbilityAlteration;
import com.Theeef.me.characters.abilities.CreatureSize;
import com.Theeef.me.characters.abilities.Language;
import com.Theeef.me.characters.features.Feature;
import com.Theeef.me.combat.damage.DamageType;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Dwarf extends Race {

    public Dwarf() {
        super("Dwarf", 25, Sets.newHashSet(Language.COMMON, Language.DWARVISH), 1, CreatureSize.MEDIUM, "Bold and hardy, dwarves are known as skilled warriors, miners, and workers of stone and metal.");
    }

    /**
     * Get a set of AbilityAlterations the source provides
     *
     * @return the set of ability alterations or null
     */
    @Override
    public Set<AbilityAlteration> getAlterations() {
        return Sets.newHashSet(new AbilityAlteration(Ability.CON, 2, this));
    }

    /**
     * Get the features granted by this source
     *
     * @return set of features
     */
    @Override
    public Set<? extends Feature> getSourceFeatures() {
        return Sets.newHashSet(Feature.DARKVISION, Feature.DWARVEN_RESILIENCE);
    }

    @Override
    public Set<DamageType> sourceResistances() {
        return Sets.newHashSet(DamageType.POISON);
    }
}