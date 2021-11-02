package com.Theeef.me.api.common.choice;

import com.Theeef.me.interaction.character.CharacterCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChoiceResult {

    private final List<Option> chosen;
    private final Choice choice;
    private final HashMap<ChoiceOption, ChoiceResult> choiceOptions;
    private final HashMap<MultipleOption, HashMap<ChoiceOption, ChoiceResult>> multipleOptionChoiceOptions;

    public ChoiceResult(Choice choice) {
        this.choice = choice;
        this.chosen = new ArrayList<>();
        this.choiceOptions = new HashMap<>();
        this.multipleOptionChoiceOptions = new HashMap<>();
    }

    public ItemStack getItem(CharacterCreator creator) {
        ItemStack item = isComplete() ? this.chosen.get(0).getOptionItem(this, creator) : new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Make A Choice");
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + "Select " + choice.getChoiceAmount() + " of the following:");

        for (Option option : choice.getOptions())
            lore.add(ChatColor.WHITE + "- " + option.getDescription());

        if (this.chosen.size() > 0) {
            lore.add("");

            if (this.chosen.size() == 1)
                lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + this.chosen.get(0).getDescription());
            else {
                lore.add(ChatColor.GRAY + "Selected:");

                for (Option selectedOption : this.chosen)
                    lore.add(ChatColor.WHITE + "- " + selectedOption.getDescription());
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
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
        else if (option.getOptionType() == Option.OptionType.MULTIPLE && choiceOptions.containsKey((MultipleOption) option))
            this.choiceOptions.remove((MultipleOption) option);
    }

    public boolean isComplete() {
        for (ChoiceResult choiceOptionResult : this.choiceOptions.values())
            if (!choiceOptionResult.isComplete())
                return false;

        for (MultipleOption multipleOption : this.multipleOptionChoiceOptions.keySet())
            for (ChoiceResult choiceOptionResult : this.multipleOptionChoiceOptions.get(multipleOption).values())
                if (!choiceOptionResult.isComplete())
                    return false;

        return this.chosen.size() == this.choice.getChoiceAmount();
    }

    public void clearChoices() {
        this.chosen.clear();
        this.choiceOptions.clear();
        this.multipleOptionChoiceOptions.clear();
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
