package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChoiceOption extends Option {

    private final Choice choice;

    public ChoiceOption(List<Prerequisite> prerequisites, Choice choice) {
        super(OptionType.CHOICE, prerequisites);

        this.choice = choice;
    }

    public ItemStack getOptionItem(ChoiceResult parentResult) {
        ItemStack item = new ItemStack(parentResult.alreadyChosen(this) ? Material.LIME_STAINED_GLASS_PANE : Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.AQUA + "Make A Choice");
        lore.add(ChatColor.GRAY + "Select " + this.choice.getChoiceAmount() + " of the following:");

        for (Option option : this.choice.getOptions())
            lore.add(ChatColor.WHITE + "- " + option.getDescription());

        if (parentResult.alreadyChosen(this)) {
            lore.add("");
            lore.add(ChatColor.WHITE + "Left Click to unselect this option");
            lore.add(ChatColor.WHITE + "Right Click to make this choice");
        } else {
            if (!parentResult.isComplete()) {
                lore.add("");
                lore.add(ChatColor.WHITE + "Click to select this option");
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public String getDescription() {
        if (this.choice.getOptionSet().getType() == OptionSet.OptionSetType.RESOURCE_LIST)
            return this.choice.getChoiceAmount() + " " + this.choice.getType() + (this.choice.getChoiceAmount() > 1 ? "s" : "") + " from the " + ((ResourceListOptionSet) this.choice.getOptionSet()).getReferenceList().get("name") + " list";

        return this.choice.getChoiceAmount() + " chosen option" + (this.choice.getChoiceAmount() > 1 ? "s" : "");
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ChoiceOption && ((ChoiceOption) object).getChoice().equals(this.choice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.choice);
    }

    // Getter methods
    public Choice getChoice() {
        return this.choice;
    }
}
