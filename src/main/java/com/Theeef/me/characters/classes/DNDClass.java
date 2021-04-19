package com.Theeef.me.characters.classes;

import com.Theeef.me.characters.abilities.Abilities;
import com.Theeef.me.characters.abilities.Ability;
import com.Theeef.me.characters.abilities.Skill;
import com.Theeef.me.characters.classes.equipment.EquipmentChoice;
import com.Theeef.me.items.DNDItem;

import java.util.List;

public abstract class DNDClass {

    private int hitDice;
    private List<DNDItem> itemProficiencies;
    private int proficientSkillAmount;
    private List<Skill> skillProficiencies;
    private List<Ability> savingThrows;

    public DNDClass(int hitDice, List<DNDItem> itemProficiencies, int proficientSkillAmount, List<Skill> skillProficiencies, List<Ability> savingThrows, List<DNDItem> equipment, EquipmentChoice... equipmentChoices) {
        this.hitDice = hitDice;
        this.itemProficiencies = itemProficiencies;
        this.proficientSkillAmount = proficientSkillAmount;
        this.skillProficiencies = skillProficiencies;
        this.savingThrows = savingThrows;
    }

    public abstract int startingHealth(Abilities abilities);

    abstract int levelUpHealth(int currentHealth);

}
