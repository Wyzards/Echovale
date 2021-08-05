package com.Theeef.me.interaction.character;

import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.*;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.api.spells.Spell;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChoiceMenu<T> {

    public static HashMap<Integer, ChoiceMenu> menuMap = new HashMap<>();

    private final String name;
    private final String previousPage;
    private final ChoiceMenu parentMenu;
    private final Inventory parentInventory;
    private final String nextPage;
    private final ChoiceResult result;
    private final Class<T> type;
    private final APIReference specificReference;

    public ChoiceMenu(String name, String previousPage, ChoiceMenu parentMenu, Inventory parentInventory, String nextPage, ChoiceResult result, Class<T> type, APIReference specificReference) {
        this.name = "Choice: " + name;
        this.previousPage = previousPage;
        this.parentMenu = parentMenu;
        this.parentInventory = parentInventory;
        this.nextPage = nextPage;
        this.result = result;
        this.type = type;
        this.specificReference = specificReference;
    }

    public ChoiceMenu(String name, String previousPage, ChoiceResult result, Class<T> type) {
        this(name, previousPage, null, null, null, result, type, null);
    }

    public ChoiceMenu(String name, String previousPage, ChoiceResult result, Class<T> type, APIReference specificReference) {
        this(name, previousPage, null, null, null, result, type, specificReference);
    }

    public ChoiceMenu(String name, ChoiceMenu parentMenu, ChoiceResult result, Class<T> type) {
        this(name, null, parentMenu, null, null, result, type, null);
    }

    public ChoiceMenu(String name, Inventory parentInventory, ChoiceResult result, Class<T> type) {
        this(name, null, null, parentInventory, null, result, type, null);
    }

    public void multiOptionInfo(ChoiceResult result, int optionIndex, CharacterCreator creator) {
        MultipleOption multiOption = (MultipleOption) result.getChoice().getOptions().get(optionIndex);
        Inventory inventory = Bukkit.createInventory(null, 27, "Items");

        for (Option option : multiOption.getItems())
            inventory.addItem(getItem(result, option, Equipment.class, creator, true));

        inventory.setItem(inventory.getSize() - 9, CharacterCreator.previousPage(hashCode()));


        for (ItemStack item : inventory.getContents())
            if (item != null) {
                NBTHandler.addString(item, "choiceMenuKey", Integer.toString(hashCode()));
                NBTHandler.addString(item, "optionIndex", Integer.toString(optionIndex));
            }
        creator.getPlayer().openInventory(inventory);
    }

    public void open(Player player) {
        CharacterCreator creator = CharacterCreator.getWIPCharacter(player);
        Inventory inventory = Bukkit.createInventory(null, getInventorySize(), this.name);

        if (this.previousPage != null)
            inventory.setItem(inventory.getSize() - 9, CharacterCreator.previousPage(this.previousPage));
        else if (this.parentInventory != null)
            inventory.setItem(inventory.getSize() - 9, NBTHandler.addString(CharacterCreator.previousPage("parentInventory"), "parentInventory", "true"));
        else if (this.parentMenu != null)
            inventory.setItem(inventory.getSize() - 9, CharacterCreator.previousPage(this.parentMenu.hashCode()));

        if (this.nextPage != null)
            inventory.setItem(inventory.getSize() - 1, CharacterCreator.nextPage(this.nextPage));

        for (int i = 0; i < this.result.getChoice().getOptionSet().getOptions().size(); i++)
            inventory.addItem(getItem(this.result, i, type, creator));

        if (this.specificReference != null)
            for (ItemStack item : inventory.getContents())
                if (item != null)
                    NBTHandler.addString(item, "specificReference", this.specificReference.getUrl());

        ChoiceMenu.menuMap.put(hashCode(), this);

        for (ItemStack item : inventory.getContents())
            if (item != null && !NBTHandler.hasString(item, "choiceMenuKey"))
                NBTHandler.addString(item, "choiceMenuKey", Integer.toString(hashCode()));

        player.openInventory(inventory);
    }

    // Helper methods
    private ItemStack getItem(ChoiceResult result, Option option, Class<?> type, CharacterCreator creator) {
        return getItem(result, option, type, creator, false);
    }

    private ItemStack getItem(ChoiceResult result, Option option, Class<?> type, CharacterCreator creator, boolean fromMultiOptionInfo) {
        switch (option.getOptionType()) {
            case SINGLE:
                APIReference reference = ((SingleOption) option).getItem().getReference();

                if (this.type == Trait.class)
                    return creator.subtraitItem((SingleOption) option);
                else if (this.type == Language.class)
                    return CharacterCreator.languageItem(new Language(reference), this.result.alreadyChosen((SingleOption) option));
                else if (this.type == Proficiency.class)
                    return CharacterCreator.proficiencyItem(new Proficiency(reference), this.result.alreadyChosen((SingleOption) option));
                else if (this.type == Spell.class)
                    return creator.traitSpellItem(this.result, (SingleOption) option);
                else if (this.type == Race.class)
                    return CharacterCreator.raceItem(new Race(reference));
                else if (this.type == Subrace.class)
                    return CharacterCreator.subraceItem(new Subrace(reference));
                else if (this.type == DNDClass.class)
                    return CharacterCreator.classItem(new DNDClass(reference));
                else if (this.type == Equipment.class) {
                    if (!fromMultiOptionInfo)
                        return equipmentOptionItem((SingleOption) option, result);
                    else
                        return Equipment.fromCountedReference(((SingleOption) option).getItem());
                } else
                    throw new IllegalArgumentException("Class " + type.getName() + " is invalid!");
            case MULTIPLE:
                assert option instanceof MultipleOption;
                return multipleItem((MultipleOption) option, result);
            case CHOICE:
                return choiceItem((ChoiceOption) option, result);
        }

        throw new IllegalArgumentException();
    }

    private ItemStack getItem(ChoiceResult result, int optionIndex, Class<?> type, CharacterCreator creator) {
        return getItem(result, result.getChoice().getOptionSet().getOptions().get(optionIndex), type, creator);
    }

    private ItemStack equipmentOptionItem(SingleOption option, ChoiceResult result) {
        ItemStack item = Equipment.fromCountedReference(option.getItem());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add("");

        if (result.alreadyChosen(option)) {
            lore.add(ChatColor.WHITE + "Click to unselect this option");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.OXYGEN, 1, true);
        } else
            lore.add(ChatColor.WHITE + "Click to select this option");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    // Only use for a ChoiceOption optionType
    private ItemStack choiceItem(ChoiceOption option, ChoiceResult result) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Item Choice");
        List<String> lore = new ArrayList<>();

        if (!result.isComplete()) {
            lore.addAll(Util.fitForLore(ChatColor.GRAY + "Selecting this option allows you to choose " + option.getChoice().getChoiceAmount() + " of the following:"));

            for (Option choiceOption : option.getChoice().getOptionSet().getOptions())
                lore.add(ChatColor.WHITE + "- " + choiceOption.getDescription());
        }

        if (result.getChoiceOptionResult(option).getChosen().size() == 1) {
            lore.add("");
            lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + result.getChoiceOptionResult(option).getChosen().get(0).getDescription());
        } else if (result.getChoiceOptionResult(option).getChosen().size() > 1) {
            lore.add("");
            lore.add(ChatColor.GRAY + "Seleted: ");

            for (Option choiceOption : result.getChosen())
                lore.add(ChatColor.WHITE + "- " + choiceOption.getDescription());
        }

        lore.add("");

        if (!result.alreadyChosen(option))
            lore.add(ChatColor.WHITE + "Click to select this option");
        else {
            lore.add(ChatColor.WHITE + "Right Click to complete this choice");
            lore.add(ChatColor.WHITE + "Left Click to unselect this option");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack multipleItem(MultipleOption option, ChoiceResult result) {
        ItemStack item = new ItemStack(result.alreadyChosen(option) ? Material.LIME_STAINED_GLASS_PANE : Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Multiple Items");
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Selecting this option grants the following:");

        for (Option multipleOption : option.getItems())
            lore.add(ChatColor.WHITE + "- " + multipleOption.getDescription());

        lore.add("");

        if (result.alreadyChosen(option))
            lore.add(ChatColor.WHITE + "Left Click to unselect this option");
        else
            lore.add(ChatColor.WHITE + "Left Click to select this option");
        lore.add(ChatColor.WHITE + "Right Click to see more information");
        meta.setLore(lore);
        item.setItemMeta(meta);

        if (result.getChoiceOptionResult(option) == null)
            return item;
        else
            return new ChoiceMenuItem(item, result.getChoiceOptionResult(option)).getItem();
    }

    private int getInventorySize() {
        return 9 * ((this.result.getChoice().getOptions().size() - 1) / 9 + 3);
    }

    // Getter methods

    public Inventory getParentInventory() {
        return this.parentInventory;
    }

    public ChoiceResult getChoiceResult() {
        return this.result;
    }

    // Static methods
    public static ChoiceMenu getMenuFromItem(ItemStack item) {
        return ChoiceMenu.menuMap.get(Integer.parseInt(NBTHandler.getString(item, "choiceMenuKey")));
    }
}
