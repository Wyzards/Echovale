package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.interaction.character.CharacterCreator;
import com.Theeef.me.interaction.character.ChoiceMenu;
import com.Theeef.me.interaction.character.ChoiceMenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
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

    public void choiceMenu(ChoiceMenu parentMenu, CharacterCreator creator) {
        ChoiceMenu menu = new ChoiceMenu(parentMenu.getName().substring(8), parentMenu, parentMenu.getChoiceResult().getChoiceOptionResult(this));
        menu.open(creator.getPlayer());
    }

    public void choiceMenu(Inventory inventory, ChoiceResult parentResult, CharacterCreator creator) {
        ChoiceMenu menu = new ChoiceMenu(inventory.getViewers().get(0).getOpenInventory().getTitle().substring(8), inventory, parentResult);
        menu.open(creator.getPlayer());
    }

    public ItemStack getMultiChoiceOptionItem(ChoiceResult choiceOptionResult) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.AQUA + "Make A Choice");
        lore.add(ChatColor.GRAY + "Select " + this.choice.getChoiceAmount() + " of the following:");

        for (Option option : this.choice.getOptions())
            lore.add(ChatColor.WHITE + "- " + option.getDescription());

        System.out.println("CHOICERESULT: " + choiceOptionResult);

        if (choiceOptionResult != null && choiceOptionResult.getChosen().size() > 0) {
            lore.add("");

            if (choiceOptionResult.getChosen().size() == 1)
                lore.add(ChatColor.GRAY + "Chosen: " + ChatColor.WHITE + choiceOptionResult.getChosen().get(0).getDescription());
            else {
                lore.add(ChatColor.GRAY + "Chosen:");

                for (Option selectedOption : choiceOptionResult.getChosen())
                    lore.add(ChatColor.WHITE + "- " + selectedOption.getDescription());
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getOptionItem(ChoiceResult parentResult) {
        ItemStack item = new ItemStack(parentResult != null && parentResult.alreadyChosen(this) ? Material.LIME_STAINED_GLASS_PANE : Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.AQUA + "Make A Choice");
        lore.add(ChatColor.GRAY + "Select " + this.choice.getChoiceAmount() + " of the following:");

        for (Option option : this.choice.getOptions())
            lore.add(ChatColor.WHITE + "- " + option.getDescription());

        if (parentResult != null) {
            ChoiceResult choiceOptionResult = parentResult.getChoiceOptionResult(this);

            if (choiceOptionResult != null && choiceOptionResult.getChosen().size() > 0) {
                lore.add("");

                if (choiceOptionResult.getChosen().size() == 1)
                    lore.add(ChatColor.GRAY + "Chosen: " + ChatColor.WHITE + choiceOptionResult.getChosen().get(0).getDescription());
                else {
                    lore.add(ChatColor.GRAY + "Chosen:");

                    for (Option selectedOption : choiceOptionResult.getChosen())
                        lore.add(ChatColor.WHITE + "- " + selectedOption.getDescription());
                }
            }

            if (parentResult.alreadyChosen(this)) {
                lore.add("");
                lore.add(ChatColor.WHITE + "Left Click to unchoose this option");

                if (choiceOptionResult == null || choiceOptionResult.getChosen().size() == 0)
                    lore.add(ChatColor.WHITE + "Right Click to make this choice");
                else
                    lore.add(ChatColor.WHITE + "Right Click to edit your choice");
            } else {
                if (!parentResult.isComplete(false)) {
                    lore.add("");
                    lore.add(ChatColor.WHITE + "Click to choose this option");
                }
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return new ChoiceMenuItem(item, parentResult.getChoiceOptionResult(this)).getItem();
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
