package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.api.common.Indexable;
import com.Theeef.me.interaction.character.CharacterCreator;
import com.Theeef.me.interaction.character.ChoiceMenu;
import com.Theeef.me.interaction.character.ChoiceMenuItem;
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
import java.util.Objects;

public class MultipleOption extends Option {

    private final List<CountedReference> items;
    private final List<ChoiceOption> choiceOptions;

    public MultipleOption(List<Prerequisite> prerequisites, List<CountedReference> items, List<ChoiceOption> choiceOptions) {
        super(Option.OptionType.MULTIPLE, prerequisites);

        this.items = items;
        this.choiceOptions = choiceOptions;
    }

    public Inventory optionInfoInventory(ChoiceMenu parentMenu) {
        ChoiceResult result = parentMenu.getChoiceResult();
        Inventory inventory = Bukkit.createInventory(null, 9 * (3 + getItems().size() / 9), "More Option Info");

        for (CountedReference item : getItems())
            inventory.addItem(item.getEquipment());

        for (ChoiceOption choiceOption : getChoices())
            inventory.addItem(choiceOption.getMultiChoiceOptionItem(result.getMultiOptionChoices(this) != null && result.getMultiOptionChoices(this).containsKey(choiceOption) ? result.getMultiOptionChoices(this).get(choiceOption) : null));

        inventory.setItem(inventory.getSize() - 9, NBTHandler.addString(parentMenu.attachToItem(CharacterCreator.previousPage("previous")), "optionIndex", Integer.toString(parentMenu.getChoiceResult().getChoice().getOptions().indexOf(this))));

        return inventory;
    }

    public void optionInfo(ChoiceMenu parentMenu, CharacterCreator creator) {
        creator.getPlayer().openInventory(optionInfoInventory(parentMenu));
    }

    public ItemStack getOptionItem(ChoiceResult parentResult) {
        ItemStack item = new ItemStack(parentResult.alreadyChosen(this) ? this.items.get(0).getEquipment().getType() : Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Multiple Items");
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Selecting this option grants the following:");

        for (CountedReference multiOptionItem : this.items)
            lore.add(ChatColor.WHITE + "- " + multiOptionItem.getDescription());

        for (ChoiceOption choiceOption : this.choiceOptions)
            lore.add(ChatColor.WHITE + "- " + choiceOption.getDescription());

        lore.add("");

        if (parentResult.alreadyChosen(this))
            lore.add(ChatColor.WHITE + "Left Click to unchoose this option");
        else if (!parentResult.isComplete(false))
            lore.add(ChatColor.WHITE + "Left Click to choose this option");
        lore.add(ChatColor.WHITE + "Right Click to see more information");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        if (parentResult.alreadyChosen(this))
            item.addUnsafeEnchantment(Enchantment.OXYGEN, 1);

        HashMap<ChoiceOption, ChoiceResult> multiOptionChoices = parentResult.getMultiOptionChoices(this);

        if (multiOptionChoices != null)
            for (ChoiceOption choiceOption : multiOptionChoices.keySet())
                if (!multiOptionChoices.get(choiceOption).isComplete())
                    return new ChoiceMenuItem(item, new ArrayList<>(multiOptionChoices.values())).getItem();

        return item;
    }

    @Override
    public String getDescription() {
        StringBuilder string = new StringBuilder();
        int itemsCount = this.items.size();
        int choicesCount = this.choiceOptions.size();
        int count = 0;

        for (CountedReference item : items) {
            count++;

            if (count == itemsCount + choicesCount)
                string.append("and ").append(items.get(count - 1).getDescription());
            else
                string.append(items.get(count - 1).getDescription() + ", ");
        }

        for (ChoiceOption option : choiceOptions) {
            count++;

            if (count == itemsCount + choicesCount)
                string.append("and ").append(choiceOptions.get(count - 1 - itemsCount).getDescription());
            else
                string.append(choiceOptions.get(count - 1 - itemsCount).getDescription() + ", ");
        }

        return string.toString();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof MultipleOption && ((MultipleOption) object).getItems().equals(this.items) && ((MultipleOption) object).getChoices().equals(this.choiceOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.items);
    }

    // Getter methods
    public List<CountedReference> getItems() {
        return this.items;
    }

    public List<ChoiceOption> getChoices() {
        return this.choiceOptions;
    }

}
