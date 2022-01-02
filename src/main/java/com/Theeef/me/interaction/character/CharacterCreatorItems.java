package com.Theeef.me.interaction.character;

import com.Theeef.me.api.backgrounds.Background;
import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.chardata.Skill;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.SpellcastingLevel;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.Info;
import com.Theeef.me.api.common.choice.ChoiceResult;
import com.Theeef.me.api.common.choice.CountedReference;
import com.Theeef.me.api.common.choice.Option;
import com.Theeef.me.api.common.choice.SingleOption;
import com.Theeef.me.api.monsters.Action;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.api.races.TraitSpecific;
import com.Theeef.me.api.spells.Spell;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CharacterCreatorItems {

    private final CharacterCreator creator;

    public CharacterCreatorItems(CharacterCreator creator) {
        this.creator = creator;
    }

    public static ItemStack spellSlotsAtLevelItem(SpellcastingLevel spellcasting, int slotLevel, int classLevel) {
        ItemStack item = new ItemStack(Material.PURPLE_DYE, (int) spellcasting.getSpellSlotsAtLevel(slotLevel));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + CharacterCreator.nthLevelString(slotLevel) + " Level Spell");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "This class has " + spellcasting.getSpellSlotsAtLevel(slotLevel) + " " + CharacterCreator.nthLevelString(slotLevel) + " level spell slots at level " + classLevel));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack spellInfoSign(int startingRow) {
        ItemStack item = new ItemStack(Material.SPRUCE_SIGN);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Viewing spell slots at levels " + startingRow + " through " + (startingRow + 3));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "row", Integer.toString(startingRow));
    }

    public static ItemStack borderItem() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack spellSlotColumn(int slotLevel) {
        ItemStack item = new ItemStack(Material.END_PORTAL_FRAME, slotLevel);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + CharacterCreator.nthLevelString(slotLevel) + " Level Spell Slot");
        item.setItemMeta(meta);

        return item;
    }

    ItemStack levelingFeaturesItem() {
        ItemStack item = new ItemStack(Material.ARMOR_STAND, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Class Features");
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.GRAY + "Click to view the features for this class");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "class features"), creator.getClassSubfeatureChoices()).getItem();
    }

    ItemStack languagesItem(List<Language> languages) {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Languages");
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.GRAY + "You can speak, read, and write the following languages:");

        for (Language language : languages)
            lore.add(ChatColor.WHITE + "- " + language.getName());

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(item, creator.getBackgroundLanguageChoice()).getItem();
    }

    public ItemStack levelingItem() {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Class Levels");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click for more information"));
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "class levels"), creator.getClassSubfeatureChoices()).getItem();
    }

    public ItemStack selectClassItem() {
        if (creator.getDNDClass() != null) {
            ItemStack item = CharacterCreatorItems.classItem(creator.getDNDClass());
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

        return new ChoiceMenuItem(item, creator.getClassChoiceResult()).getItem();
    }

    public ItemStack subracesItem() {
        ItemStack item = new ItemStack(Material.TURTLE_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = Lists.newArrayList();

        if (creator.getSubrace() == null) {
            assert meta != null;
            meta.setDisplayName(ChatColor.AQUA + "Subraces");
            lore.add(ChatColor.GRAY + "Subrace Options:");

            for (Subrace subrace : creator.getRace().getSubraces())
                lore.add(ChatColor.WHITE + "- " + subrace.getName());

            lore.add("");
            lore.add(ChatColor.WHITE + "Click to select your subrace");
        } else {
            assert meta != null;
            meta.setDisplayName(ChatColor.AQUA + "Subrace: " + creator.getSubrace().getName());
            lore.addAll(Util.fitForLore(ChatColor.GRAY + CharacterCreator.abilityScoreIncString(creator.getSubrace().getAbilityBonuses())));
            lore.add("");
            lore.addAll(Util.fitForLore(ChatColor.GRAY + creator.getSubrace().getDescription()));
            lore.add("");
            lore.add(ChatColor.WHITE + "Click for more info");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "subrace");

        return new ChoiceMenuItem(item, creator.getSubraceChoiceResult()).getItem();
    }

    public ItemStack startingProfChoiceItem() {
        ItemStack item = new ItemStack(Material.TARGET, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Proficiency Options");
        List<String> lore = Lists.newArrayList();

        if (!creator.getRaceProfChoiceResult().isComplete()) {
            lore.add(ChatColor.GRAY + "Select " + creator.getRaceProfChoiceResult().getChoice().getChoiceAmount() + (creator.getRaceProfChoiceResult().getChoice().getChoiceAmount() > 1 ? " proficiencies" : " proficiency") + " from the following:");

            List<Option> options = Lists.newArrayList();

            options.addAll(creator.getRace().getStartingProficiencyOptions().getOptions());

            for (Option proficiency : options)
                lore.add(ChatColor.WHITE + "- " + proficiency.getDescription());

            lore.add("");
        }

        if (creator.getRaceProfChoiceResult().getChosen().size() > 0) {
            lore.add(ChatColor.GRAY + "Selected Proficiencies:");
            for (Option proficiency : creator.getRaceProfChoiceResult().getChosen())
                lore.add(ChatColor.WHITE + "- " + proficiency.getDescription());

            lore.add("");

            if (creator.getRaceProfChoiceResult().isComplete())
                lore.add(ChatColor.WHITE + "Click to edit your starting proficiencies");
            else
                lore.add(ChatColor.WHITE + "Click to finish selecting your starting proficiencies");
        } else
            lore.add(ChatColor.WHITE + "Click to select your starting proficiencies");

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "race proficiency choice");

        return new ChoiceMenuItem(item, creator.getRaceProfChoiceResult()).getItem();
    }

    public ItemStack selectSubraceItem() {
        if (creator.getSubrace() != null) {
            ItemStack item = CharacterCreatorItems.subraceItem(creator.getSubrace());
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

        return new ChoiceMenuItem(item, creator.getSubraceChoiceResult()).getItem();
    }

    public ItemStack selectRaceItem() {
        if (creator.getRace() != null) {
            ItemStack item = CharacterCreatorItems.raceItem(creator.getRace());
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

        return new ChoiceMenuItem(item, creator.getRaceChoiceResult()).getItem();
    }

    public ItemStack traitItem(Trait trait) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + trait.getName());
        List<String> lore = Lists.newArrayList();

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
                ChoiceResult choiceResult = creator.getSubtraitChoices().get(trait);

                choiceResultLoreHelper(lore, choiceResult);

                if (choiceResult.getChosen().size() < choiceResult.getChoice().getChoiceAmount())
                    lore.add(ChatColor.WHITE + "Click to select subtraits");
                else
                    lore.add(ChatColor.WHITE + "Click to change your subtrait selections");
            }

            if (specific.getSpellOptions() != null) {
                ChoiceResult choiceResult = creator.getTraitSpellChoices().get(trait);

                choiceResultLoreHelper(lore, choiceResult);

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
            List<ChoiceResult> results = Lists.newArrayList();

            if (trait.getTraitSpecific().getSubtraitOptions() != null)
                results.addAll(creator.getSubtraitChoices().values());

            if (trait.getTraitSpecific().getSpellOptions() != null)
                results.addAll(creator.getTraitSpellChoices().values());

            if (results.size() > 0)
                return new ChoiceMenuItem(item, results).getItem();
        }

        return item;
    }

    private void choiceResultLoreHelper(List<String> lore, ChoiceResult choiceResult) {
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
    }

    public ItemStack subtraitItem(SingleOption subtraitOption) {
        Trait subtrait = new Trait(subtraitOption.getItem().getReference().getUrl());
        ItemStack item = traitItem(subtrait);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();

        ChoiceResult result = subtrait.getParent() == null ? null : creator.getSubtraitChoices().get(subtrait.getParent());
        assert lore != null;
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
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(ChatColor.AQUA + "Racial Traits");
        List<String> lore = Lists.newArrayList();

        for (int i = 0; i < creator.getSubrace().getRacialTraits().size(); i++) {
            Trait trait = creator.getSubrace().getRacialTraits().get(i);

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

        NBTHandler.addString(item, "goesTo", "subrace traits");

        return new ChoiceMenuItem(item, new ArrayList<>(creator.getTraitSpellChoices().values())).getItem();
    }

    public ItemStack raceTraitItem() {
        ItemStack item = new ItemStack(Material.CREEPER_BANNER_PATTERN, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(ChatColor.AQUA + "Racial Traits");
        List<String> lore = Lists.newArrayList();

        for (int i = 0; i < creator.getRace().getTraits().size(); i++) {
            Trait trait = creator.getRace().getTraits().get(i);

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

        return new ChoiceMenuItem(item, new ArrayList<>(creator.getSubtraitChoices().values())).getItem();
    }

    public ItemStack subraceLanguageChoiceItem() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Language Options");
        List<String> lore = Lists.newArrayList();

        if (!creator.getSubraceLanguageChoiceResult().isComplete()) {
            lore.add(ChatColor.GRAY + "Select " + creator.getSubraceLanguageChoiceResult().getChoice().getChoiceAmount() + (creator.getSubraceLanguageChoiceResult().getChoice().getChoiceAmount() > 1 ? " languages" : " language") + " from the following:");

            for (Option option : creator.getSubrace().getLanguageOptions().getOptions())
                lore.add(ChatColor.WHITE + "- " + ((SingleOption) option).getItem().getReference().getName());

            lore.add("");
        }

        if (creator.getSubraceLanguageChoiceResult().getChosen().size() > 0) {
            lore.add(ChatColor.GRAY + "Selected Languages:");
            for (Option language : creator.getSubraceLanguageChoiceResult().getChosen())
                lore.add(ChatColor.WHITE + "- " + language.getDescription());

            lore.add("");

            if (creator.getSubraceLanguageChoiceResult().isComplete())
                lore.add(ChatColor.WHITE + "Click to edit your starting languages");
            else
                lore.add(ChatColor.WHITE + "Click to finish selecting your starting languages");
        } else
            lore.add(ChatColor.WHITE + "Click to select your starting languages");

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "subrace language choice");

        return new ChoiceMenuItem(item, creator.getSubraceLanguageChoiceResult()).getItem();
    }

    public ItemStack raceLanguageChoiceItem() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Language Options");
        List<String> lore = Lists.newArrayList();

        if (!creator.getRaceLanguageChoiceResult().isComplete()) {
            lore.add(ChatColor.GRAY + "Select " + creator.getRaceLanguageChoiceResult().getChoice().getChoiceAmount() + (creator.getRaceLanguageChoiceResult().getChoice().getChoiceAmount() > 1 ? " languages" : " language") + " from the following:");

            for (Option option : creator.getRace().getLanguageOptions().getOptions())
                lore.add(ChatColor.WHITE + "- " + ((SingleOption) option).getItem().getReference().getName());

            lore.add("");
        }

        if (creator.getRaceLanguageChoiceResult().getChosen().size() > 0) {
            lore.add(ChatColor.GRAY + "Selected Languages:");
            for (Option language : creator.getRaceLanguageChoiceResult().getChosen())
                lore.add(ChatColor.WHITE + "- " + language.getDescription());

            lore.add("");

            if (creator.getRaceLanguageChoiceResult().isComplete())
                lore.add(ChatColor.WHITE + "Click to edit your starting languages");
            else
                lore.add(ChatColor.WHITE + "Click to finish selecting your starting languages");
        } else
            lore.add(ChatColor.WHITE + "Click to select your starting languages");

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "race language choice");

        return new ChoiceMenuItem(item, creator.getRaceLanguageChoiceResult()).getItem();
    }

    public ItemStack startingEquipmentItem() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = Lists.newArrayList();

        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Equipment");

        lore.add(ChatColor.GRAY + "Equipment: ");

        for (CountedReference reference : creator.getDNDClass().getStartingEquipment())
            lore.add(ChatColor.WHITE + "- " + reference.getReference().getName() + (reference.getCount() > 1 ? " x" + reference.getCount() : ""));

        if (lore.size() > 1)
            lore.add(ChatColor.WHITE + "- and " + creator.getDNDClass().getStartingEquipmentOptions().size() + " other choices...");
        else
            lore.add(ChatColor.WHITE + "- " + creator.getDNDClass().getStartingEquipmentOptions().size() + " choices");

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to choose your starting equipment");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "starting equipment");

        return new ChoiceMenuItem(item, creator.getStartingEquipmentChoiceResult()).getItem();
    }

    public ItemStack spellcastingItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Spellcasting");
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "This class gains the ability to cast spells at level " + creator.getDNDClass().getSpellcasting().getLevel(), "", ChatColor.GRAY + "Spellcasting Ability: " + ChatColor.WHITE + creator.getDNDClass().getSpellcasting().getSpellcastingAbility().getFullName(), "", ChatColor.WHITE + "Click for more info");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "spellcasting");
    }

    public ItemStack classProficiencyChoicesItem() {
        ItemStack item = new ItemStack(Material.TARGET);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Proficiency Choices");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to select your class's starting proficiencies"));
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "class proficiency choices"), creator.getClassProficiencyChoiceResults()).getItem();
    }

    public ItemStack selectSubclassItem() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Subclasses");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view subclass options for " + creator.getDNDClass().getName()));
        item.setItemMeta(meta);

        NBTHandler.addString(item, "goesTo", "subclass");

        if (creator.getSubclassChoiceResult() == null)
            return item;
        else
            return new ChoiceMenuItem(item, creator.getSubclassChoiceResult()).getItem();
    }

    public ItemStack subclassMenuItem() {
        ItemStack item = CharacterCreatorItems.subclassItem(creator.getSubclass());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        lore.set(lore.size() - 1, ChatColor.WHITE + "Click to select a different subclass");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "select subclass");
    }

    public ItemStack subclassSpellItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Expanded Spell List");
        List<String> lore = Util.fitForLore(ChatColor.GRAY + "This subclass lets you choose from an expended list of spells when you learn a new spell. The following spells are available for each spell level.");
        lore.add("");

        HashMap<Integer, List<Spell>> spells = creator.getSubclass().getSpellsByLevel();

        for (int spellLevel : spells.keySet()) {
            StringBuilder string = new StringBuilder(ChatColor.WHITE + CharacterCreator.nthLevelString(spellLevel) + ": ");

            for (Spell spell : spells.get(spellLevel))
                string.append(spell.getName()).append(", ");

            lore.addAll(Util.fitForLore(string.substring(0, string.length() - 2)));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack selectBackgroundItem() {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Select A Background");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view background options"));
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "select background"), creator.getBackgroundChoiceResult()).getItem();
    }

    public ItemStack backgroundItem() {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + creator.getBackground().getName());
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to select a different background"));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "select background");
    }

    public ItemStack backgroundIdealsItem() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Ideals");
        List<String> lore = Lists.newArrayList();

        if (creator.getBackgroundIdeals().getChosen().size() > 1) {
            lore.add(ChatColor.GRAY + "Currently Selected: ");

            for (Option option : creator.getBackgroundIdeals().getChosen())
                lore.addAll(Util.fitForLore(ChatColor.WHITE + "- " + option.getDescription()));
            lore.add("");
        } else if (creator.getBackgroundIdeals().getChosen().size() == 1) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + "Currently Selected: " + ChatColor.WHITE + creator.getBackgroundIdeals().getChosen().get(0).getDescription()));
            lore.add("");
        }

        if (creator.getBackgroundIdeals().isComplete())
            lore.add(ChatColor.WHITE + "Click to edit current selections");
        else
            lore.add(ChatColor.WHITE + "Click to select your character bonds from your background");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "choose ideals"), creator.getBackgroundIdeals()).getItem();
    }

    public ItemStack backgroundBondsItem() {
        ItemStack item = new ItemStack(Material.CHAIN);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Bonds");
        List<String> lore = Lists.newArrayList();

        if (creator.getBackgroundBonds().getChosen().size() > 1) {
            lore.add(ChatColor.GRAY + "Currently Selected: ");

            for (Option option : creator.getBackgroundBonds().getChosen())
                lore.addAll(Util.fitForLore(ChatColor.WHITE + "- " + option.getDescription()));
            lore.add("");
        } else if (creator.getBackgroundBonds().getChosen().size() == 1) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + "Currently Selected: " + ChatColor.WHITE + creator.getBackgroundBonds().getChosen().get(0).getDescription()));
            lore.add("");
        }

        if (creator.getBackgroundBonds().isComplete())
            lore.add(ChatColor.WHITE + "Click to edit current selections");
        else
            lore.add(ChatColor.WHITE + "Click to select your character bonds from your background");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "choose bonds"), creator.getBackgroundBonds()).getItem();
    }

    public ItemStack backgroundFlawsItem() {
        ItemStack item = new ItemStack(Material.WITHER_ROSE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Flaws");
        List<String> lore = Lists.newArrayList();

        if (creator.getBackgroundFlaws().getChosen().size() > 1) {
            lore.add(ChatColor.GRAY + "Currently Selected: ");

            for (Option option : creator.getBackgroundFlaws().getChosen())
                lore.addAll(Util.fitForLore(ChatColor.WHITE + "- " + option.getDescription()));
            lore.add("");
        } else if (creator.getBackgroundFlaws().getChosen().size() == 1) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + "Currently Selected: " + ChatColor.WHITE + creator.getBackgroundFlaws().getChosen().get(0).getDescription()));
            lore.add("");
        }

        if (creator.getBackgroundFlaws().isComplete())
            lore.add(ChatColor.WHITE + "Click to edit current selections");
        else
            lore.add(ChatColor.WHITE + "Click to select your character flaws from your background");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "choose flaws"), creator.getBackgroundFlaws()).getItem();
    }

    public ItemStack backgroundPersonalityTraitsItem() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Personality Traits");
        List<String> lore = Lists.newArrayList();

        if (creator.getBackgroundPersonalityTraits().getChosen().size() > 1) {
            lore.add(ChatColor.GRAY + "Currently Selected: ");

            for (Option option : creator.getBackgroundPersonalityTraits().getChosen())
                lore.addAll(Util.fitForLore(ChatColor.WHITE + "- " + option.getDescription()));

            lore.add("");
        } else if (creator.getBackgroundPersonalityTraits().getChosen().size() == 1) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + "Currently Selected: " + ChatColor.WHITE + creator.getBackgroundPersonalityTraits().getChosen().get(0).getDescription()));
            lore.add("");
        }

        if (creator.getBackgroundPersonalityTraits().isComplete())
            lore.add(ChatColor.WHITE + "Click to edit current selections");
        else
            lore.add(ChatColor.WHITE + "Click to select your personality traits from your background");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "choose personality traits"), creator.getBackgroundPersonalityTraits()).getItem();
    }

    public ItemStack backgroundRoleplayItem() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Roleplay");
        meta.setLore(Util.fitForLore(ChatColor.WHITE + "Click to choose the personality traits, bonds, ideals, and flaws this background offers"));
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "background roleplay"), Lists.newArrayList(creator.getBackgroundBonds(), creator.getBackgroundPersonalityTraits(), creator.getBackgroundFlaws(), creator.getBackgroundIdeals())).getItem();
    }

    public ItemStack backgroundFeatureItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + creator.getBackground().getBackgroundFeature().getName());
        List<String> lore = Lists.newArrayList();

        for (int i = 0; i < creator.getBackground().getBackgroundFeature().getDesc().size(); i++) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + creator.getBackground().getBackgroundFeature().getDesc().get(i)));

            if (i + 1 != creator.getBackground().getBackgroundFeature().getDesc().size())
                lore.add("");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack backgroundStartingEquipmentItem() {
        ItemStack item = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Equipment");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to view the equipment this background grants you"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "background equipment"), creator.getBackgroundStartingEquipmentChoiceResult()).getItem();
    }

    public ItemStack backgroundLanguageOptionsItem() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Language Options");
        List<String> lore = Lists.newArrayList();

        if (creator.getBackgroundLanguageChoice().getChosen().size() > 1) {
            lore.add(ChatColor.GRAY + "Currently Selected:");
            for (Option option : creator.getBackgroundLanguageChoice().getChosen())
                lore.add(ChatColor.WHITE + "- " + option.getDescription());
        } else if (creator.getBackgroundLanguageChoice().getChosen().size() == 1)
            lore.add(ChatColor.GRAY + "Currently Selected: " + ChatColor.WHITE + creator.getBackgroundLanguageChoice().getChosen().get(0).getDescription());

        lore.add("");

        if (!creator.getBackgroundLanguageChoice().isComplete())
            lore.add(ChatColor.WHITE + "Click to select a language this background teaches you");
        else
            lore.add(ChatColor.WHITE + "Click to edit your current language selections");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(NBTHandler.addString(item, "goesTo", "background language options"), creator.getBackgroundLanguageChoice()).getItem();
    }

    public ItemStack abilityScoreItem(AbilityScore score) {
        ItemStack item;

        switch (score.getName()) {
            case "CHA":
                item = new ItemStack(Material.POPPY);
                break;
            case "CON":
                item = new ItemStack(Material.IRON_CHESTPLATE);
                break;
            case "DEX":
                item = new ItemStack(Material.FEATHER);
                break;
            case "STR":
                item = new ItemStack(Material.DIAMOND_SWORD);
                break;
            case "INT":
                item = new ItemStack(Material.ENCHANTED_BOOK);
                break;
            case "WIS":
                item = new ItemStack(Material.ENDER_EYE);
                break;
            default:
                throw new IllegalArgumentException();
        }

        item.setAmount(creator.getAbilityScores().get(score));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + score.getFullName());
        List<String> lore = Lists.newArrayList();

        for (String string : score.getDescription()) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + string));

            if (!score.getDescription().get(score.getDescription().size() - 1).equals(string))
                lore.add("");
        }

        lore.add("");

        lore.addAll(Util.fitForLore(ChatColor.GRAY + "You have " + CharacterCreator.pointBuyMap().get(creator.getAbilityScores().get(score)) + " points put into " + score.getFullName()));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }


    // Static Methods
    public static ItemStack increaseAbilityScoreItem(AbilityScore score) {
        ItemStack item = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        assert meta != null;
        meta.setColor(Color.LIME);
        meta.setDisplayName(ChatColor.GREEN + "Increase");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to allocate more points into " + score.getFullName()));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "increaseAbility", score.getUrl());
    }

    public static ItemStack traitSpellItem(ChoiceResult result, SingleOption spellOption) {
        Spell spell = Spell.fromIndex(spellOption.getItem().getReference().getIndex());
        ItemStack item = new ItemStack(result.alreadyChosen(spellOption) ? Material.ENCHANTED_BOOK : Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = Lists.newArrayList();
        assert meta != null;
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

    // Static Item Methods
    public static ItemStack infoItem(Material material, Info info) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + info.getName());
        List<String> lore = Lists.newArrayList();

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
        List<String> lore = Lists.newArrayList();
        assert meta != null;
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

    public static ItemStack proficiencyItem(Proficiency proficiency, boolean isProficientAlready) {
        ItemStack item = new ItemStack(isProficientAlready ? Material.LIME_STAINED_GLASS_PANE : Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = Lists.newArrayList();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + proficiency.getName());

        if (proficiency.getType().equals("Skills")) {
            Skill skill = new Skill(proficiency.getUsageReference().getUrl());

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

    public static ItemStack startingProficienciesItem(List<Proficiency> proficiencies) {
        ItemStack item = new ItemStack(Material.FLETCHING_TABLE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Starting Proficiencies");
        List<String> lore = Lists.newArrayList();
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
        List<String> lore = Lists.newArrayList();

        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Ability Score Increase: " + ChatColor.WHITE + CharacterCreator.abilityScoreIncString(subrace.getAbilityBonuses())));
        lore.add("");
        lore.addAll(Util.fitForLore(ChatColor.GRAY + subrace.getDescription()));
        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select this subrace");
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + subrace.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "subrace", subrace.getUrl());

        return NBTHandler.addString(item, "goesTo", "subrace");
    }

    public static ItemStack decreaseAbilityScoreItem(AbilityScore score) {
        ItemStack item = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        assert meta != null;
        meta.setColor(Color.RED);
        meta.setDisplayName(ChatColor.RED + "Decrease");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to allocate less points into " + score.getFullName()));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "decreaseAbility", score.getUrl());
    }

    public static ItemStack backgroundItem(Background background) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + background.getName());
        StringBuilder proficiencyString = new StringBuilder(ChatColor.GRAY + "Proficiencies:");

        for (Proficiency proficiency : background.getProficiencies())
            proficiencyString.append(" ").append(proficiency.getName()).append(",");

        List<String> lore = Util.fitForLore(proficiencyString.substring(0, proficiencyString.length() - 1));
        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select this background");
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "background", background.getIndex());

        return NBTHandler.addString(item, "goesTo", "backgrounds");
    }

    public static ItemStack levelInfoItem() {
        ItemStack item = new ItemStack(Material.LECTERN);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Leveling Perks");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to see this class's perk values at each level"));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "leveling perks");
    }

    public static ItemStack perkInfoTableValueJSONObject(String name, JSONObject json) {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, (int) (long) json.get("dice_count"));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + name);
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + name + " is " + json.get("dice_count") + "d" + json.get("dice_value") + " at this level"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack perkInfoTableValue(String name, int value) {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, value);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + name);
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + name + " is " + (value > 0 ? "+" : "") + value + " at this level"));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack perkMenuPrevPage() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "View Lower Levels");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view this class's perk values at lower levels"));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack perkMenuNextPage() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "View Higher Levels");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view this class's perk values at higher levels"));
        item.setItemMeta(meta);

        return item;
    }

    static ItemStack perkMenuSignItem(int level) {
        ItemStack item = new ItemStack(Material.SPRUCE_SIGN);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Viewing levels " + level + " through " + (level + 4));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "row", Integer.toString(level));
    }

    public static ItemStack previousFeatureRow() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "View Lower Levels");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view this class's features at lower levels"));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack spellTableNextRow() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "View Higher Levels");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view this class's spell slots at higher levels"));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack spellTablePrevRow() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "View Lower Levels");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view this class's spell slots at lower levels"));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack subclassLevelItem() {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Subclass Features");
        meta.setLore(Lists.newArrayList(ChatColor.WHITE + "Click to view this subclass's features"));
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "subclass features");
    }

    public static ItemStack subclassItem(Subclass subclass) {
        ItemStack item = new ItemStack(Material.GLASS);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + subclass.getFlavorText() + ": " + subclass.getName());
        List<String> lore = Lists.newArrayList();

        for (String desc : subclass.getDescription())
            lore.addAll(Util.fitForLore(ChatColor.GRAY + desc));

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to view this subclass's features");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "subclass", subclass.getIndex());
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
        List<String> lore = Lists.newArrayList();
        StringBuilder proficiencyString = new StringBuilder();

        for (Proficiency prof : dndclass.getProficiencies())
            proficiencyString.append(prof.getName()).append(", ");

        proficiencyString.delete(proficiencyString.length() - 2, proficiencyString.length());

        StringBuilder savingThrowString = new StringBuilder();

        for (AbilityScore score : dndclass.getSavingThrows())
            savingThrowString.append(score.getFullName()).append(", ");
        savingThrowString.delete(savingThrowString.length() - 2, savingThrowString.length());


        lore.add(ChatColor.GRAY + "Hit Die: " + ChatColor.WHITE + "d" + dndclass.getHitDie());
        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Proficiencies: " + ChatColor.WHITE + proficiencyString));
        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Saving Throws: " + ChatColor.WHITE + savingThrowString));

        if (dndclass.getSubclasses().size() > 0) {
            StringBuilder subclassesString = new StringBuilder();

            for (Subclass subclass : dndclass.getSubclasses())
                subclassesString.append(subclass.getName()).append(", ");

            subclassesString.delete(subclassesString.length() - 2, subclassesString.length() + 1);

            lore.addAll(Util.fitForLore(ChatColor.GRAY + "Subclasses: " + ChatColor.WHITE + subclassesString));
        }

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select this class");
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + dndclass.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "class", dndclass.getUrl());

        return NBTHandler.addString(item, "goesTo", "class");
    }

    public static ItemStack selectSubclassItem(Subclass subclass) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + subclass.getName());
        List<String> lore = Lists.newArrayList();

        for (String string : subclass.getDescription())
            lore.addAll(Util.fitForLore(ChatColor.GRAY + string));

        lore.add("");
        lore.add(ChatColor.WHITE + "Click to select " + subclass.getName() + " as your " + subclass.getFlavorText());
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "subclass", subclass.getIndex());

        return NBTHandler.addString(item, "goesTo", "subclass");
    }

    public static ItemStack raceItem(Race race) {
        ItemStack item = new ItemStack(Material.GLASS, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = Lists.newArrayList();

        lore.addAll(Util.fitForLore(ChatColor.GRAY + "Ability Score Increase: " + ChatColor.WHITE + CharacterCreator.abilityScoreIncString(race.getAbilityBonuses())));
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
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + race.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "race", race.getUrl());

        return NBTHandler.addString(item, "goesTo", "race");
    }

    public static ItemStack nextFeatureRow() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "View Higher Levels");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Click to view this class's features at higher levels"));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack classSpecificColumn(String key) {
        ItemStack item = new ItemStack(Material.BOOKSHELF);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + Util.cleanEnumName(key));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack proficiencyColumn() {
        ItemStack item = new ItemStack(Material.TARGET);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Proficiency Bonus");
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack spellsKnownColumn() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Spells Known");
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack levelingSpellcastingItem() {
        ItemStack item = new ItemStack(Material.ENCHANTING_TABLE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Spellcasting");
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.GRAY + "Click to view the spellcasting table for this class");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "goesTo", "spellcastingTable");
    }

    public static ItemStack cantripsKnownColumn() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Cantrips Known");
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack perkMenuLevelItem(int level) {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, level);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "Level " + level);
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + "Items to the right show perk values for this level"));
        item.setItemMeta(meta);

        return item;
    }
}
