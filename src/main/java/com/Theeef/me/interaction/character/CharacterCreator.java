package com.Theeef.me.interaction.character;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.backgrounds.Background;
import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.Feature;
import com.Theeef.me.api.classes.Level;
import com.Theeef.me.api.classes.SpellcastingLevel;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.AbilityBonus;
import com.Theeef.me.api.common.choice.*;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CharacterCreator {

    public static HashMap<UUID, CharacterCreator> charactersBeingCreated = Maps.newHashMap();

    private final CharacterCreatorItems characterCreatorItems;
    private final CharacterCreatorMenus menus;
    private final UUID player;
    private final ChoiceResult raceChoiceResult;
    private ChoiceResult subraceChoiceResult;
    private final ChoiceResult classChoiceResult;
    private ChoiceResult raceProfChoiceResult;
    private ChoiceResult raceLanguageChoiceResult;
    private ChoiceResult subraceLanguageChoiceResult;
    private final ChoiceResult backgroundChoiceResult;
    private List<ChoiceResult> startingEquipmentChoiceResult;
    private List<ChoiceResult> backgroundEquipmentOptions;
    private ChoiceResult backgroundLanguageChoice;
    private ChoiceResult backgroundIdeals;
    private ChoiceResult backgroundPersonalityTraits;
    private ChoiceResult backgroundFlaws;
    private ChoiceResult backgroundBonds;
    private final List<ChoiceResult> classProficiencyChoiceResults;
    private ChoiceResult subclassChoiceResult;
    private final HashMap<Feature, ChoiceResult> subfeatureChoices;
    private final HashMap<Feature, ChoiceResult> expertiseChoices;
    private int abilityScorePoints;
    private final HashMap<AbilityScore, Integer> abilityScores;
    private static final HashMap<Integer, Integer> pointBuyMap = Maps.newHashMap();
    private final HashMap<Trait, ChoiceResult> traitSpellChoices;
    private final HashMap<Trait, ChoiceResult> subtraitChoices;

    public static void charCommand(Player player) {
        if (CharacterCreator.hasWIPCharacter(player)) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter(player);
            creator.raceMenu();
        } else
            new CharacterCreator(player);
    }

    public boolean characterComplete() {
        return raceComplete() && classComplete() && backgroundComplete() && abilityScoresComplete();
    }

    private boolean abilityScoresComplete() {
        return this.abilityScorePoints == 0;
    }

    public CharacterCreator(Player player) {
        this.characterCreatorItems = new CharacterCreatorItems(this);
        this.menus = new CharacterCreatorMenus(this);
        this.player = player.getUniqueId();
        this.subtraitChoices = Maps.newHashMap();
        this.traitSpellChoices = Maps.newHashMap();
        this.raceChoiceResult = new ChoiceResult(new Choice("race", 1, new ResourceListOptionSet("/api/races")));
        this.classChoiceResult = new ChoiceResult(new Choice("class", 1, new ResourceListOptionSet("/api/classes")));
        this.backgroundChoiceResult = new ChoiceResult(new Choice("background", 1, new ResourceListOptionSet("/api/backgrounds")));
        this.classProficiencyChoiceResults = Lists.newArrayList();
        this.subfeatureChoices = Maps.newHashMap();
        this.expertiseChoices = Maps.newHashMap();
        this.abilityScores = Maps.newHashMap();
        this.abilityScorePoints = 27;

        for (AbilityScore score : AbilityScore.values())
            this.abilityScores.put(score, 8);

        CharacterCreator.charactersBeingCreated.put(this.player, this);

        raceMenu();

        if (pointBuyMap.isEmpty()) {
            pointBuyMap.put(8, 0);
            pointBuyMap.put(9, 1);
            pointBuyMap.put(10, 2);
            pointBuyMap.put(11, 3);
            pointBuyMap.put(12, 4);
            pointBuyMap.put(13, 5);
            pointBuyMap.put(14, 7);
            pointBuyMap.put(15, 9);
        }
    }

    public void goToPage(String page) {
        switch (page) {
            case "ability scores":
                abilityScoresMenu();
                break;
            case "race":
                raceMenu();
                break;
            case "subrace":
                menus.subraceMenu();
                break;
            case "racial traits":
                menus.racialTraitMenu();
                break;
            case "subrace traits":
                menus.subraceRacialTraitsMenu();
                break;
            case "class":
                menus.classMenu();
                break;
            case "subclass":
                subclassMenu();
                break;
            case "select race":
                new ChoiceMenu("Select Your Race", "race", this.raceChoiceResult).open(getPlayer());
                break;
            case "select subrace":
                new ChoiceMenu("Select Your Subrace", "subrace", this.subraceChoiceResult).open(getPlayer());
                break;
            case "select class":
                new ChoiceMenu("Select Your Class", "class", this.classChoiceResult).open(getPlayer());
                break;
            case "race proficiency choice":
                new ChoiceMenu("Choose " + getRace().getStartingProficiencyOptions().getChoiceAmount() + (getRace().getStartingProficiencyOptions().getChoiceAmount() > 1 ? " Proficiencies" : " Proficiency"), "race", this.raceProfChoiceResult).open(getPlayer());
                break;
            case "race language choice":
                new ChoiceMenu("Choose " + getRace().getLanguageOptions().getChoiceAmount() + (getRace().getLanguageOptions().getChoiceAmount() > 1 ? " Languages" : " Language"), "race", this.raceLanguageChoiceResult).open(getPlayer());
                break;
            case "subrace language choice":
                new ChoiceMenu("Choose " + getSubrace().getLanguageOptions().getChoiceAmount() + (getSubrace().getLanguageOptions().getChoiceAmount() > 1 ? " Languages" : " Language"), "subrace", this.subraceLanguageChoiceResult).open(getPlayer());
                break;
            case "class levels":
                menus.classLevelsMenu();
                break;
            case "class features":
                featureTable(1);
                break;
            case "starting equipment":
                menus.startingEquipmentMenu();
                break;
            case "spellcasting":
                menus.spellcastingMenu();
                break;
            case "spellcastingTable":
                spellcastingTable(1);
                break;
            case "leveling perks":
                menus.perkInfoMenu(1);
                break;
            case "backgrounds":
                menus.backgroundMenu();
                break;
            case "select background":
                new ChoiceMenu("Select A Background", "backgrounds", this.backgroundChoiceResult).open(getPlayer());
                break;
            case "background language options":
                new ChoiceMenu("Choose " + getBackground().getLanguageOptions().getChoiceAmount() + (getBackground().getLanguageOptions().getChoiceAmount() > 1 ? " Languages" : " Language"), "backgrounds", this.backgroundLanguageChoice).open(getPlayer());
                break;
            case "background equipment":
                menus.backgroundStartingEquipmentMenu();
                break;
            case "background roleplay":
                menus.backgroundRoleplayMenu();
                break;
            case "choose personality traits":
                new ChoiceMenu("Choose " + this.backgroundPersonalityTraits.getChoice().getChoiceAmount() + (this.backgroundPersonalityTraits.getChoice().getChoiceAmount() > 1 ? " Traits" : " Trait"), "background roleplay", this.backgroundPersonalityTraits).open(getPlayer());
                break;
            case "choose bonds":
                new ChoiceMenu("Choose " + this.backgroundBonds.getChoice().getChoiceAmount() + (this.backgroundBonds.getChoice().getChoiceAmount() > 1 ? " Bonds" : " Bond"), "background roleplay", this.backgroundBonds).open(getPlayer());
                break;
            case "choose flaws":
                new ChoiceMenu("Choose " + this.backgroundFlaws.getChoice().getChoiceAmount() + (this.backgroundFlaws.getChoice().getChoiceAmount() > 1 ? " Flaws" : " Flaw"), "background roleplay", this.backgroundFlaws).open(getPlayer());
                break;
            case "choose ideals":
                new ChoiceMenu("Choose " + this.backgroundIdeals.getChoice().getChoiceAmount() + (this.backgroundIdeals.getChoice().getChoiceAmount() > 1 ? " Ideals" : " Ideal"), "background roleplay", this.backgroundIdeals).open(getPlayer());
                break;
            case "class proficiency choices":
                menus.classProficiencyChoicesMenu();
                break;
            case "select subclass":
                new ChoiceMenu("Select 1 Subclass", "subclass", this.subclassChoiceResult).open(getPlayer());
                break;
            case "subclass features":
                subclassFeatureMenu(getSubclass());
                break;
            default:
                throw new NullPointerException(page + " DIDNT KNOW HOW TO DIRECT");
        }
    }

    public void abilityScoresMenu() {
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Ability Scores: " + getAbilityScorePointsLeft() + " Points Left");

        inventory.setItem(2 * 9 + 1, this.characterCreatorItems.abilityScoreItem(AbilityScore.INT));
        inventory.setItem(2 * 9 + 2, this.characterCreatorItems.abilityScoreItem(AbilityScore.DEX));
        inventory.setItem(2 * 9 + 3, this.characterCreatorItems.abilityScoreItem(AbilityScore.STR));
        inventory.setItem(2 * 9 + 5, this.characterCreatorItems.abilityScoreItem(AbilityScore.CHA));
        inventory.setItem(2 * 9 + 6, this.characterCreatorItems.abilityScoreItem(AbilityScore.WIS));
        inventory.setItem(2 * 9 + 7, this.characterCreatorItems.abilityScoreItem(AbilityScore.CON));

        if (this.abilityScores.get(AbilityScore.INT) < 15)
            inventory.setItem(9 + 1, CharacterCreatorItems.increaseAbilityScoreItem(AbilityScore.INT));
        if (this.abilityScores.get(AbilityScore.DEX) < 15)
            inventory.setItem(9 + 2, CharacterCreatorItems.increaseAbilityScoreItem(AbilityScore.DEX));
        if (this.abilityScores.get(AbilityScore.STR) < 15)
            inventory.setItem(9 + 3, CharacterCreatorItems.increaseAbilityScoreItem(AbilityScore.STR));
        if (this.abilityScores.get(AbilityScore.CHA) < 15)
            inventory.setItem(9 + 5, CharacterCreatorItems.increaseAbilityScoreItem(AbilityScore.CHA));
        if (this.abilityScores.get(AbilityScore.WIS) < 15)
            inventory.setItem(9 + 6, CharacterCreatorItems.increaseAbilityScoreItem(AbilityScore.WIS));
        if (this.abilityScores.get(AbilityScore.CON) < 15)
            inventory.setItem(9 + 7, CharacterCreatorItems.increaseAbilityScoreItem(AbilityScore.CON));

        if (this.abilityScores.get(AbilityScore.INT) > 8)
            inventory.setItem(3 * 9 + 1, CharacterCreatorItems.decreaseAbilityScoreItem(AbilityScore.INT));
        if (this.abilityScores.get(AbilityScore.DEX) > 8)
            inventory.setItem(3 * 9 + 2, CharacterCreatorItems.decreaseAbilityScoreItem(AbilityScore.DEX));
        if (this.abilityScores.get(AbilityScore.STR) > 8)
            inventory.setItem(3 * 9 + 3, CharacterCreatorItems.decreaseAbilityScoreItem(AbilityScore.STR));
        if (this.abilityScores.get(AbilityScore.CHA) > 8)
            inventory.setItem(3 * 9 + 5, CharacterCreatorItems.decreaseAbilityScoreItem(AbilityScore.CHA));
        if (this.abilityScores.get(AbilityScore.WIS) > 8)
            inventory.setItem(3 * 9 + 6, CharacterCreatorItems.decreaseAbilityScoreItem(AbilityScore.WIS));
        if (this.abilityScores.get(AbilityScore.CON) > 8)
            inventory.setItem(3 * 9 + 7, CharacterCreatorItems.decreaseAbilityScoreItem(AbilityScore.CON));

        inventory.setItem(5 * 9, CharacterCreatorItems.previousPage("backgrounds"));
        inventory.setItem(6 * 9 - 1, CharacterCreatorItems.nextPage("backgrounds"));

        getPlayer().openInventory(inventory);
    }

    public int getAbilityScorePointsLeft() {
        return this.abilityScorePoints;
    }

    public void increaseScore(AbilityScore score) {
        int currentScore = this.abilityScores.get(score);

        if (currentScore >= 15)
            return;

        int pointsNeeded = CharacterCreator.pointBuyMap.get(currentScore + 1) - CharacterCreator.pointBuyMap.get(currentScore);

        if (this.abilityScorePoints >= pointsNeeded) {
            this.abilityScorePoints -= pointsNeeded;
            this.abilityScores.put(score, ++currentScore);
        }
    }

    public void decreaseScore(AbilityScore score) {
        int currentScore = this.abilityScores.get(score);

        if (currentScore <= 8)
            return;

        int granted = CharacterCreator.pointBuyMap.get(currentScore) - CharacterCreator.pointBuyMap.get(currentScore - 1);

        if (this.abilityScorePoints + granted <= 27) {
            this.abilityScorePoints += granted;
            this.abilityScores.put(score, --currentScore);
        }
    }

    // Menu
    List<ChoiceResult> getClassSubfeatureChoices() {
        List<ChoiceResult> list = Lists.newArrayList();

        for (Feature feature : this.subfeatureChoices.keySet())
            if (feature.getSubclass() == null)
                list.add(this.subfeatureChoices.get(feature));

        return list;
    }

    public static String nthLevelString(int level) {
        if (level > 3 && level < 21)
            return level + "th";

        switch (level % 10) {
            case 1:
                return level + "st";
            case 2:
                return level + "nd";
            case 3:
                return level + "rd";
            default:
                return level + "th";
        }
    }

    public void featureTable(int level) {
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Level Features");
        List<Level> levels = getDNDClass().getClassLevels(false);

        for (int i = 0; i < Math.min(20 - (level - 1), 5); i++) {
            inventory.setItem(i * 9, featureXPItem(level + i));
            Level levelObj = null;

            for (Level levelO : levels)
                if (levelO.getLevel() == level + i)
                    levelObj = levelO;

            if (levelObj == null)
                throw new NullPointerException();

            for (int j = 0; j < levelObj.getFeatures().size(); j++)
                inventory.setItem(i * 9 + 1 + j, featureItem(levelObj.getFeatures().get(j)));
        }

        for (int i = 45; i < 45 + 9; i++)
            inventory.setItem(i, CharacterCreatorItems.borderItem());

        inventory.setItem(49, featureSignItem(level));
        inventory.setItem(45, CharacterCreatorItems.previousPage("class levels"));

        if (level > 1)
            inventory.setItem(48, CharacterCreatorItems.previousFeatureRow());
        if (level <= 15)
            inventory.setItem(50, CharacterCreatorItems.nextFeatureRow());

        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equals("Level Features") && !classFeaturesComplete()) {
                    int currentLevel = Integer.parseInt(NBTHandler.getString(getPlayer().getOpenInventory().getItem(45 + 4), "row"));

                    if (currentLevel == level)
                        featureTable(level);
                }
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public ItemStack featureItem(Feature feature) {
        ItemStack item;

        if (feature.getName().equals("Ability Score Improvement"))
            item = new ItemStack(Material.NETHER_STAR);
        else
            item = new ItemStack(Material.BOOK);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + feature.getName());
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Obtained at level " + feature.getLevel(), "");

        for (String descLine : feature.getDescription()) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + descLine));

            if (!descLine.equals(feature.getDescription().get(feature.getDescription().size() - 1)))
                lore.add("");
        }

        boolean isSelected = this.subfeatureChoices.containsKey(feature.getParent()) && this.subfeatureChoices.get(feature.getParent()).alreadyChosen(new SingleOption(Lists.newArrayList(), new CountedReference(1, feature.getReference())));

        if (feature.getFeatureSpecific() != null && feature.getFeatureSpecific().getSubfeatureOptions() != null) {
            lore.add("");
            lore.add(ChatColor.WHITE + "Click to select this feature's subfeatures");
        }

        if (isSelected) {
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 1, true);
            lore.add("");
            lore.add(ChatColor.WHITE + "Click to unselect");
        } else if (feature.getParent() != null) {
            lore.add("");
            lore.add(ChatColor.WHITE + "Click to select");
        }

        if (feature.getFeatureSpecific() != null && feature.getFeatureSpecific().getExpertiseOptions() != null) {
            lore.add("");
            lore.add(ChatColor.WHITE + "Click to select your expertise");
        }

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "feature", feature.getIndex());


        if (feature.getFeatureSpecific() != null) {
            if (feature.getFeatureSpecific().getSubfeatureOptions() != null)
                return new ChoiceMenuItem(item, this.subfeatureChoices.get(feature)).getItem();
            else if (feature.getFeatureSpecific().getExpertiseOptions() != null)
                return new ChoiceMenuItem(item, this.expertiseChoices.get(feature)).getItem();
        }

        return item;
    }

    private static ItemStack featureSignItem(int level) {
        ItemStack item = new ItemStack(Material.SPRUCE_SIGN);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Viewing features for levels " + level + " through " + (level + 4));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "row", Integer.toString(level));
    }

    private static ItemStack featureXPItem(int level) {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, level);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "Level " + level + "'s Features");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "The features to the right are obtained at level " + level));
        item.setItemMeta(meta);

        return item;
    }

    public void spellcastingTable(int row) {
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "-Spell Slots per Spell Level-");
        List<Level> levels = getDNDClass().getClassLevels(false);

        for (int i = 0; i < 9; i++)
            inventory.setItem(i, CharacterCreatorItems.spellSlotColumn(i + 1));

        for (int i = 45; i < 45 + 9; i++)
            inventory.setItem(i, CharacterCreatorItems.borderItem());

        for (int i = 0; i < Math.min(4, 20 - (row - 1)); i++) {
            inventory.setItem(9 + i * 9, new ItemStack(Material.PURPLE_DYE, i));
            SpellcastingLevel spellcastingLevel = levels.get(row + i - 1).getSpellcasting();

            for (int spellLevel = 1; spellLevel <= 9; spellLevel++)
                inventory.setItem(9 + 9 * i + spellLevel - 1, CharacterCreatorItems.spellSlotsAtLevelItem(spellcastingLevel, spellLevel, row + i));
        }


        inventory.setItem(49, CharacterCreatorItems.spellInfoSign(row));

        if (row > 1)
            inventory.setItem(48, CharacterCreatorItems.spellTablePrevRow());

        if (row <= 16)
            inventory.setItem(50, CharacterCreatorItems.spellTableNextRow());

        inventory.setItem(45, CharacterCreatorItems.previousPage("class levels"));

        getPlayer().openInventory(inventory);
    }

    private boolean classComplete() {
        boolean equipmentComplete = true;

        for (ChoiceResult equipmentResult : this.startingEquipmentChoiceResult)
            if (!equipmentResult.isComplete()) {
                equipmentComplete = false;
                break;
            }

        boolean proficiencyComplete = true;

        for (ChoiceResult proficiencyResult : this.classProficiencyChoiceResults)
            if (!proficiencyResult.isComplete()) {
                proficiencyComplete = false;
                break;
            }

        return getDNDClass() != null && equipmentComplete && proficiencyComplete && classFeaturesComplete() && subclassComplete();
    }

    boolean classFeaturesComplete() {
        boolean subfeaturesComplete = true;

        for (Feature feature : this.subfeatureChoices.keySet())
            if (feature.getSubclass() == null && !this.subfeatureChoices.get(feature).isComplete()) {
                subfeaturesComplete = false;
                break;
            }

        return subfeaturesComplete;
    }

    private boolean subclassComplete() {
        boolean subclassSubfeaturesComplete = true;

        for (Feature feature : this.subfeatureChoices.keySet())
            if (feature.getSubclass() != null && !this.subfeatureChoices.get(feature).isComplete()) {
                subclassSubfeaturesComplete = false;
                break;
            }

        return this.subclassChoiceResult == null || (getSubclass() != null && subclassSubfeaturesComplete);
    }

    boolean backgroundComplete() {
        boolean backgroundEquipmentOptionsComplete = true;

        if (this.backgroundEquipmentOptions != null)
            for (ChoiceResult equipmentResult : this.backgroundEquipmentOptions)
                if (!equipmentResult.isComplete()) {
                    backgroundEquipmentOptionsComplete = false;
                    break;
                }

        return getBackground() != null && backgroundEquipmentOptionsComplete && this.backgroundFlaws.isComplete() && this.backgroundPersonalityTraits.isComplete() && this.backgroundBonds.isComplete() && this.backgroundIdeals.isComplete();
    }

    public void subclassMenu() {
        if (this.subclassChoiceResult != null && !this.subclassChoiceResult.isComplete()) {
            new ChoiceMenu("Select 1 Subclass", "subclass", this.subclassChoiceResult).open(getPlayer());
            return;
        }

        Inventory inventory;

        if (this.subclassChoiceResult == null) {
            inventory = Bukkit.createInventory(null, 3 * 9, "Subclass");

            for (Subclass subclass : getDNDClass().getSubclasses())
                inventory.addItem(CharacterCreatorItems.subclassItem(subclass));

            inventory.setItem(2 * 9, CharacterCreatorItems.previousPage("class"));
        } else {
            inventory = Bukkit.createInventory(null, 5 * 9, "Subclass: " + getSubclass().getName());

            inventory.setItem(9 + 4, this.characterCreatorItems.subclassMenuItem());

            if (getSubclass().getSpells().size() == 0)
                inventory.setItem(3 * 9 + 4, CharacterCreatorItems.subclassLevelItem());
            else {
                inventory.setItem(3 * 9 + 3, CharacterCreatorItems.subclassLevelItem());
                inventory.setItem(3 * 9 + 5, this.characterCreatorItems.subclassSpellItem());
            }

            inventory.setItem(4 * 9, CharacterCreatorItems.previousPage("class"));
        }

        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().startsWith("Subclass") && !getPlayer().getOpenInventory().getTitle().equals("Subclass Features") && !subclassComplete())
                    subclassMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void subclassFeatureMenu(Subclass subclass) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, "Subclass Features");

        for (Level level : subclass.getSublassLevels())
            for (Feature feature : level.getFeatures())
                inventory.addItem(featureItem(feature));

        inventory.setItem(2 * 9, CharacterCreatorItems.previousPage("subclass"));
        getPlayer().openInventory(inventory);
    }

    public void raceMenu() {
        Inventory inventory;

        if (getRace() == null)
            inventory = Bukkit.createInventory(null, 27, "Race");
        else
            inventory = Bukkit.createInventory(null, 9 * 5, "Race: " + getRace().getName());

        inventory.setItem(13, this.characterCreatorItems.selectRaceItem());
        inventory.setItem(inventory.getSize() - 1, CharacterCreatorItems.nextPage("class"));

        if (getRace() != null) {
            int align = (getRace().getStartingProficiencies().size() > 0 ? 1 : 0) + (getRace().getTraits().size() > 0 ? 1 : 0) + (getRace().getLanguageOptions() == null ? 0 : 1) + (getRace().getStartingProficiencyOptions() == null ? 0 : 1) + (getRace().getSubraces().size() > 0 ? 1 : 0);
            int count = 0;

            if (getRace().getTraits().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.characterCreatorItems.raceTraitItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.characterCreatorItems.raceTraitItem());
                count++;
            }

            if (getRace().getStartingProficiencies().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, CharacterCreatorItems.startingProficienciesItem(getRace().getStartingProficiencies()));
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), CharacterCreatorItems.startingProficienciesItem(getRace().getStartingProficiencies()));
                count++;
            }

            if (getRace().getStartingProficiencyOptions() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.characterCreatorItems.startingProfChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.characterCreatorItems.startingProfChoiceItem());
                count++;
            }

            if (getRace().getLanguageOptions() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.characterCreatorItems.raceLanguageChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.characterCreatorItems.raceLanguageChoiceItem());
                count++;
            }

            if (getRace().getSubraces().size() > 0)
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.characterCreatorItems.subracesItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.characterCreatorItems.subracesItem());
        }

        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().startsWith("Race") && !raceComplete())
                    raceMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    private boolean raceComplete() {
        boolean subtraitsComplete = true;
        boolean traitSpellChoicesComplete = true;

        if (getRace() != null)
            for (Trait trait : getRace().getTraits())
                if (this.subtraitChoices.containsKey(trait) && !this.subtraitChoices.get(trait).isComplete())
                    subtraitsComplete = false;

        for (ChoiceResult spellTraitResult : this.traitSpellChoices.values())
            if (!spellTraitResult.isComplete()) {
                traitSpellChoicesComplete = false;
            }

        return getRace() != null && subtraitsComplete && traitSpellChoicesComplete && (this.raceProfChoiceResult == null || this.raceProfChoiceResult.isComplete()) && (this.raceLanguageChoiceResult == null || this.raceLanguageChoiceResult.isComplete()) && subraceComplete();
    }

    boolean subraceComplete() {
        boolean subtraitsComplete = true;

        if (getSubrace() != null)
            for (Trait trait : getSubrace().getRacialTraits())
                if (this.subtraitChoices.containsKey(trait) && !this.subtraitChoices.get(trait).isComplete()) {
                    subtraitsComplete = false;
                    break;
                }

        System.out.println(getSubrace() == null ? null : getSubrace().getName());

        return this.subraceChoiceResult == null || (getSubrace() != null && (this.subraceLanguageChoiceResult == null || this.subraceLanguageChoiceResult.isComplete()) && subtraitsComplete);
    }

    // Object based Item

    // Setter Methods
    public void setSubclass(SingleOption subclassOption) {
        Subclass subclass = Subclass.fromIndex(subclassOption.getItem().getReference().getIndex());

        if (getSubclass() == null || !getSubclass().equals(subclass)) {
            this.subclassChoiceResult.clearChoices();
            this.subfeatureChoices.clear();
            this.expertiseChoices.clear();
        }

        this.subclassChoiceResult.choose(subclassOption);

        for (Level level : getDNDClass().getClassLevels(false))
            for (Feature feature : level.getFeatures())
                if (feature.getFeatureSpecific() != null) {
                    if (feature.getFeatureSpecific().getSubfeatureOptions() != null)
                        this.subfeatureChoices.put(feature, new ChoiceResult(feature.getFeatureSpecific().getSubfeatureOptions()));
                    if (feature.getFeatureSpecific().getExpertiseOptions() != null) {
                        Choice choice = feature.getFeatureSpecific().getExpertiseOptions();
                        choice.setType("expertise");
                        this.expertiseChoices.put(feature, new ChoiceResult(choice));
                    }
                }

        for (Level level : getSubclass().getSublassLevels())
            for (Feature feature : level.getFeatures())
                if (feature.getFeatureSpecific() != null) {
                    if (feature.getFeatureSpecific().getSubfeatureOptions() != null)
                        this.subfeatureChoices.put(feature, new ChoiceResult(feature.getFeatureSpecific().getSubfeatureOptions()));
                    if (feature.getFeatureSpecific().getExpertiseOptions() != null)
                        this.expertiseChoices.put(feature, new ChoiceResult(feature.getFeatureSpecific().getExpertiseOptions()));
                }
    }

    public void setClass(SingleOption classOption) {
        DNDClass dndclass = DNDClass.fromIndex((classOption).getItem().getReference().getIndex());

        if (getDNDClass() == null || !getDNDClass().equals(dndclass)) {
            this.startingEquipmentChoiceResult = Lists.newArrayList();

            for (Choice choice : dndclass.getStartingEquipmentOptions())
                this.startingEquipmentChoiceResult.add(new ChoiceResult(choice));

            this.classProficiencyChoiceResults.clear();

            if (this.classChoiceResult.isComplete())
                this.classChoiceResult.clearChoices();

            this.subfeatureChoices.clear();
            this.expertiseChoices.clear();
        }

        this.classChoiceResult.choose(classOption);

        for (Level level : getDNDClass().getClassLevels(false))
            for (Feature feature : level.getFeatures())
                if (feature.getFeatureSpecific() != null) {
                    if (feature.getFeatureSpecific().getSubfeatureOptions() != null)
                        this.subfeatureChoices.put(feature, new ChoiceResult(feature.getFeatureSpecific().getSubfeatureOptions()));
                    else if (feature.getFeatureSpecific().getExpertiseOptions() != null) {
                        Choice choice = feature.getFeatureSpecific().getExpertiseOptions();
                        choice.setType("expertise");
                        this.expertiseChoices.put(feature, new ChoiceResult(choice));
                    }
                }

        this.subclassChoiceResult = getDNDClass().hasFirstLevelSubclass() ? new ChoiceResult(new Choice("subclass", 1, new ResourceListOptionSet(getDNDClass().getUrl() + "/subclasses"))) : null;

        List<Option> subraceProficiencies = Lists.newArrayList();
        List<Option> classProficiencies = Lists.newArrayList();
        List<Option> backgroundProficiencies = Lists.newArrayList();
        List<Option> raceProficiencies = Lists.newArrayList();

        if (getRace() != null)
            for (Proficiency proficiency : getRace().getStartingProficiencies())
                raceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        if (getSubrace() != null)
            for (Proficiency proficiency : getSubrace().getStartingProficiencies())
                subraceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        if (getDNDClass() != null)
            for (Proficiency proficiency : getDNDClass().getProficiencies())
                classProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        if (getBackground() != null)
            for (Proficiency proficiency : getBackground().getProficiencies())
                backgroundProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        subraceProficiencies.addAll(classProficiencies);
        subraceProficiencies.addAll(backgroundProficiencies);
        raceProficiencies.addAll(subraceProficiencies);

        if (this.raceProfChoiceResult != null) {
            this.raceProfChoiceResult.clearOptionExclusions();
            this.raceProfChoiceResult.exclude(Arrays.copyOf(backgroundProficiencies.toArray(), backgroundProficiencies.size(), Option[].class));
        }

        for (Choice choice : getDNDClass().getProficiencyChoices()) {
            List<ChoiceResult> excludedResults = Lists.newArrayList(this.raceProfChoiceResult);
            excludedResults.addAll(this.classProficiencyChoiceResults);

            this.classProficiencyChoiceResults.add(new ChoiceResult(choice, Lists.newArrayList(raceProficiencies), Arrays.copyOf(excludedResults.toArray(), excludedResults.size(), ChoiceResult[].class)));
        }
    }

    public void setBackground(SingleOption backgroundOption) {
        Background background = new Background(backgroundOption.getItem().getReference().getUrl());

        if (getBackground() == null || !getBackground().equals(background)) {
            this.backgroundEquipmentOptions = Lists.newArrayList();

            for (Choice choice : background.getStartingEquipmentOptions())
                this.backgroundEquipmentOptions.add(new ChoiceResult(choice));

            this.backgroundIdeals = new ChoiceResult(background.getIdeals());
            this.backgroundPersonalityTraits = new ChoiceResult(background.getPersonalityTraits());
            this.backgroundFlaws = new ChoiceResult(background.getFlaws());
            this.backgroundBonds = new ChoiceResult(background.getBonds());

            List<Option> raceLanguages = Lists.newArrayList();
            if (getRace() != null)
                for (Language language : getRace().getLanguages())
                    raceLanguages.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, language.getReference())));

            List<Option> subraceLanguages = Lists.newArrayList();
            if (getSubrace() != null)
                for (Language language : getSubrace().getLanguages())
                    subraceLanguages.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, language.getReference())));

            raceLanguages.addAll(subraceLanguages);

            this.backgroundLanguageChoice = new ChoiceResult(background.getLanguageOptions(), raceLanguages, this.raceLanguageChoiceResult, this.subraceLanguageChoiceResult);

            if (this.backgroundChoiceResult.isComplete())
                this.backgroundChoiceResult.clearChoices();
        }

        this.backgroundChoiceResult.choose(backgroundOption);

        List<Option> raceProficiencies = Lists.newArrayList();
        List<Option> subraceProficiencies = Lists.newArrayList();
        List<Option> classProficiencies = Lists.newArrayList();
        List<Option> backgroundProficiencies = Lists.newArrayList();

        if (getRace() != null)
            for (Proficiency proficiency : getRace().getStartingProficiencies())
                raceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        if (getSubrace() != null)
            for (Proficiency proficiency : getSubrace().getStartingProficiencies())
                subraceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        if (getDNDClass() != null)
            for (Proficiency proficiency : getDNDClass().getProficiencies())
                classProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        if (getBackground() != null)
            for (Proficiency proficiency : getBackground().getProficiencies())
                backgroundProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        if (this.classProficiencyChoiceResults != null)
            for (ChoiceResult result : this.classProficiencyChoiceResults) {
                result.clearOptionExclusions();
                result.exclude(Arrays.copyOf(raceProficiencies.toArray(), raceProficiencies.size(), Option[].class));
                result.exclude(Arrays.copyOf(subraceProficiencies.toArray(), subraceProficiencies.size(), Option[].class));
                result.exclude(Arrays.copyOf(backgroundProficiencies.toArray(), backgroundProficiencies.size(), Option[].class));
            }

        subraceProficiencies.addAll(classProficiencies);
        subraceProficiencies.addAll(backgroundProficiencies);

        if (this.raceProfChoiceResult != null) {
            this.raceProfChoiceResult.clearOptionExclusions();
            this.raceProfChoiceResult.exclude(Arrays.copyOf(subraceProficiencies.toArray(), subraceProficiencies.size(), Option[].class));
        }
    }

    public void setSubrace(SingleOption subraceOption) {
        Subrace subrace = subraceOption == null ? null : Subrace.fromIndex((subraceOption).getItem().getReference().getIndex());

        if (subrace == null || (getSubrace() != null && !subrace.equals(getSubrace()))) {
            if (subrace == null && this.subraceChoiceResult != null)
                this.subraceChoiceResult.clearChoices();

            this.subraceLanguageChoiceResult = null;
            this.traitSpellChoices.clear();

            for (Trait trait : this.subtraitChoices.keySet())
                if (!trait.getSubraces().contains(subrace) && !trait.getRaces().contains(getRace()))
                    this.subtraitChoices.remove(trait);
        }

        if (subrace != null)
            this.subraceChoiceResult.choose(subraceOption);

        if (getSubrace() != null) {
            for (Trait trait : getSubrace().getRacialTraits())
                if (trait.getTraitSpecific() != null) {
                    if (trait.getTraitSpecific().getSubtraitOptions() != null)
                        this.subtraitChoices.put(trait, new ChoiceResult(trait.getTraitSpecific().getSubtraitOptions()));
                    if (trait.getTraitSpecific().getSpellOptions() != null)
                        this.traitSpellChoices.put(trait, new ChoiceResult(trait.getTraitSpecific().getSpellOptions()));
                }

            // Removing Language Choice Overlap
            List<Option> raceLanguages = Lists.newArrayList();
            if (getRace() != null)
                for (Language language : getRace().getLanguages())
                    raceLanguages.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, language.getReference())));

            List<Option> subraceLanguages = Lists.newArrayList();
            if (getSubrace() != null)
                for (Language language : getSubrace().getLanguages())
                    subraceLanguages.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, language.getReference())));

            raceLanguages.addAll(subraceLanguages);

            if (getSubrace().getLanguageOptions() != null)
                this.subraceLanguageChoiceResult = new ChoiceResult(getSubrace().getLanguageOptions(), raceLanguages, this.raceLanguageChoiceResult, this.backgroundLanguageChoice);

            if (getBackground() != null) {
                this.backgroundLanguageChoice.clearOptionExclusions();
                this.backgroundLanguageChoice.exclude(Arrays.copyOf(raceLanguages.toArray(), raceLanguages.size(), Option[].class));
            }


            // Removing Proficiency Choice Overlap
            List<Option> subraceProficiencies = Lists.newArrayList();
            List<Option> classProficiencies = Lists.newArrayList();
            List<Option> backgroundProficiencies = Lists.newArrayList();
            List<Option> raceProficiencies = Lists.newArrayList();
            List<Option> classProficiencyExclusions = Lists.newArrayList();

            if (getRace() != null)
                for (Proficiency proficiency : getRace().getStartingProficiencies())
                    raceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

            if (getSubrace() != null)
                for (Proficiency proficiency : getSubrace().getStartingProficiencies())
                    subraceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

            if (getDNDClass() != null)
                for (Proficiency proficiency : getDNDClass().getProficiencies())
                    classProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

            if (getBackground() != null)
                for (Proficiency proficiency : getBackground().getProficiencies())
                    backgroundProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

            classProficiencyExclusions.addAll(subraceProficiencies);
            classProficiencyExclusions.addAll(raceProficiencies);
            classProficiencyExclusions.addAll(backgroundProficiencies);
            subraceProficiencies.addAll(classProficiencies);
            subraceProficiencies.addAll(backgroundProficiencies);

            if (this.raceLanguageChoiceResult != null) {
                this.raceLanguageChoiceResult.clearOptionExclusions();
                this.raceLanguageChoiceResult.exclude(Arrays.copyOf(backgroundProficiencies.toArray(), backgroundProficiencies.size(), Option[].class));
            }

            for (ChoiceResult result : this.classProficiencyChoiceResults) {
                result.clearOptionExclusions();
                result.exclude(Arrays.copyOf(classProficiencyExclusions.toArray(), classProficiencyExclusions.size(), Option[].class));
            }
        }
    }

    public void setRace(SingleOption raceOption) {
        Race race = Race.fromIndex(raceOption.getItem().getReference().getIndex());

        if (getRace() == null || !race.equals(getRace())) {
            setSubrace(null);
            this.raceProfChoiceResult = null;
            this.raceLanguageChoiceResult = null;

            for (Trait trait : this.subtraitChoices.keySet())
                if (!trait.getRaces().contains(race))
                    this.subtraitChoices.remove(trait);

            if (this.raceChoiceResult.isComplete())
                this.raceChoiceResult.clearChoices();

            this.subraceChoiceResult = race.getSubraces().size() > 0 ? new ChoiceResult(new Choice("subrace", 1, new ResourceListOptionSet(race.getUrl() + "/subraces"))) : null;
            this.raceChoiceResult.choose(raceOption);

            for (Trait trait : getRace().getTraits())
                if (trait.getTraitSpecific() != null && trait.getTraitSpecific().getSubtraitOptions() != null)
                    this.subtraitChoices.put(trait, new ChoiceResult(trait.getTraitSpecific().getSubtraitOptions()));

            if (getRace().getStartingProficiencyOptions() != null) {
                List<Option> subraceProficiencies = Lists.newArrayList();
                List<Option> classProficiencies = Lists.newArrayList();
                List<Option> backgroundProficiencies = Lists.newArrayList();
                List<Option> raceProficiencies = Lists.newArrayList();
                List<Option> classProficiencyExclusions = Lists.newArrayList();

                if (getRace() != null)
                    for (Proficiency proficiency : getRace().getStartingProficiencies())
                        raceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

                if (getSubrace() != null)
                    for (Proficiency proficiency : getSubrace().getStartingProficiencies())
                        subraceProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

                if (getDNDClass() != null)
                    for (Proficiency proficiency : getDNDClass().getProficiencies())
                        classProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

                if (getBackground() != null)
                    for (Proficiency proficiency : getBackground().getProficiencies())
                        backgroundProficiencies.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

                classProficiencyExclusions.addAll(subraceProficiencies);
                classProficiencyExclusions.addAll(raceProficiencies);
                classProficiencyExclusions.addAll(backgroundProficiencies);
                subraceProficiencies.addAll(classProficiencies);
                subraceProficiencies.addAll(backgroundProficiencies);

                this.raceProfChoiceResult = new ChoiceResult(getRace().getStartingProficiencyOptions(), subraceProficiencies, Arrays.copyOf(this.classProficiencyChoiceResults.toArray(), this.classProficiencyChoiceResults.size(), ChoiceResult[].class));

                for (ChoiceResult result : this.classProficiencyChoiceResults) {
                    result.clearOptionExclusions();
                    result.exclude(Arrays.copyOf(classProficiencyExclusions.toArray(), classProficiencyExclusions.size(), Option[].class));
                }
            }

            List<Option> raceLanguages = Lists.newArrayList();
            for (Language language : getRace().getLanguages())
                raceLanguages.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, language.getReference())));

            List<Option> subraceLanguages = Lists.newArrayList();
            if (getSubrace() != null)
                for (Language language : getSubrace().getLanguages())
                    subraceLanguages.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, language.getReference())));

            if (getRace().getLanguageOptions() != null)
                this.raceLanguageChoiceResult = new ChoiceResult(getRace().getLanguageOptions(), Lists.newArrayList(), this.subraceLanguageChoiceResult, this.backgroundLanguageChoice);

            if (getBackground() != null) {
                this.backgroundLanguageChoice.clearOptionExclusions();
                this.backgroundLanguageChoice.exclude(Arrays.copyOf(raceLanguages.toArray(), raceLanguages.size(), Option[].class));
                this.backgroundLanguageChoice.exclude(Arrays.copyOf(subraceLanguages.toArray(), subraceLanguages.size(), Option[].class));
            }
        }
    }

    // Getter methods
    public HashMap<Feature, ChoiceResult> getSubfeatureChoices() {
        return this.subfeatureChoices;
    }

    public HashMap<Feature, ChoiceResult> getExpertiseChoices() {
        return this.expertiseChoices;
    }

    public CharacterCreatorMenus getMenus() {
        return this.menus;
    }

    public CharacterCreatorItems getItemCreator() {
        return new CharacterCreatorItems(this);
    }

    public ChoiceResult getClassChoiceResult() {
        return this.classChoiceResult;
    }

    public ChoiceResult getSubraceChoiceResult() {
        return this.subraceChoiceResult;
    }

    public ChoiceResult getRaceChoiceResult() {
        return this.raceChoiceResult;
    }

    public ChoiceResult getSubraceLanguageChoiceResult() {
        return this.subraceLanguageChoiceResult;
    }

    public ChoiceResult getRaceLanguageChoiceResult() {
        return this.raceLanguageChoiceResult;
    }

    public List<ChoiceResult> getClassProficiencyChoiceResults() {
        return this.classProficiencyChoiceResults;
    }

    public ChoiceResult getSubclassChoiceResult() {
        return this.subclassChoiceResult;
    }

    public ChoiceResult getBackgroundChoiceResult() {
        return this.backgroundChoiceResult;
    }

    public ChoiceResult getBackgroundIdeals() {
        return this.backgroundIdeals;
    }

    public ChoiceResult getBackgroundFlaws() {
        return this.backgroundFlaws;
    }

    public ChoiceResult getBackgroundPersonalityTraits() {
        return this.backgroundPersonalityTraits;
    }

    public ChoiceResult getBackgroundBonds() {
        return this.backgroundBonds;
    }

    public static HashMap<Integer, Integer> pointBuyMap() {
        return CharacterCreator.pointBuyMap;
    }

    public HashMap<AbilityScore, Integer> getAbilityScores() {
        return this.abilityScores;
    }

    public List<ChoiceResult> getStartingEquipmentChoiceResult() {
        return this.startingEquipmentChoiceResult;
    }

    public List<ChoiceResult> getBackgroundStartingEquipmentChoiceResult() {
        return this.backgroundEquipmentOptions;
    }

    public DNDClass getDNDClass() {
        return this.classChoiceResult.isComplete() ? new DNDClass(((SingleOption) this.classChoiceResult.getChosen().get(0)).getItem().getReference()) : null;
    }

    public Subclass getSubclass() {
        return this.subclassChoiceResult == null || !this.subclassChoiceResult.isComplete() ? null : new Subclass(((SingleOption) this.subclassChoiceResult.getChosen().get(0)).getItem().getReference().getUrl());
    }

    public Background getBackground() {
        return this.backgroundChoiceResult.isComplete() ? new Background(((SingleOption) this.backgroundChoiceResult.getChosen().get(0)).getItem().getReference().getUrl()) : null;
    }

    public HashMap<Trait, ChoiceResult> getTraitSpellChoices() {
        return this.traitSpellChoices;
    }

    public ChoiceResult getRaceProfChoiceResult() {
        return this.raceProfChoiceResult;
    }

    public Race getRace() {
        return this.raceChoiceResult.isComplete() ? new Race(((SingleOption) this.raceChoiceResult.getChosen().get(0)).getItem().getReference()) : null;
    }

    public Subrace getSubrace() {
        return this.subraceChoiceResult == null ? null : (this.subraceChoiceResult.isComplete() ? new Subrace(((SingleOption) this.subraceChoiceResult.getChosen().get(0)).getItem().getReference()) : null);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.player);
    }

    public HashMap<Trait, ChoiceResult> getSubtraitChoices() {
        return this.subtraitChoices;
    }

    public List<Proficiency> getCurrentProficiencies() {
        List<Proficiency> proficiencies = Lists.newArrayList();

        if (getRace() != null) {
            proficiencies.addAll(getRace().getStartingProficiencies());

            if (getRaceProfChoiceResult() != null)
                for (Option option : getRaceProfChoiceResult().getChosen())
                    proficiencies.add(new Proficiency(((SingleOption) option).getItem().getReference().getUrl()));
        }

        if (getSubrace() != null)
            proficiencies.addAll(getSubrace().getStartingProficiencies());

        if (getDNDClass() != null) {
            proficiencies.addAll(getDNDClass().getProficiencies());

            for (ChoiceResult result : this.classProficiencyChoiceResults)
                for (Option option : result.getChosen())
                    proficiencies.add(new Proficiency(((SingleOption) option).getItem().getReference().getUrl()));
        }

        if (getBackground() != null)
            proficiencies.addAll(getBackground().getProficiencies());

        return proficiencies;
    }

    public List<SingleOption> toOptionsList(List<Proficiency> proficiencies) {
        List<SingleOption> options = Lists.newArrayList();

        for (Proficiency proficiency : proficiencies)
            options.add(new SingleOption(Lists.newArrayList(), new CountedReference(1, proficiency.toReference())));

        return options;
    }

    // Static Methods
    public static String abilityScoreIncString(List<AbilityBonus> bonuses) {
        if (bonuses.size() == 6) {
            boolean allSame = true;
            long val = -1;

            for (AbilityBonus bonus : bonuses)
                if (val == -1)
                    val = bonus.getBonus();
                else if (val != bonus.getBonus())
                    allSame = false;


            if (allSame)
                return "Your ability scores each increase by " + val;
            else
                return "Your abilityyyyyy";
        } else if (bonuses.size() >= 1) {
            StringBuilder string = new StringBuilder();

            for (AbilityBonus bonus : bonuses)
                string.append(string.length() > 0 ? ", and " : "").append(string.length() == 0 ? "Your" : "your").append(" ").append(bonus.getAbilityScore().getFullName()).append(" score increases by ").append(bonus.getBonus());

            return string.toString();
        }

        return "None";
    }

    public static boolean hasWIPCharacter(Player player) {
        return CharacterCreator.charactersBeingCreated.containsKey(player.getUniqueId());
    }

    public static CharacterCreator getWIPCharacter(Player player) {
        CharacterCreator creator = CharacterCreator.charactersBeingCreated.get(player.getUniqueId());

        if (creator != null)
            return creator;
        else
            throw new NullPointerException(player.getName() + " does not have a work-in-progress Character!");
    }

    public ChoiceResult getBackgroundLanguageChoice() {
        return this.backgroundLanguageChoice;
    }
}
