package com.Theeef.me.characters.classes;

import com.Theeef.me.characters.abilities.Abilities;
import com.Theeef.me.characters.abilities.Ability;
import com.Theeef.me.characters.abilities.Proficiency;
import com.Theeef.me.characters.abilities.Skill;

import java.util.List;

public abstract class DNDClass {

    private int hitDice;
    private List<Proficiency> proficiencies;
    private List<Skill> proficientSkills;
    private List<Ability> savingThrows;

    public DNDClass(int hitDice, List<Proficiency> proficiencies, List<Skill> proficientSkills, List<Ability> savingThrows) {
        this.hitDice = hitDice;
        this.proficiencies = proficiencies;
        this.proficientSkills = proficientSkills;
        this.savingThrows = savingThrows;
    }

    abstract int startingHealth();

    abstract int levelUpHealth(int currentHealth);

}
