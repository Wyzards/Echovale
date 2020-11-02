package com.Theeef.me.characters.abilities;

import com.google.common.collect.Lists;

import java.util.List;

public class Language {

    public static Language COMMON = new Language("Common", "Common", "Humans");
    public static Language DWARVISH = new Language("Dwarvish", "Dwarvish", "Dwarves");
    public static Language ELVISH = new Language("Elvish", "Elvish", "Elves");
    public static Language GIANT = new Language("Giant", "Dwarvish", Lists.newArrayList("Ogres", "Giants"));
    public static Language GNOMISH = new Language("Gnomish", "Dwarvish", "Gnomes");
    public static Language GOBLIN = new Language("Goblin", "Dwarvish", "Goblinoids");
    public static Language HALFLING = new Language("Halfling", "Common", "Halflings");
    public static Language ORC = new Language("Orc", "Dwarvish", "Orcs");
    public static Language ABYSSAL = new Language("Abyssal", "Infernal", "Demons");
    public static Language CELESTIAL = new Language("Celestial", "Celestial", "Celestials");
    public static Language DRACONIC = new Language("Draconic", "Draconic", Lists.newArrayList("Dragons", "Dragonborn"));
    public static Language DEEP_SPEECH = new Language("Deep Speech", "-", Lists.newArrayList("Aboleths", "Cloakers"));
    public static Language INFERNAL = new Language("Infernal", "Infernal", "Devils");
    public static Language PRIMORDIAL = new Language("Primordial", "Dwarvish", "Elementals");
    public static Language SYLVAN = new Language("Sylvan", "Elvish", "Fey creatures");
    public static Language UNDERCOMMON = new Language("Undercommon", "Elvish", "Underworld traders");

    private String name;
    private List<String> typicalSpeakers;
    private String script;

    public Language(String name, String script, String typicalSpeaker) {
        this(name, script, Lists.newArrayList(typicalSpeaker));
    }

    public Language(String name, String script, List<String> typicalSpeakers) {
        this.name = name;
        this.script = script;
        this.typicalSpeakers = typicalSpeakers;
    }
}
