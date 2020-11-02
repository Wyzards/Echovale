package com.Theeef.me.characters.abilities;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Ability {

    private String description;
    private String name;
    private String abbreviation;
    private Set<Skill> skills;

    // If skills cause issues, method to init them afterwars
    public static Ability STR = new Ability("Strength", "STR", "Strength measures bodily power, athletic training, and the extent to which you can exert raw physical force.", Sets.newHashSet(Skill.ATHLETICS));
    public static Ability DEX = new Ability("Dexterity", "DEX", "Dexterity measures agility, reflexes, and balance.", Sets.newHashSet(Skill.ACROBATICS, Skill.SLEIGHT_OF_HAND, Skill.STEALTH));
    public static Ability CON = new Ability("Constitution", "CON", "Constitution measures health, stamina, and vital force.", Sets.newHashSet());
    public static Ability INTEL = new Ability("Intelligence", "INTEL", "Intelligence measures mental acuity, accuracy of recall, and the ability to reason.", Sets.newHashSet(Skill.ARCANA, Skill.HISTORY, Skill.INVESTIGATION, Skill.RELIGION, Skill.NATURE));
    public static Ability WIS = new Ability("Wisdom", "WIS", "Wisdom reflects how attuned you are to the world around you and represents perceptiveness and intuition.", Sets.newHashSet(Skill.ANIMAL_HANDLING, Skill.INSIGHT, Skill.MEDICINE, Skill.PERCEPTION, Skill.SURVIVAL));
    public static Ability CHA = new Ability("Charisma", "CHA", "Charisma measures your ability to interact effectively with others. It includes such factors as confidence and eloquence, and it can represent a charming or commanding personality.", Sets.newHashSet(Skill.DECEPTION, Skill.PERSUASION, Skill.PERFORMANCE, Skill.PERSUASION));

    public Ability(String name, String abbreviation, String description, Set<Skill> skills) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.skills = skills;
    }

    public static Set<Ability> values() {
        return Sets.newHashSet(STR, DEX, CON, INTEL, WIS, CHA);
    }
}
