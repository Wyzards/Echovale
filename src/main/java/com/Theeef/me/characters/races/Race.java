package com.Theeef.me.characters.races;

import com.Theeef.me.characters.abilities.*;
import com.Theeef.me.characters.feats.Feat;
import com.Theeef.me.characters.feats.FeatSource;
import com.Theeef.me.characters.features.Feature;
import com.Theeef.me.characters.features.FeatureSource;
import com.Theeef.me.combat.damage.DamageType;
import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public abstract class Race implements AbilityAlterationSource, SkillProficiencySource, ProficiencySource, FeatSource, FeatureSource, ResistanceSource {

    public static Race HUMAN = new Human(false);
    public static Race VARIANT_HUMAN = new VariantHuman();
    public static Race DRAGONBORN = new Dragonborn();
    public static Race DWARF = new Dwarf();

    private String name;
    private String description;
    private int speed;
    private Set<Language> languages;
    private int freeLanguages;
    private CreatureSize size;

    public Race(String name, int speed, Set<Language> languages, int freeLanguages, CreatureSize size, String description) {
        this.name = name;
        this.description = description;
        this.speed = speed;
        this.languages = languages;
        this.freeLanguages = freeLanguages;
        this.size = size;
    }

    /**
     * The set of abilities you can select to alter because of this source
     *
     * @return the set of abilities to pick from or null
     */
    @Override
    public Set<Ability> abilityOptions() {
        return null;
    }

    /**
     * The maximum amount you can alter an ability you select from the ability options
     *
     * @return the maximum amount
     */
    @Override
    public int maxAbilityAlteration() {
        return 0;
    }

    /**
     * Get how many abilities you must select from the options list
     *
     * @return the amount to select
     */
    @Override
    public int abilityChoiceAmount() {
        return 0;
    }

    /**
     * Get the skills this source gives you proficiency in
     *
     * @return the set of skills or null
     */
    @Override
    public Set<Skill> sourceSkills() {
        return null;
    }

    /**
     * Get the set of skills you can select from for any optional skills.
     *
     * @return the set of skills you can choose from or null
     */
    @Override
    public Set<Skill> sourceSkillOptions() {
        return null;
    }

    /**
     * Get the amount of skills you can select from the source skill options set to be proficient in.
     *
     * @return the amount of skills to select
     */
    @Override
    public int skillChoiceAmount() {
        return 0;
    }

    @Override
    public Set<Feat> featOptions() {
        return null;
    }

    @Override
    public int featChoiceAmount() {
        return 0;
    }

    @Override
    public Set<? extends Feature> getSourceFeatures() {
        return null;
    }

    @Override
    public Set<DamageType> sourceResistances() {
        return null;
    }

    @Override
    public Set<Proficiency> proficiencyOptions() {
        return null;
    }

    @Override
    public int proficiencyChoiceAmount() {
        return 0;
    }

    @Override
    public Set<Proficiency> sourceProficiencies() {
        return null;
    }

    public static Set<Race> values() {
        return Sets.newHashSet(DRAGONBORN, HUMAN, VARIANT_HUMAN, DWARF);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract Material getDisplayMaterial();
}
