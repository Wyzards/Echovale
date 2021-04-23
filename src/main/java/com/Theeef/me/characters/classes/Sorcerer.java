package com.Theeef.me.characters.classes;

import com.Theeef.me.characters.abilities.Abilities;
import com.Theeef.me.characters.abilities.Ability;
import com.Theeef.me.characters.abilities.Skill;
import com.Theeef.me.characters.classes.equipment.EquipmentChoice;
import com.Theeef.me.characters.classes.equipment.EquipmentChoiceList;
import com.Theeef.me.characters.classes.equipment.IEquipmentChoice;
import com.Theeef.me.items.armor.Armor;
import com.Theeef.me.items.equipment.AdventuringGear;
import com.Theeef.me.items.weapons.Weapons;
import com.google.common.collect.Lists;

public class Sorcerer extends DNDClass {

    public Sorcerer() {
        super(6, Lists.newArrayList(Weapons.DAGGER, Weapons.DART, Weapons.SLING, Weapons.QUARTERSTAFF, Weapons.LIGHT_CROSSBOW), 2, Lists.newArrayList(Skill.ARCANA, Skill.DECEPTION, Skill.INSIGHT, Skill.INTIMIDATION, Skill.PERSUASION, Skill.RELIGION), Lists.newArrayList(Ability.CON, Ability.CHA), Lists.newArrayList(Weapons.DAGGER.getAmount(2)),
                new EquipmentChoice(Lists.newArrayList(new EquipmentChoice(null, (IEquipmentChoice[]) Weapons.values().toArray())), new EquipmentChoiceList(Weapons.LIGHT_CROSSBOW, AdventuringGear.CROSSBOW_BOLT.getAmount(20))), new EquipmentChoice(null, Armor.BREASTPLATE));
    }

    @Override
    public int startingHealth(Abilities abilities) {
        return 0;
    }

    @Override
    int levelUpHealth(int currentHealth) {
        return 0;
    }
}
