package com.Theeef.me.interaction.character;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.backgrounds.Background;
import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.chardata.Skill;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.Level;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.AbilityBonus;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Character {

    private static Echovale plugin = Echovale.getPlugin(Echovale.class);

    private final UUID uuid;
    private final UUID owner;
    private final String name;
    private final Race race;
    private final Subrace subrace;
    private final HashMap<DNDClass, Integer> classes;
    private final HashMap<DNDClass, Subclass> subclasses;
    private final HashMap<AbilityScore, Integer> baseAbilityScores; // Does not include ability score modifiers achieved at higher level, or any effects
    private final Background background;
    private int currentHitPoints;
    private int maxHitPoints;
    private int tempHitPoints;

    public Character(UUID owner, String name, Race race, Subrace subrace, HashMap<DNDClass, Integer> classes, HashMap<AbilityScore, Integer> baseAbilityScores, Background background) {
        UUID uuid = null;

        while (uuid == null || characterExists(owner, uuid))
            uuid = UUID.randomUUID();

        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.race = race;
        this.subrace = subrace;
        this.classes = classes;
        this.subclasses = new HashMap<>();
        this.baseAbilityScores = baseAbilityScores;
        this.background = background;

        saveCharacter();
    }

    public Character(UUID owner, String name, Race race, Subrace subrace, DNDClass dndclass, int cha, int con, int dex, int intel, int str, int wis, Background background) {
        this(owner, name, race, subrace, Character.newClassMap(dndclass), newAbilityScoreMap(cha, con, dex, intel, str, wis), background);
    }

    public Character(UUID owner, UUID uuid) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = plugin.getConfigManager().getCharacters().getString(getSavePath() + ".name");
        this.race = new Race(plugin.getConfigManager().getCharacters().getString(getSavePath() + ".race"));
        this.subrace = plugin.getConfigManager().getCharacters().contains(getSavePath() + ".subrace") ? new Subrace(plugin.getConfigManager().getCharacters().getString(getSavePath() + ".subrace")) : null;
        this.classes = new HashMap<>();
        this.subclasses = new HashMap<>();
        this.baseAbilityScores = new HashMap<>();
        this.background = new Background(plugin.getConfigManager().getCharacters().getString(getSavePath() + ".background"));
        this.currentHitPoints = plugin.getConfigManager().getCharacters().getInt(getSavePath() + ".currentHitPoints");
        this.maxHitPoints = plugin.getConfigManager().getCharacters().getInt(getSavePath() + ".maxHitPoints");
        this.tempHitPoints = plugin.getConfigManager().getCharacters().getInt(getSavePath() + ".tempHitPoints");

        for (String dndclass : plugin.getConfigManager().getCharacters().getConfigurationSection(getSavePath() + ".classes").getKeys(false)) {
            this.classes.put(DNDClass.fromIndex(dndclass), plugin.getConfigManager().getCharacters().getInt(getSavePath() + ".classes." + dndclass + ".level"));

            if (plugin.getConfigManager().getCharacters().contains(getSavePath() + ".classes." + dndclass + ".subclass"))
                this.subclasses.put(DNDClass.fromIndex(dndclass), Subclass.fromIndex(plugin.getConfigManager().getCharacters().getString(getSavePath() + ".classes." + dndclass + ".subclass")));
        }

        for (String abilityScore : plugin.getConfigManager().getCharacters().getConfigurationSection(getSavePath() + ".baseAbilityScores").getKeys(false))
            this.baseAbilityScores.put(AbilityScore.fromIndex(abilityScore), plugin.getConfigManager().getCharacters().getInt(getSavePath() + ".baseAbilityScores." + abilityScore));
    }

    public int getAbilityScore(AbilityScore ability) {
        int score = this.baseAbilityScores.get(ability);

        // TODO: Make this include ability score upgrades

        for (AbilityBonus bonus : this.race.getAbilityBonuses())
            if (bonus.getAbilityScore().equals(ability))
                score += bonus.getBonus();

        return score;
    }

    public int getAbilityModifier(AbilityScore ability) {
        return (int) ((getAbilityScore(ability) - 10) / 2.0);
    }

    public int getSkillModifier(Skill skill) {
        return getAbilityModifier(skill.getAbilityScore()) + ((isProficient(Proficiency.fromIndex("skill-" + skill.getIndex())) ? getProficiencyBonus() : 0));
    }

    public boolean isProficient(Proficiency proficiency) {
        if (this.race.getStartingProficiencies().contains(proficiency) || this.background.getProficiencies().contains(proficiency))
            return true;

        for (DNDClass dndclass : this.classes.keySet())
            if (dndclass.getProficiencies().contains(proficiency))
                return true;

        // Other potential sources:
        // Racial & Class choices
        // Temp sources?
        // Features? ~ individual

        return false;
    }

    public int getTotalLevel() {
        int level = 0;

        for (DNDClass dndclass : this.classes.keySet())
            level += this.classes.get(dndclass);

        return level;
    }

    public int getProficiencyBonus() {
        for (Level level : DNDClass.fromIndex("rogue").getClassLevels(getTotalLevel()))
            if (level.getProficiencyBonus() != -1)
                return (int) level.getProficiencyBonus();
        return 20000;
    }

    // Helper methods
    private void saveCharacter() {
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".name", this.name);
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".race", this.race.getUrl());
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".subrace", this.subrace == null ? null : this.subrace.getUrl());

        for (DNDClass dndclass : this.classes.keySet()) {
            plugin.getConfigManager().getCharacters().set(getSavePath() + ".classes." + dndclass.getIndex() + ".level", this.classes.get(dndclass));

            if (this.subclasses.containsKey(dndclass))
                plugin.getConfigManager().getCharacters().set(getSavePath() + ".classes." + dndclass.getIndex() + ".subclass", this.subclasses.get(dndclass));
        }

        for (AbilityScore abilityScore : this.baseAbilityScores.keySet())
            plugin.getConfigManager().getCharacters().set(getSavePath() + ".baseAbilityScores." + abilityScore.getIndex(), this.baseAbilityScores.get(abilityScore));

        plugin.getConfigManager().getCharacters().set(getSavePath() + ".background", this.background.getUrl());
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".currentHitPoints", this.currentHitPoints);
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".maxHitPoints", this.maxHitPoints);
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".tempHitPoints", this.tempHitPoints);
        plugin.getConfigManager().saveCharacters();
    }

    private String getSavePath() {
        return this.owner.toString() + "." + this.uuid.toString();
    }

    // Getter methods
    public UUID getUUID() {
        return this.uuid;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.name;
    }

    public Race getRace() {
        return this.race;
    }

    public Subrace getSubrace() {
        return this.subrace;
    }

    public HashMap<DNDClass, Integer> getClasses() {
        return this.classes;
    }

    public HashMap<DNDClass, Subclass> getSubclasses() {
        return this.subclasses;
    }

    public HashMap<AbilityScore, Integer> getBaseAbilityScores() {
        return this.baseAbilityScores;
    }

    public Background getBackground() {
        return this.background;
    }

    public int getCurrentHitPoints() {
        return this.currentHitPoints;
    }

    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    public int getTempHitPoints() {
        return this.tempHitPoints;
    }

    // Static methods
    private static HashMap<AbilityScore, Integer> newAbilityScoreMap(int cha, int con, int dex, int intel, int str, int wis) {
        HashMap<AbilityScore, Integer> map = new HashMap<>();
        map.put(AbilityScore.CHA, cha);
        map.put(AbilityScore.CON, con);
        map.put(AbilityScore.DEX, dex);
        map.put(AbilityScore.INT, intel);
        map.put(AbilityScore.STR, str);
        map.put(AbilityScore.WIS, wis);

        return map;
    }

    private static HashMap<DNDClass, Integer> newClassMap(DNDClass dndclass) {
        HashMap<DNDClass, Integer> map = new HashMap<>();
        map.put(dndclass, 1);
        return map;
    }

    private static boolean characterExists(UUID owner, UUID character) {
        return plugin.getConfigManager().getCharacters().contains(owner.toString() + "." + character.toString());
    }

    public static List<Character> getUsersCharacters(UUID owner) {
        List<Character> list = new ArrayList<>();

        if (plugin.getConfigManager().getCharacters().contains(owner.toString()))
            for (String characterUUID : plugin.getConfigManager().getCharacters().getConfigurationSection(owner.toString()).getKeys(false))
                list.add(new Character(owner, UUID.fromString(characterUUID)));

        return list;
    }
}
