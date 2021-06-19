package com.Theeef.me.interaction.character;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.backgrounds.Background;
import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;

import java.util.HashMap;
import java.util.UUID;

public class Character {

    private static Echovale plugin = Echovale.getPlugin(Echovale.class);

    private final UUID uuid;
    private final UUID owner;
    private final String name;
    private final Race race;
    private final Subrace subrace;
    private final HashMap<DNDClass, Integer> classes;
    private final HashMap<AbilityScore, Integer> baseAbilityScores; // Does not include ability score modifiers achieved at higher level, or any effects
    private final Background background;

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
        this.baseAbilityScores = baseAbilityScores;
        this.background = background;

        saveCharacter();
    }

    public Character(UUID owner, String name, Race race, Subrace subrace, DNDClass dndclass, int cha, int con, int dex, int intel, int str, int wis, Background background) {
        this(owner, name, race, subrace, Character.newClassMap(dndclass), newAbilityScoreMap(cha, con, dex, intel, str, wis), background);
    }

    private void saveCharacter() {
        // TODO: Save basic character info
        // How to store and retrieve choices?
        // How to make choices

        plugin.getConfigManager().getCharacters().set(getSavePath() + ".name", this.name);
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".race", this.race.getUrl());
        plugin.getConfigManager().getCharacters().set(getSavePath() + ".subrace", this.subrace == null ? null : this.subrace.getUrl());

        for (DNDClass dndclass : this.classes.keySet())
            plugin.getConfigManager().getCharacters().set(getSavePath() + ".classes." + dndclass.getIndex(), this.classes.get(dndclass));

        for (AbilityScore abilityScore : this.baseAbilityScores.keySet())
            plugin.getConfigManager().getCharacters().set(getSavePath() + ".baseAbilityScores." + abilityScore.getIndex(), this.baseAbilityScores.get(abilityScore));

        plugin.getConfigManager().getCharacters().set(getSavePath() + ".background", this.background.getUrl());
        plugin.getConfigManager().saveCharacters();
    }

    private String getSavePath() {
        return this.owner.toString() + "." + this.uuid.toString();
    }

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

    // Get methods
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

    public HashMap<AbilityScore, Integer> getBaseAbilityScores() {
        return this.baseAbilityScores;
    }

    public Background getBackground() {
        return this.background;
    }

}
