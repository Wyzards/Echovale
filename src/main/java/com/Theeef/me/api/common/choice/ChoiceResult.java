package com.Theeef.me.api.common.choice;

import com.Theeef.me.interaction.character.CharacterCreator;
import com.Theeef.me.interaction.character.ChoiceMenuItem;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChoiceResult {

    private final List<Option> chosen;
    private final Choice choice;
    private final HashMap<ChoiceOption, ChoiceResult> choiceOptions;
    private final HashMap<MultipleOption, HashMap<ChoiceOption, ChoiceResult>> multipleOptionChoiceOptions;
    public final List<ChoiceResult> excludedResults;
    public final List<Option> excludedOptions;

    public ChoiceResult(Choice choice, List<Option> excludedOptions, ChoiceResult... excludeResults) {
        this.choice = choice;
        this.chosen = new ArrayList<>();
        this.choiceOptions = new HashMap<>();
        this.multipleOptionChoiceOptions = new HashMap<>();
        this.excludedResults = Lists.newArrayList(excludeResults);
        this.excludedOptions = excludedOptions;
        this.excludedResults.removeIf(Objects::isNull);

        for (ChoiceResult result : this.excludedResults)
            result.exclude(this);
    }

    public ChoiceResult(Choice choice) {
        this(choice, Lists.newArrayList());
    }

    public void exclude(ChoiceResult... excluded) {
        for (ChoiceResult result : excluded)
            if (result != null && !this.excludedResults.contains(result)) {
                this.excludedResults.add(result);

                for (Option option : result.getChosen())
                    if (alreadyChosen(option))
                        unchoose(option);
            }
    }

    public void exclude(Option... options) {
        for (Option option : options) {
            this.excludedOptions.add(option);

            if (alreadyChosen(option))
                unchoose(option);
        }
    }

    public void clearOptionExclusions() {
        this.excludedOptions.clear();
    }

    public ItemStack getItem(CharacterCreator creator) {
        ItemStack item = isComplete() ? this.chosen.get(0).getOptionItem(this, creator) : new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Make A Choice");
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + "Choose " + choice.getChoiceAmount() + " of the following:");

        for (Option option : choice.getOptions())
            lore.add(ChatColor.WHITE + "- " + option.getDescription());

        if (this.chosen.size() > 0) {
            lore.add("");

            if (this.chosen.size() == 1)
                lore.add(ChatColor.GRAY + "Chosen: " + ChatColor.WHITE + this.chosen.get(0).getDescription());
            else {
                lore.add(ChatColor.GRAY + "Chosen:");

                for (Option selectedOption : this.chosen)
                    lore.add(ChatColor.WHITE + "- " + selectedOption.getDescription());
            }

            lore.add("");
            lore.add(ChatColor.WHITE + "Click to edit your choice");
        } else {
            lore.add("");
            lore.add(ChatColor.WHITE + "Click to make your choice");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(item, this).getItem();
    }

    public boolean alreadyChosen(Option option) {
        return this.chosen.contains(option);
    }

    public boolean choose(Option option) {
        if (alreadyChosen(option) || isComplete())
            return false;
        else {
            this.chosen.add(option);

            if (option.getOptionType() == Option.OptionType.CHOICE)
                this.choiceOptions.put((ChoiceOption) option, new ChoiceResult(((ChoiceOption) option).getChoice()));
            else if (option.getOptionType() == Option.OptionType.MULTIPLE) {
                HashMap<ChoiceOption, ChoiceResult> map = new HashMap<>();

                for (ChoiceOption multiOptionChoice : ((MultipleOption) option).getChoices())
                    map.put(multiOptionChoice, new ChoiceResult(multiOptionChoice.getChoice()));

                this.multipleOptionChoiceOptions.put((MultipleOption) option, map);
            }

            return true;
        }
    }

    public void unchoose(Option option) {
        this.chosen.remove(option);

        if (option.getOptionType() == Option.OptionType.CHOICE && choiceOptions.containsKey((ChoiceOption) option))
            this.choiceOptions.remove((ChoiceOption) option);
        else if (option.getOptionType() == Option.OptionType.MULTIPLE && multipleOptionChoiceOptions.containsKey((MultipleOption) option))
            this.multipleOptionChoiceOptions.remove((MultipleOption) option);
    }

    public boolean isComplete() {
        return isComplete(true);
    }

    public boolean isComplete(boolean includeSubchoices) {

        if (includeSubchoices) {
            for (ChoiceResult choiceOptionResult : this.choiceOptions.values())
                if (!choiceOptionResult.isComplete())
                    return false;

            for (MultipleOption multipleOption : this.multipleOptionChoiceOptions.keySet())
                for (ChoiceResult choiceOptionResult : this.multipleOptionChoiceOptions.get(multipleOption).values())
                    if (!choiceOptionResult.isComplete())
                        return false;
        }

        return this.chosen.size() == this.choice.getChoiceAmount();
    }

    public void clearChoices() {
        this.chosen.clear();
        this.choiceOptions.clear();
        this.multipleOptionChoiceOptions.clear();
    }

    public List<Option> getNonexcludedOptions() {
        List<Option> options = Lists.newArrayList();

        for (Option option : getChoice().getOptions())
            options.add(option);

        options.removeAll(getExcludedOptions());

        return options;
    }

    public List<Option> getExcludedOptions() {
        List<Option> options = Lists.newArrayList();

        for (ChoiceResult excludedResult : this.excludedResults)
            if (excludedResult != null)
                options.addAll(excludedResult.getChosen());

        options.addAll(this.excludedOptions);

        return options;
    }

    // Getter methods
    public ChoiceResult getChoiceOptionResult(ChoiceOption option) {
        return this.choiceOptions.get(option);
    }

    public HashMap<ChoiceOption, ChoiceResult> getMultiOptionChoices(MultipleOption option) {
        return this.multipleOptionChoiceOptions.get(option);
    }

    public List<Option> getChosen() {
        return this.chosen;
    }

    public Choice getChoice() {
        return this.choice;
    }

}
