package com.Theeef.me.interaction.character;

import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.AbilityBonus;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CharacterCreator {

    public static HashMap<UUID, CharacterCreator> charactersBeingCreated = new HashMap<UUID, CharacterCreator>();

    private final UUID player;
    private Race race;
    private Subrace subrace;
    private DNDClass dndclass;

    public CharacterCreator(Player player) {
        this.player = player.getUniqueId();
        CharacterCreator.charactersBeingCreated.put(this.player, this);

        raceMenu();
    }

    public void racialTraitMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, "Racial Traits");

        for (Trait trait : this.race.getTraits())
            inventory.addItem(traitItem(trait));

        inventory.setItem(inventory.getSize() - 9, previousPage(ChatColor.GRAY + "Return to the race menu"));

        getPlayer().openInventory(inventory);
    }

    public static ItemStack traitItem(Trait trait) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + trait.getName());
        List<String> lore = new ArrayList<>();

        for (String desc : trait.getDescription())
            lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public void classMenu() {
        Inventory inventory;

        if (this.dndclass == null)
            inventory = Bukkit.createInventory(null, 27, "Class");
        else
            inventory = Bukkit.createInventory(null, 9 * 5, "Class");

        inventory.setItem(13, selectClassItem());
        inventory.setItem(inventory.getSize() - 9, previousPage(ChatColor.GRAY + "Return to the race page"));

        getPlayer().openInventory(inventory);
    }

    public ItemStack selectClassItem() {
        if (this.dndclass != null) {
            ItemStack item = classItem(this.dndclass);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            assert lore != null;
            lore.remove(lore.size() - 1);
            lore.add(ChatColor.WHITE + "Left Click to switch classes");
            meta.setLore(lore);
            item.setItemMeta(meta);

            return item;
        }

        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "Select Class");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to select your character's starting class"));
        item.setItemMeta(meta);

        return item;
    }

    public void raceMenu() {
        Inventory inventory;

        if (race == null)
            inventory = Bukkit.createInventory(null, 27, "Race");
        else
            inventory = Bukkit.createInventory(null, 9 * 5, "Race");

        inventory.setItem(13, selectRaceItem());
        inventory.setItem(inventory.getSize() - 1, nextPage(ChatColor.GRAY + "Continue to class selection"));

        if (this.race != null) {
            int align = (this.race.getTraits().size() > 0 ? 1 : 0) + (this.race.getLanguageOptions() == null ? 0 : 1) + (this.race.getStartingProficiencyOptions() == null ? 0 : 1) + (this.race.getSubraces().size() > 0 ? 1 : 0);
            int count = 0;

            if (this.race.getTraits().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, raceTraitItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), raceTraitItem());
                count++;
            }

            if (this.race.getStartingProficiencyOptions() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, startingProfChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), startingProfChoiceItem());
                count++;
            }

            if (this.race.getLanguageOptions() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, languageChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), languageChoiceItem());
                count++;
            }

            if (this.race.getSubraces().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, subracesItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), subracesItem());
                count++;
            }
        }

        getPlayer().openInventory(inventory);
    }

    public ItemStack subracesItem() {
        // TODO: CHANGE WHEN SUBRACE EXISTS
        ItemStack item;

        if (this.subrace == null) {
            item = new ItemStack(Material.TURTLE_EGG, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + "Subraces");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Subrace Options:");

            for (Subrace subrace : this.race.getSubraces())
                lore.add(ChatColor.WHITE + "- " + subrace.getName());

            lore.add("");
            lore.add(ChatColor.WHITE + "Click to select your subrace");

            meta.setLore(lore);
            item.setItemMeta(meta);
        } else
            return null;

        return item;
    }

    public ItemStack languageChoiceItem() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Starting Language Options");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Select " + this.race.getLanguageOptions().getChoiceAmount() + (this.race.getLanguageOptions().getChoiceAmount() > 1 ? " languages" : " language") + " from the following:");

        for (APIReference reference : this.race.getLanguageOptions().getChoices())
            lore.add(ChatColor.WHITE + "- " + reference.getName());

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select your starting languages");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack startingProfChoiceItem() {
        ItemStack item = new ItemStack(Material.TARGET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Starting Proficiency Options");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Select " + this.race.getStartingProficiencyOptions().getChoiceAmount() + (this.race.getStartingProficiencyOptions().getChoiceAmount() > 1 ? " proficiencies" : " proficiency") + " from the following:");

        for (APIReference reference : this.race.getStartingProficiencyOptions().getChoices())
            lore.add(ChatColor.WHITE + "- " + reference.getName());

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select your starting proficiencies");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack selectRaceItem() {
        if (race != null) {
            ItemStack item = raceItem(race);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            assert lore != null;
            lore.remove(lore.size() - 1);
            lore.add(ChatColor.WHITE + "Left Click to switch races");
            meta.setLore(lore);
            item.setItemMeta(meta);

            return item;
        }

        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "Select Race");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to select your character's race"));
        item.setItemMeta(meta);

        return item;
    }

    public void menuPickRace(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Select your race");

        for (Race race : Race.values())
            inventory.addItem(raceItem(race));

        inventory.setItem(inventory.getSize() - 9, previousPage(ChatColor.GRAY + "Return to the race page"));

        player.openInventory(inventory);
    }

    public ItemStack raceTraitItem() {
        ItemStack item = new ItemStack(Material.CREEPER_BANNER_PATTERN, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(ChatColor.AQUA + "Racial Traits");
        List<String> lore = new ArrayList<>();

        for (int i = 0; i < this.race.getTraits().size(); i++) {
            if (i != 0)
                lore.add("");

            lore.add(ChatColor.WHITE + this.race.getTraits().get(i).getName());

            for (String desc : this.race.getTraits().get(i).getDescription())
                lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));
        }

        lore.add("");
        lore.add(ChatColor.WHITE + "Click for more info");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    private boolean playerOnline() {
        return Bukkit.getOfflinePlayer(this.player).isOnline();
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(this.player);
    }

    // Static Methods
    private static ItemStack classItem(DNDClass dndclass) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.add(dndclass.getName());
        lore.add(ChatColor.WHITE + "Left Click to select this class");
        meta.setDisplayName(dndclass.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack raceItem(Race race) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Ability Score Increase: " + ChatColor.WHITE + abilityScoreIncString(race)));
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
        lore.add(ChatColor.WHITE + "Left Click to select this race");
        meta.setDisplayName(ChatColor.GOLD + race.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "race", race.getUrl());
    }

    private static String abilityScoreIncString(Race race) {
        List<AbilityBonus> bonuses = race.getAbilityBonuses();

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
        if (CharacterCreator.hasWIPCharacter(player))
            return CharacterCreator.charactersBeingCreated.get(player.getUniqueId());
        else
            return null;
    }

    public static ItemStack nextPage(String description) {
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Next Page");

        if (description != null)
            meta.setLore(Lists.newArrayList(description));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack previousPage(String description) {
        ItemStack item = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Go Back");

        if (description != null)
            meta.setLore(Lists.newArrayList(description));

        item.setItemMeta(meta);

        return item;
    }
}
