package com.Theeef.me.characters;

import com.Theeef.me.characters.abilities.Abilities;
import com.Theeef.me.characters.abilities.Ability;
import com.Theeef.me.characters.abilities.Proficiency;
import com.Theeef.me.characters.abilities.Skill;
import com.Theeef.me.characters.backgrounds.Background;
import com.Theeef.me.characters.classes.DNDClass;
import com.Theeef.me.characters.classes.HitDice;
import com.Theeef.me.characters.races.Race;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Character {

    private UUID owner;
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
    private Set<Proficiency> proficiencies;
    private Set<Skill> proficientSkills;

    public Character(UUID owner, String name, HashMap<Ability, Integer> baseAbilities, Race race, DNDClass startingClass, Background background) {
        this.owner = owner;
        this.name = name;
        this.race = race;
        this.classes = new HashMap<DNDClass, Integer>;
        classes.put(startingClass, 1);
        this.background = background;
        this.abilities = new Abilities(baseAbilities, race.getAlterations());
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
}
