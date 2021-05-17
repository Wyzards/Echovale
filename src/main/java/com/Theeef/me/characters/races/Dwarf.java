package com.Theeef.me.characters.races;

import com.Theeef.me.api.equipment.DamageType;
import com.Theeef.me.characters.abilities.*;
import com.Theeef.me.characters.features.Feature;
import com.google.common.collect.Sets;
import org.bukkit.Material;

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
        return Sets.newHashSet(Feature.DARKVISION, Feature.DWARVEN_RESILIENCE, Feature.STONECUNNING);
    }

    @Override
    public Set<DamageType> sourceResistances() {
        return Sets.newHashSet(DamageType.POISON);
    }

    @Override
    public Set<Proficiency> proficiencyOptions() {
        return Sets.newHashSet(Proficiency.SMITHS_TOOLS, Proficiency.BREWERS_SUPPLIES, Proficiency.MASONS_TOOLS);
    }

    @Override
    public int proficiencyChoiceAmount() {
        return 1;
    }

    @Override
    public Set<Proficiency> sourceProficiencies() {
        return Sets.newHashSet(Proficiency.BATTLEAXE, Proficiency.HANDAXE, Proficiency.LIGHT_HAMMER, Proficiency.WARHAMMER);
    }

    public Material getDisplayMaterial() {
        return Material.NETHERITE_PICKAXE;
    }
}