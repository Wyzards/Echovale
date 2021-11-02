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
    private final ChoiceResult result;
    private final APIReference specificReference;

    public ChoiceMenu(String name, String previousPage, ChoiceResult result, Class<T> type, APIReference specificReference) {
        this.name = "Choice: " + name;
        this.previousPage = previousPage;
        this.result = result;
        this.specificReference = specificReference;
    }

    public ChoiceMenu(String name, String previousPage, ChoiceResult result, Class<T> type) {
        this(name, previousPage, result, type, null);
    }

    public void open(Player player) {
        CharacterCreator creator = CharacterCreator.getWIPCharacter(player);
        Inventory inventory = Bukkit.createInventory(null, 9 * ((this.result.getChoice().getOptions().size() - 1) / 9 + 3), this.name);

        if (this.previousPage != null)
            inventory.setItem(inventory.getSize() - 9, CharacterCreator.previousPage(this.previousPage));

        for (Option option : this.result.getChoice().getOptions())
            inventory.addItem(option.getOptionItem(this.result, creator));

        if (this.specificReference != null)
            for (ItemStack item : inventory.getContents())
                if (item != null)
                    NBTHandler.addString(item, "specificReference", this.specificReference.getUrl());

        for (ItemStack item : inventory.getContents())
            if (item != null)
                attachToItem(item);

        player.openInventory(inventory);
    }

    // Helper methods
    public ItemStack attachToItem(ItemStack item) {
        NBTHandler.addString(item, "choiceMenuCode", Integer.toString(hashCode()));

        if (!ChoiceMenu.menuMap.containsKey(hashCode()))
            ChoiceMenu.menuMap.put(hashCode(), this);

        return item;
    }

    // Getter methods
    public String getName() {
        return this.name;
    }

    public String getPreviousPage() {
        return this.previousPage;
    }

    public ChoiceResult getChoiceResult() {
        return this.result;
    }

    public APIReference getSpecificReference() {
        return this.specificReference;
    }

    // Static methods

    public static ChoiceMenu getMenuFromItem(ItemStack item) {
        return ChoiceMenu.menuMap.get(Integer.parseInt(NBTHandler.getString(item, "choiceMenuCode")));
    }
}
