package com.Theeef.me.characters.races;

import com.Theeef.me.characters.abilities.Ability;
import com.Theeef.me.characters.abilities.AbilityAlteration;
import com.Theeef.me.characters.abilities.CreatureSize;
import com.Theeef.me.characters.abilities.Language;
import com.Theeef.me.characters.features.Feature;
import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.Set;

public class Dragonborn extends Race {

    public Dragonborn() {
        super("Dragonborn", 30, Sets.newHashSet(Language.COMMON), 0, CreatureSize.MEDIUM, "desc");
    }

    /**
     * Get a set of AbilityAlterations the source provides
     *
     * @return the set of ability alterations or null
     */
    @Override
    public Set<AbilityAlteration> getAlterations() {
        return Sets.newHashSet(new AbilityAlteration(Ability.STR, 2, this), new AbilityAlteration(Ability.CHA, 1, this));
    }

    @Override
    public Set<? extends Feature> getSourceFeatures() {
        return Sets.newHashSet(Feature.DRACONIC_ANCESTRY, Feature.BREATH_WEAPON);
    }

    public Material getDisplayMaterial() {
        return Material.DRAGON_EGG;
    }
}
