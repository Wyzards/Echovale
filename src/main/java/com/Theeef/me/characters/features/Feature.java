package com.Theeef.me.characters.features;

import java.util.Set;

public class Feature {

    public static Feature BREATH_WEAPON = new Feature("Breath Weapon", "You can use your action to exhale destructive energy. Your draconic ancestry determines the size, shape, and damage type of the exhalation. When you use your breath weapon, each creature in the area of the exhalation must make a saving throw, the type of which is determined by your draconic ancestry. The DC for this saving throw equals 8 + your Constitution modifier + your proficiency bonus. A creature takes 2d6 damage on a failed save, and half as much damage on a successful one. The damage increases to 3d6 at 6th level, 4d6 at 11th level, and 5d6 at 16th level. After you use your breath weapon, you can’t use it again until you complete a short or long rest.");
    public static Feature DRACONIC_ANCESTRY = new FeatureDraconicAncestry();
    public static Feature DARKVISION = new Feature("Darkvision", "You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray.");
    public static Feature DWARVEN_RESILIENCE = new Feature("Dwarven Resilience", "You have advantage on saving throws against poison.");
    public static Feature STONECUNNING = new Feature("Stonecunning", "Whenever you make an Intelligence (History) check related to the Origin of stonework, you are considered proficient in the History skill and add double your Proficiency Bonus to the check, instead of your normal Proficiency Bonus.");

    private String name;
    private String description;

    public Feature(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
