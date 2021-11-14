package com.Theeef.me.interaction.character;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.chardata.Skill;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.AbilityBonus;
import com.Theeef.me.api.common.Info;
import com.Theeef.me.api.common.choice.*;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.api.monsters.Action;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.api.races.TraitSpecific;
import com.Theeef.me.api.spells.Spell;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CharacterCreator {

    public static HashMap<UUID, CharacterCreator> charactersBeingCreated = new HashMap<UUID, CharacterCreator>();

    private final UUID player;
    private ChoiceResult raceChoiceResult;
    private ChoiceResult subraceChoiceResult;
    private ChoiceResult classChoiceResult;
    private ChoiceResult raceProfChoiceResult;
    private ChoiceResult raceLanguageChoiceResult;
    private ChoiceResult subraceLanguageChoiceResult;
    private List<ChoiceResult> startingEquipmentChoiceResult;

    // TODO: Figure out a better way to store spellChoices
    private final HashMap<Trait, ChoiceResult> traitSpellChoices;
    private final HashMap<Trait, ChoiceResult> subtraitChoices;

    public CharacterCreator(Player player) {
        this.player = player.getUniqueId();
        this.subtraitChoices = new HashMap<>();
        this.traitSpellChoices = new HashMap<>();
        this.raceChoiceResult = new ChoiceResult(new Choice("race", 1, new ResourceListOptionSet("/api/races")));
        this.classChoiceResult = new ChoiceResult(new Choice("class", 1, new ResourceListOptionSet("/api/classes")));

        CharacterCreator.charactersBeingCreated.put(this.player, this);

        raceMenu();
    }

    public void goToPage(String page) {
        switch (page) {
            case "race":
                raceMenu();
                break;
            case "subrace":
                subraceMenu();
                break;
            case "racial traits":
                racialTraitMenu();
                break;
            case "subrace traits":
                subraceRacialTraitsMenu();
                break;
            case "class":
                classMenu();
                break;
            case "select race":
                selectRaceMenu();
                break;
            case "select subrace":
                selectSubraceMenu();
                break;
            case "select class":
                selectClassMenu();
                break;
            case "race proficiency choice":
                raceProficiencyOptionsMenu();
                break;
            case "race language choice":
                raceLanguageOptionsMenu();
                break;
            case "subrace language choice":
                subraceLanguageOptionsMenu();
                break;
            case "class levels":
                classLevelsMenu();
                break;
            case "starting equipment":
                startingEquipmentMenu();
                break;
            case "spellcasting":
                spellcastingMenu();
                break;
            default:
                throw new NullPointerException(page + " DIDNT KNOW HOW TO DIRECT");
        }
    }

    // Menu
    public void startingEquipmentMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, getDNDClass().getName() + " Starting Equipment");

        for (CountedReference equipment : getDNDClass().getStartingEquipment())
            inventory.addItem(equipment.getEquipment());

        for (ChoiceResult equipmentChoiceResult : this.startingEquipmentChoiceResult)
            inventory.addItem(equipmentChoiceResult.getItem(this));

        inventory.setItem(inventory.getSize() - 9, CharacterCreator.previousPage("class"));
        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equals(getDNDClass().getName() + " Starting Equipment"))
                    startingEquipmentMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void spellcastingMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, getDNDClass().getName() + " Spellcasting");
        ItemStack infoItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = infoItem.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Spellcasting");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + getDNDClass().getName() + " unlocks spellcasting at level " + getDNDClass().getSpellcasting().getLevel(), "", ChatColor.GRAY + "Spellcasting Ability: " + ChatColor.WHITE + getDNDClass().getSpellcasting().getSpellcastingAbility().getFullName()));
        infoItem.setItemMeta(meta);

        inventory.addItem(infoItem);

        for (Info info : getDNDClass().getSpellcasting().getInfo())
            inventory.addItem(CharacterCreator.infoItem(Material.MAP, info));

        inventory.setItem(inventory.getSize() - 9, CharacterCreator.previousPage("class"));

        getPlayer().openInventory(inventory);
    }

    public void classLevelsMenu() {

    }

    public void selectClassMenu() {
        ChoiceMenu menu = new ChoiceMenu("Select Your Class", "class", this.classChoiceResult);
        menu.open(getPlayer());
    }

    public void subtraitsMenu(Trait parentTrait, String returnTo) {
        ChoiceMenu menu = new ChoiceMenu(parentTrait.getName(), returnTo, this.subtraitChoices.get(parentTrait));
        menu.open(getPlayer());
    }

    public void subraceRacialTraitsMenu() {
        Inventory inventory = Bukkit.createInventory(null, 9 * ((getSubrace().getRacialTraits().size() - 1) / 9 + 3), "Subrace Traits");

        for (Trait trait : getSubrace().getRacialTraits())
            inventory.addItem(traitItem(trait));

        inventory.setItem(inventory.getSize() - 9, previousPage("subrace"));

        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equals("Subrace Traits"))
                    subraceRacialTraitsMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void subraceLanguageOptionsMenu() {
        ChoiceMenu menu = new ChoiceMenu("Choose " + getSubrace().getLanguageOptions().getChoiceAmount() + (getSubrace().getLanguageOptions().getChoiceAmount() > 1 ? " Languages" : " Language"), "subrace", this.subraceLanguageChoiceResult);
        menu.open(getPlayer());
    }

    public void raceLanguageOptionsMenu() {
        ChoiceMenu menu = new ChoiceMenu("Choose " + getRace().getLanguageOptions().getChoiceAmount() + (getRace().getLanguageOptions().getChoiceAmount() > 1 ? " Languages" : " Language"), "race", this.raceLanguageChoiceResult);
        menu.open(getPlayer());
    }

    public void selectSubraceMenu() {
        ChoiceMenu menu = new ChoiceMenu("Select Your Subrace", "subrace", this.subraceChoiceResult);
        menu.open(getPlayer());
    }

    public void subraceMenu() {
        Inventory inventory;

        if (getSubrace() == null)
            inventory = Bukkit.createInventory(null, 27, "Subrace");
        else
            inventory = Bukkit.createInventory(null, 5 * 9, "Subrace");

        inventory.setItem(13, selectSubraceItem());
        inventory.setItem(inventory.getSize() - 9, previousPage("race"));

        if (getSubrace() != null) {
            int align = (getSubrace().getRacialTraits().size() > 0 ? 1 : 0) + (getSubrace().getLanguageOptions() == null ? 0 : 1) + (getSubrace().getLanguages().size() == 0 ? 0 : 1) + (getSubrace().getStartingProficiencies().size() > 0 ? 1 : 0);
            int count = 0;

            if (getSubrace().getRacialTraits().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, subraceTraitItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), subraceTraitItem());
                count++;
            }

            if (getSubrace().getStartingProficiencies().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, startingProficienciesItem(getSubrace().getStartingProficiencies()));
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), startingProficienciesItem(getSubrace().getStartingProficiencies()));
                count++;
            }

            if (getSubrace().getLanguages().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, languagesItem(getSubrace().getLanguages()));
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), languagesItem(getSubrace().getLanguages()));
                count++;
            }

            if (getSubrace().getLanguageOptions() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, subraceLanguageChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), subraceLanguageChoiceItem());
                count++;
            }
        }

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equals("Subrace"))
                    subraceMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);

        getPlayer().openInventory(inventory);
    }

    public void raceProficiencyOptionsMenu() {
        ChoiceMenu menu = new ChoiceMenu("Choose " + getRace().getStartingProficiencyOptions().getChoiceAmount() + (getRace().getStartingProficiencyOptions().getChoiceAmount() > 1 ? " Proficiencies" : " Proficiency"), "race", this.raceProfChoiceResult);
        menu.open(getPlayer());
    }

    public void racialTraitMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, "Racial Traits");

        for (Trait trait : getRace().getTraits())
            inventory.addItem(traitItem(trait));

        inventory.setItem(inventory.getSize() - 9, previousPage("race"));

        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equals("Racial Traits"))
                    racialTraitMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void classMenu() {
        Inventory inventory;

        if (getDNDClass() == null)
            inventory = Bukkit.createInventory(null, 27, "Class");
        else {
            inventory = Bukkit.createInventory(null, 9 * 5, "Class: " + getDNDClass().getName());

            int align = (((getDNDClass().getStartingEquipment().size() > 0 || getDNDClass().getStartingEquipmentOptions() != null) ? 1 : 0) + 1 + (getDNDClass().getSpellcasting() != null ? 1 : 0));
            int count = 0;

            if (getDNDClass().getStartingEquipment().size() > 0 || getDNDClass().getStartingEquipment() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, startingEquipmentItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), startingEquipmentItem());
                count++;
            }

            if (getDNDClass().getSpellcasting() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, spellcastingItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), spellcastingItem());
                count++;
            }

            // Leveling
            if (align % 2 != 0)
                inventory.setItem(27 + (9 - align) / 2 + count, levelingItem());
            else
                inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), levelingItem());
            count++;

        }

        inventory.setItem(13, selectClassItem());
        inventory.setItem(inventory.getSize() - 9, previousPage("race"));

        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equals("Class") || getDNDClass() != null && getPlayer().getOpenInventory().getTitle().equals("Class: " + getDNDClass().getName()))
                    classMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void raceMenu() {
        Inventory inventory;

        if (getRace() == null)
            inventory = Bukkit.createInventory(null, 27, "Race");
        else
            inventory = Bukkit.createInventory(null, 9 * 5, "Race: " + getRace().getName());

        inventory.setItem(13, selectRaceItem());
        inventory.setItem(inventory.getSize() - 1, nextPage("class"));

        if (getRace() != null) {
            int align = (getRace().getStartingProficiencies().size() > 0 ? 1 : 0) + (getRace().getTraits().size() > 0 ? 1 : 0) + (getRace().getLanguageOptions() == null ? 0 : 1) + (getRace().getStartingProficiencyOptions() == null ? 0 : 1) + (getRace().getSubraces().size() > 0 ? 1 : 0);
            int count = 0;

            if (getRace().getTraits().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, raceTraitItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), raceTraitItem());
                count++;
            }

            if (getRace().getStartingProficiencies().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, startingProficienciesItem(getRace().getStartingProficiencies()));
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), startingProficienciesItem(getRace().getStartingProficiencies()));
                count++;
            }

            if (getRace().getStartingProficiencyOptions() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, startingProfChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), startingProfChoiceItem());
                count++;
            }

            if (getRace().getLanguageOptions() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, raceLanguageChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), raceLanguageChoiceItem());
                count++;
            }

            if (getRace().getSubraces().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, subracesItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), subracesItem());
                count++;
            }
        }

        getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equals("Race") || getRace() != null && getPlayer().getOpenInventory().getTitle().equals("Race: " + getRace().getName()))
                    raceMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void selectRaceMenu() {
        ChoiceMenu menu = new ChoiceMenu("Select Your Race", "race", this.raceChoiceResult);
        menu.open(getPlayer());
    }

    // Object based Item

    public ItemStack spellcastingItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Spellcasting");
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "This class gains the ability to cast spells at level " + getDNDClass().getSpellcasting().getLevel(), "", ChatColor.GRAY + "Spellcasting Ability: " + ChatColor.WHITE + getDNDClass().getSpellcasting().getSpellcastingAbility().getFullName(), "", ChatColor.WHITE + "Click for more info");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "spellcasting");
    }

    public ItemStack startingEquipmentItem() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Equipment");

        lore.add(ChatColor.GRAY + "Equipment: ");

        for (CountedReference reference : getDNDClass().getStartingEquipment())
            lore.add(ChatColor.WHITE + "- " + reference.getReference().getName() + (reference.getCount() > 1 ? " x" + reference.getCount() : ""));

        if (lore.size() > 1)
            lore.add(ChatColor.WHITE + "- and " + getDNDClass().getStartingEquipmentOptions().size() + " other choices...");
        else
            lore.add(ChatColor.WHITE + "- " + getDNDClass().getStartingEquipmentOptions().size() + " choices");

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to choose your starting equipment");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "starting equipment");

        return new ChoiceMenuItem(item, this.startingEquipmentChoiceResult).getItem();
    }

    public ItemStack levelingItem() {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Class Levels");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click for more information"));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "class levels");
    }

    public ItemStack traitSpellItem(ChoiceResult result, SingleOption spellOption) {
        Spell spell = Spell.fromIndex(spellOption.getItem().getReference().getIndex());
        ItemStack item = new ItemStack(result.alreadyChosen(spellOption) ? Material.ENCHANTED_BOOK : Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.RESET + spellOption.getItem().getReference().getName());

        for (String desc : spell.getDescription()) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));
            lore.add("");
        }

        if (result.alreadyChosen(spellOption))
            lore.add(ChatColor.WHITE + "Click to unchoose this spell");
        else
            lore.add(ChatColor.WHITE + "Click to choose this spell");

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack selectClassItem() {
        if (getDNDClass() != null) {
            ItemStack item = classItem(getDNDClass());
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            assert lore != null;
            lore.remove(lore.size() - 1);
            lore.add(ChatColor.WHITE + "Click to switch classes");
            meta.setLore(lore);
            item.setItemMeta(meta);

            NBTHandler.addString(item, "goesTo", "select class");

            return item;
        }

        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Select Class");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to select your character's starting class"));
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "select class");

        return new ChoiceMenuItem(item, this.classChoiceResult).getItem();
    }

    public ItemStack subracesItem() {
        ItemStack item = new ItemStack(Material.TURTLE_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (getSubrace() == null) {
            meta.setDisplayName(ChatColor.AQUA + "Subraces");
            lore.add(ChatColor.GRAY + "Subrace Options:");

            for (Subrace subrace : getRace().getSubraces())
                lore.add(ChatColor.WHITE + "- " + subrace.getName());

            lore.add("");
            lore.add(ChatColor.WHITE + "Click to select your subrace");
        } else {
            meta.setDisplayName(ChatColor.AQUA + "Subrace: " + getSubrace().getName());
            lore.addAll(Util.fitForLore(ChatColor.GRAY + abilityScoreIncString(getSubrace().getAbilityBonuses())));
            lore.add("");
            lore.addAll(Util.fitForLore(ChatColor.GRAY + getSubrace().getDescription()));
            lore.add("");
            lore.add(ChatColor.WHITE + "Click for more info");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "subrace");

        return new ChoiceMenuItem(item, this.subraceChoiceResult).getItem();
    }

    public ItemStack startingProfChoiceItem() {
        ItemStack item = new ItemStack(Material.TARGET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Starting Proficiency Options");
        List<String> lore = new ArrayList<>();

        if (!this.raceProfChoiceResult.isComplete()) {
            lore.add(ChatColor.GRAY + "Select " + this.raceProfChoiceResult.getChoice().getChoiceAmount() + (this.raceProfChoiceResult.getChoice().getChoiceAmount() > 1 ? " proficiencies" : " proficiency") + " from the following:");

            List<Option> options = new ArrayList<>();

            for (Option option : getRace().getStartingProficiencyOptions().getOptions())
                options.add((SingleOption) option);

            for (Option proficiency : options)
                lore.add(ChatColor.WHITE + "- " + proficiency.getDescription());

            lore.add("");
        }

        if (this.raceProfChoiceResult.getChosen().size() > 0) {
            lore.add(ChatColor.GRAY + "Selected Proficiencies:");
            for (Option proficiency : this.raceProfChoiceResult.getChosen())
                lore.add(ChatColor.WHITE + "- " + proficiency.getDescription());

            lore.add("");

            if (this.raceProfChoiceResult.isComplete())
                lore.add(ChatColor.WHITE + "Click to edit your starting proficiencies");
            else
                lore.add(ChatColor.WHITE + "Click to finish selecting your starting proficiencies");
        } else
            lore.add(ChatColor.WHITE + "Click to select your starting proficiencies");

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "race proficiency choice");

        return new ChoiceMenuItem(item, this.raceProfChoiceResult).getItem();
    }

    public ItemStack selectSubraceItem() {
        if (getSubrace() != null) {
            ItemStack item = subraceItem(getSubrace());
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            assert lore != null;
            lore.remove(lore.size() - 1);
            lore.add(ChatColor.WHITE + "Click to switch subraces");
            meta.setLore(lore);
            item.setItemMeta(meta);

            return NBTHandler.addString(item, "goesTo", "select subrace");
        }

        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Select Subrace");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to select your character's subrace"));
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "select subrace");

        return new ChoiceMenuItem(item, this.subraceChoiceResult).getItem();
    }

    public ItemStack selectRaceItem() {
        if (getRace() != null) {
            ItemStack item = raceItem(getRace());
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            assert lore != null;
            lore.remove(lore.size() - 1);
            lore.add(ChatColor.WHITE + "Click to switch races");
            meta.setLore(lore);
            item.setItemMeta(meta);

            return NBTHandler.addString(item, "goesTo", "select race");
        }

        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Select Race");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to select your character's race"));
        item.setItemMeta(meta);
        NBTHandler.addString(item, "goesTo", "select race");

        return new ChoiceMenuItem(item, this.raceChoiceResult).getItem();
    }

    public ItemStack traitItem(Trait trait) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + trait.getName());
        List<String> lore = new ArrayList<>();

        for (String desc : trait.getDescription()) {
            if (!trait.getDescription().get(0).equals(desc))
                lore.add("");

            lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));
        }

        if (trait.getTraitSpecific() != null) {
            TraitSpecific specific = trait.getTraitSpecific();

            if (specific.getDamageType() != null) {
                lore.add("");
                lore.add(ChatColor.GRAY + "Damage Type: " + ChatColor.WHITE + specific.getDamageType().getName());
            }

            if (specific.getBreathWeapon() != null) {
                Action breathWeapon = specific.getBreathWeapon();
                lore.add("");
                lore.addAll(Util.fitForLore(ChatColor.GRAY + "Breath Weapon: " + ChatColor.WHITE + breathWeapon.getDamage().get(0).getDamageAtCharacterLevel(1).getMin() + "-" + breathWeapon.getDamage().get(0).getDamageAtCharacterLevel(1).getMax() + " " + breathWeapon.getDamage().get(0).getType().getName() + " damage or half as much on a successful DC 8 + CON MOD + proficiency bonus " + breathWeapon.getDC().getDCType().getName() + " save."));
            }

            if (specific.getSubtraitOptions() != null) {
                ChoiceResult choiceResult = this.subtraitChoices.get(trait);

                if (choiceResult.getChosen().size() == 1) {
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + choiceResult.getChosen().get(0).getDescription());
                } else if (choiceResult.getChosen().size() > 1) {
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Selected:");

                    for (Option subtrait : choiceResult.getChosen())
                        lore.add(ChatColor.WHITE + "- " + subtrait.getDescription());
                }

                lore.add("");

                if (choiceResult.getChosen().size() < choiceResult.getChoice().getChoiceAmount())
                    lore.add(ChatColor.WHITE + "Click to select subtraits");
                else
                    lore.add(ChatColor.WHITE + "Click to change your subtrait selections");
            }

            if (specific.getSpellOptions() != null) {
                ChoiceResult choiceResult = this.traitSpellChoices.get(trait);

                if (choiceResult.getChosen().size() == 1) {
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + choiceResult.getChosen().get(0).getDescription());
                } else if (choiceResult.getChosen().size() > 1) {
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Selected:");

                    for (Option spell : choiceResult.getChosen())
                        lore.add(ChatColor.WHITE + "- " + spell.getDescription());
                }

                lore.add("");

                if (choiceResult.getChosen().size() < choiceResult.getChoice().getChoiceAmount())
                    lore.add(ChatColor.WHITE + "Click to select your spells");
                else
                    lore.add(ChatColor.WHITE + "Click to change your spell selections");
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "trait", trait.getUrl());

        if (trait.getTraitSpecific() != null) {
            List<ChoiceResult> results = new ArrayList<>();

            if (trait.getTraitSpecific().getSubtraitOptions() != null)
                results.addAll(this.subtraitChoices.values());

            if (trait.getTraitSpecific().getSpellOptions() != null)
                results.addAll(this.traitSpellChoices.values());

            if (results.size() > 0)
                return new ChoiceMenuItem(item, results).getItem();
        }

        return item;
    }

    public ItemStack subtraitItem(SingleOption subtraitOption) {
        Trait subtrait = new Trait(subtraitOption.getItem().getReference().getUrl());
        ItemStack item = traitItem(subtrait);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        ChoiceResult result = subtrait.getParent() == null ? null : this.subtraitChoices.get(subtrait.getParent());
        lore.add("");

        if (result != null) {
            if (result.alreadyChosen(subtraitOption)) {
                item.setType(Material.LIME_STAINED_GLASS_PANE);
                lore.add(ChatColor.WHITE + "Click to unchoose this subtrait");
            } else {
                lore.add(ChatColor.WHITE + "Click to choose this subtrait");
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack subraceTraitItem() {
        ItemStack item = new ItemStack(Material.CREEPER_BANNER_PATTERN, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(ChatColor.AQUA + "Racial Traits");
        List<String> lore = new ArrayList<>();

        for (int i = 0; i < getSubrace().getRacialTraits().size(); i++) {
            Trait trait = getSubrace().getRacialTraits().get(i);

            if (i != 0)
                lore.add("");

            lore.add(ChatColor.WHITE + trait.getName());

            for (String desc : trait.getDescription()) {
                if (!trait.getDescription().get(0).equals(desc))
                    lore.add("");

                lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));
            }
        }

        lore.add("");
        lore.add(ChatColor.WHITE + "Click for more info");

        meta.setLore(lore);
        item.setItemMeta(meta);

        // TODO: Separate subtrait choices from races and subraces

        NBTHandler.addString(item, "goesTo", "subrace traits");

        return new ChoiceMenuItem(item, new ArrayList<>(this.traitSpellChoices.values())).getItem();
    }

    public ItemStack raceTraitItem() {
        ItemStack item = new ItemStack(Material.CREEPER_BANNER_PATTERN, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(ChatColor.AQUA + "Racial Traits");
        List<String> lore = new ArrayList<>();

        for (int i = 0; i < getRace().getTraits().size(); i++) {
            Trait trait = getRace().getTraits().get(i);

            if (i != 0)
                lore.add("");

            lore.add(ChatColor.WHITE + trait.getName());

            for (String desc : trait.getDescription()) {
                if (!trait.getDescription().get(0).equals(desc))
                    lore.add("");

                lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));
            }
        }

        lore.add("");
        lore.add(ChatColor.WHITE + "Click for more info");

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "racial traits");

        return new ChoiceMenuItem(item, new ArrayList<>(this.subtraitChoices.values())).getItem();
    }

    public ItemStack subraceLanguageChoiceItem() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Starting Language Options");
        List<String> lore = new ArrayList<>();

        if (!this.subraceLanguageChoiceResult.isComplete()) {
            lore.add(ChatColor.GRAY + "Select " + this.subraceLanguageChoiceResult.getChoice().getChoiceAmount() + (this.subraceLanguageChoiceResult.getChoice().getChoiceAmount() > 1 ? " languages" : " language") + " from the following:");

            for (Option option : getSubrace().getLanguageOptions().getOptions())
                lore.add(ChatColor.WHITE + "- " + ((SingleOption) option).getItem().getReference().getName());

            lore.add("");
        }

        if (this.subraceLanguageChoiceResult.getChosen().size() > 0) {
            lore.add(ChatColor.GRAY + "Selected Languages:");
            for (Option language : this.subraceLanguageChoiceResult.getChosen())
                lore.add(ChatColor.WHITE + "- " + language.getDescription());

            lore.add("");

            if (this.subraceLanguageChoiceResult.isComplete())
                lore.add(ChatColor.WHITE + "Click to edit your starting languages");
            else
                lore.add(ChatColor.WHITE + "Click to finish selecting your starting languages");
        } else
            lore.add(ChatColor.WHITE + "Click to select your starting languages");

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "subrace language choice");

        return new ChoiceMenuItem(item, this.subraceLanguageChoiceResult).getItem();
    }

    public ItemStack raceLanguageChoiceItem() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Starting Language Options");
        List<String> lore = new ArrayList<>();

        if (!this.raceLanguageChoiceResult.isComplete()) {
            lore.add(ChatColor.GRAY + "Select " + this.raceLanguageChoiceResult.getChoice().getChoiceAmount() + (this.raceLanguageChoiceResult.getChoice().getChoiceAmount() > 1 ? " languages" : " language") + " from the following:");

            for (Option option : getRace().getLanguageOptions().getOptions())
                lore.add(ChatColor.WHITE + "- " + ((SingleOption) option).getItem().getReference().getName());

            lore.add("");
        }

        if (this.raceLanguageChoiceResult.getChosen().size() > 0) {
            lore.add(ChatColor.GRAY + "Selected Languages:");
            for (Option language : this.raceLanguageChoiceResult.getChosen())
                lore.add(ChatColor.WHITE + "- " + language.getDescription());

            lore.add("");

            if (this.raceLanguageChoiceResult.isComplete())
                lore.add(ChatColor.WHITE + "Click to edit your starting languages");
            else
                lore.add(ChatColor.WHITE + "Click to finish selecting your starting languages");
        } else
            lore.add(ChatColor.WHITE + "Click to select your starting languages");

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "race language choice");

        return new ChoiceMenuItem(item, this.raceLanguageChoiceResult).getItem();
    }

    // Helper Methods
    private boolean playerOnline() {
        return Bukkit.getOfflinePlayer(this.player).isOnline();
    }

    // Setter Methods
    public void setClass(SingleOption classOption) {
        DNDClass dndclass = DNDClass.fromIndex((classOption).getItem().getReference().getIndex());

        if (getDNDClass() == null || !getDNDClass().equals(dndclass)) {
            this.startingEquipmentChoiceResult = new ArrayList<>();

            for (Choice choice : dndclass.getStartingEquipmentOptions())
                this.startingEquipmentChoiceResult.add(new ChoiceResult(choice));

            if (this.classChoiceResult.isComplete())
                this.classChoiceResult.clearChoices();
        }

        this.classChoiceResult.choose(classOption);
    }

    public void setSubrace(SingleOption subraceOption) {
        Subrace subrace = subraceOption == null ? null : Subrace.fromIndex((subraceOption).getItem().getReference().getIndex());

        if (subrace == null || (getSubrace() != null && !subrace.equals(subrace))) {
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

            if (getSubrace().getLanguageOptions() != null)
                this.subraceLanguageChoiceResult = new ChoiceResult(getSubrace().getLanguageOptions());
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
        }

        if (race != null)
            this.subraceChoiceResult = race.getSubraces().size() > 0 ? new ChoiceResult(new Choice("subrace", 1, new ResourceListOptionSet(race.getUrl() + "/subraces"))) : null;

        this.raceChoiceResult.choose(raceOption);

        for (Trait trait : getRace().getTraits())
            if (trait.getTraitSpecific() != null && trait.getTraitSpecific().getSubtraitOptions() != null)
                this.subtraitChoices.put(trait, new ChoiceResult(trait.getTraitSpecific().getSubtraitOptions()));

        if (getRace().getStartingProficiencyOptions() != null)
            this.raceProfChoiceResult = new ChoiceResult(getRace().getStartingProficiencyOptions());

        if (getRace().getLanguageOptions() != null)
            this.raceLanguageChoiceResult = new ChoiceResult(getRace().getLanguageOptions());
    }

    // Getter methods
    public List<ChoiceResult> getStartingEquipmentChoiceResult() {
        return this.startingEquipmentChoiceResult;
    }

    public DNDClass getDNDClass() {
        return this.classChoiceResult.isComplete() ? new DNDClass(((SingleOption) this.classChoiceResult.getChosen().get(0)).getItem().getReference()) : null;
    }

    public HashMap<Trait, ChoiceResult> getTraitSpellChoices() {
        return this.traitSpellChoices;
    }

    public ChoiceResult getRaceChoiceResult() {
        return this.raceChoiceResult;
    }

    public ChoiceResult getSubraceChoiceResult() {
        return this.subraceChoiceResult;
    }

    public ChoiceResult getClassChoiceResult() {
        return this.classChoiceResult;
    }

    public ChoiceResult getRaceProfChoiceResult() {
        return this.raceProfChoiceResult;
    }

    public ChoiceResult getSubraceLanguageChoiceResult() {
        return this.subraceLanguageChoiceResult;
    }

    public ChoiceResult getRaceLanguageChoiceResult() {
        return this.raceLanguageChoiceResult;
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

    // Static Item Methods
    public static ItemStack infoItem(Material material, Info info) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + info.getName());
        List<String> lore = new ArrayList<>();

        for (String infoDesc : info.getDescription()) {
            if (!info.getDescription().get(0).equals(infoDesc))
                lore.add("");

            lore.addAll(Util.fitForLore(ChatColor.GRAY + infoDesc));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack languageItem(Language language, boolean isAlreadyLearned) {
        ItemStack item = new ItemStack(isAlreadyLearned ? Material.BOOK : Material.MAP, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.WHITE + language.getName());

        lore.add(ChatColor.GRAY + "Script: " + (language.getScript() == null ? (ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "forsaken") : ChatColor.WHITE + language.getScript()));
        lore.add(ChatColor.GRAY + "Typical Speakers:");

        for (String typicalSpeaker : language.getTypicalSpeakers())
            lore.add(ChatColor.WHITE + "- " + typicalSpeaker);

        lore.add("");
        lore.add(ChatColor.WHITE + (isAlreadyLearned ? "Click to unlearn this language" : "Click to learn this language"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack racialTraitChoiceItem(Choice choice) {
        ItemStack item = new ItemStack(Material.BOOKSHELF, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();

        meta.setDisplayName(ChatColor.AQUA + "Racial Trait Options");
        lore.add(ChatColor.GRAY + "Select " + choice.getChoiceAmount() + " racial " + (choice.getChoiceAmount() == 1 ? "trait" : "traits") + " from the following:");

        for (Option option : choice.getOptions())
            lore.add(ChatColor.WHITE + "- " + ((SingleOption) option).getItem().getReference().getName());

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select your racial traits");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack languagesItem(List<Language> languages) {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Starting Languages");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "You can speak, read, and write the following languages:");

        for (Language language : languages)
            lore.add(ChatColor.WHITE + "- " + language.getName());

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack proficiencyItem(Proficiency proficiency, boolean isProficientAlready) {
        ItemStack item = new ItemStack(isProficientAlready ? Material.LIME_STAINED_GLASS_PANE : Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.AQUA + proficiency.getName());

        if (proficiency.getType().equals("Skills")) {
            Skill skill = new Skill(proficiency.getReferences().get(0).getUrl());

            for (String desc : skill.getDescription()) {
                if (!skill.getDescription().get(0).equals(desc))
                    lore.add("");

                lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));

            }
        }
        lore.add("");

        if (isProficientAlready)
            lore.add(ChatColor.WHITE + "Click to remove proficiency in this");
        else
            lore.add(ChatColor.WHITE + "Click to become proficient in this");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack startingProficienciesItem(List<Proficiency> proficiencies) {
        ItemStack item = new ItemStack(Material.FLETCHING_TABLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Starting Proficiencies");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "You are proficient with the following:");

        for (Proficiency proficiency : proficiencies)
            lore.add(ChatColor.WHITE + "- " + proficiency.getName());

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack subraceItem(Subrace subrace) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Ability Score Increase: " + ChatColor.WHITE + abilityScoreIncString(subrace.getAbilityBonuses())));
        lore.add("");
        lore.addAll(Util.fitForLore(ChatColor.GRAY + subrace.getDescription()));
        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select this subrace");
        meta.setDisplayName(ChatColor.GOLD + subrace.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "subrace", subrace.getUrl());

        return NBTHandler.addString(item, "goesTo", "subrace");
    }

    public static ItemStack nextPage(String goesTo) {
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "Continue");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Continue to the " + goesTo + " page"));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", goesTo);
    }

    public static ItemStack previousPage(String goesTo) {
        ItemStack item = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "Go Back");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Return to the " + goesTo + " page"));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", goesTo);
    }

    public static ItemStack classItem(DNDClass dndclass) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        StringBuilder proficiencyString = new StringBuilder();

        for (Proficiency prof : dndclass.getProficiencies())
            proficiencyString.append(prof.getName() + ", ");

        proficiencyString.delete(proficiencyString.length() - 2, proficiencyString.length());

        StringBuilder savingThrowString = new StringBuilder();

        for (AbilityScore score : dndclass.getSavingThrows())
            savingThrowString.append(score.getFullName() + ", ");
        savingThrowString.delete(savingThrowString.length() - 2, savingThrowString.length());


        lore.add(ChatColor.GRAY + "Hit Die: " + ChatColor.WHITE + "d" + dndclass.getHitDie());
        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Proficiencies: " + ChatColor.WHITE + proficiencyString.toString()));
        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Saving Throws: " + ChatColor.WHITE + savingThrowString.toString()));

        if (dndclass.getSubclasses().size() > 0) {
            StringBuilder subclassesString = new StringBuilder();

            for (Subclass subclass : dndclass.getSubclasses())
                subclassesString.append(subclass.getName() + ", ");

            subclassesString.delete(subclassesString.length() - 2, subclassesString.length() + 1);

            lore.addAll(Util.fitForLore(ChatColor.GRAY + "Subclasses: " + ChatColor.WHITE + subclassesString));
        }
        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select this class");
        meta.setDisplayName(ChatColor.GOLD + dndclass.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "class", dndclass.getUrl());

        return NBTHandler.addString(item, "goesTo", "class");
    }

    public static ItemStack raceItem(Race race) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Ability Score Increase: " + ChatColor.WHITE + abilityScoreIncString(race.getAbilityBonuses())));
        lore.add(ChatColor.GRAY + "Speed: " + ChatColor.WHITE + race.getSpeed());
        lore.add("");
        lore.addAll(Util.fitForLore(ChatColor.GRAY + race.getAgeDescription()));
        lore.add("");
        lore.addAll(Util.fitForLore(ChatColor.GRAY + race.getAlignmentDescription()));
        lore.add("");
        lore.addAll(Util.fitForLore(ChatColor.GRAY + race.getSizeDescription()));
        lore.add("");
        lore.addAll(Util.fitForLore(ChatColor.GRAY + race.getLanguageDescription()));
        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select this race");
        meta.setDisplayName(ChatColor.GOLD + race.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "race", race.getUrl());

        return NBTHandler.addString(item, "goesTo", "race");
    }

    // Static Methods
    private static String abilityScoreIncString(List<AbilityBonus> bonuses) {
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
                string.append(string.length() > 0 ? ", and " : "").append(string.length() == 0 ? "Your" : "your").append(" " + bonus.getAbilityScore().getFullName() + " score increases by " + bonus.getBonus());

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
}
