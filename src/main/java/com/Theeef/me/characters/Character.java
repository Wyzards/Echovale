package com.Theeef.me.characters;

import com.Theeef.me.api.equipment.DamageType;
import com.Theeef.me.characters.abilities.*;
import com.Theeef.me.characters.backgrounds.Background;
import com.Theeef.me.characters.classes.DNDClass;
import com.Theeef.me.characters.classes.HitDice;
import com.Theeef.me.characters.features.FeatureDraconicAncestry;
import com.Theeef.me.characters.races.Race;
import com.Theeef.me.items.DNDItem;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Character {

    private final UUID owner;
    private int characterID;
    private String name;
    private Race race;
    private HashMap<DNDClass, Integer> classes;
    private Background background;
    private int experience;
    private Abilities abilities;
    private int maxHP;
    private int currentHP;
    private int tempHP;
    private Set<Ability> savingThrows;
    private int deathSuccesses;
    private int deathFailures;
    private Set<DNDItem> itemProficiencies;
    private Set<Skill> proficientSkills;

    public Character(UUID owner, String name, HashMap<Ability, Integer> baseAbilities, Race race, Set<AbilityAlteration> chosenAlterations, DNDClass startingClass, Set<DNDItem> itemProficiencies, Background background) {
        this.owner = owner;
        this.name = name;
        this.race = race;
        this.classes = new HashMap<DNDClass, Integer>();
        this.itemProficiencies = itemProficiencies;
        classes.put(startingClass, 1);
        this.background = background;
        this.abilities = new Abilities(baseAbilities, race.getAlterations());
        abilities.addAlterations(chosenAlterations);
        abilities.addAlterations(background.getAlterations());
        this.experience = 0;
        this.maxHP = startingClass.startingHealth(abilities);
        this.currentHP = maxHP;
        this.tempHP = 0;
    }

    public int getAbilityModifier(Ability ability) {
        return 0;
    }

    public int getSkillModifier(Skill skill) {
        return 0;
    }

    public HashMap<HitDice, Integer> getHitDice() {
        return null;
    }

    public int getSpeed() {
        return 0;
    }

    public Set<DamageType> getResistances() {
        Set<DamageType> resistances = race.sourceResistances();

        if (race.getName().equals("Dragonborn")) {
            resistances.add(FeatureDraconicAncestry.getChoice(owner, characterID).getDamageType());
        }

        return resistances;
    }
}
