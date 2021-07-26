package com.Theeef.me.interaction.character;

import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.common.choice.Option;
import com.Theeef.me.api.common.choice.SingleOption;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.Choice;
import com.Theeef.me.api.common.choice.ChoiceResult;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.api.spells.Spell;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChoiceMenu<T> {

    private final String name;
    private final String previousPage;
    private final String nextPage;
    private final Choice choice;
    private final ChoiceResult<T> result;
    private final Class type;
    private final APIReference specificReference;

    public ChoiceMenu(String name, String previousPage, String nextPage, Choice choice, ChoiceResult<T> result, Class type, APIReference specificReference) {
        this.name = "Choice: " + name;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.choice = choice;
        this.result = result;
        this.type = type;
        this.specificReference = specificReference;
    }

    public ChoiceMenu(String name, String previousPage, Choice choice, ChoiceResult result, Class type) {
        this(name, previousPage, null, choice, result, type, null);
    }

    public void open(Player player) {
        CharacterCreator creator = CharacterCreator.getWIPCharacter(player);
        Inventory inventory = Bukkit.createInventory(null, getInventorySize(), this.name);

        if (this.previousPage != null)
            inventory.setItem(inventory.getSize() - 9, CharacterCreator.previousPage(this.previousPage));
        if (this.nextPage != null)
            inventory.setItem(inventory.getSize() - 1, CharacterCreator.nextPage(this.nextPage));

        for (Option option : this.choice.getChoices()) {
            // Add item for each reference that isnt already chosen
            // Add dif item for each that IS chosen
            APIReference reference = ((SingleOption) option).getItem().getReference();

            if (this.type == Trait.class)
                inventory.addItem(creator.subtraitItem(new Trait(reference)));
            else if (this.type == Language.class) {
                Language language = new Language(reference);

                inventory.addItem(CharacterCreator.languageItem(language, this.result.alreadyChosen((T) language)));
            } else if (this.type == Proficiency.class) {
                Proficiency proficiency = new Proficiency(reference);

                inventory.addItem(CharacterCreator.proficiencyItem(proficiency, this.result.alreadyChosen((T) proficiency)));
            } else if (this.type == Spell.class)
                inventory.addItem(creator.traitSpellItem((ChoiceResult<Spell>) this.result, new Spell(reference)));
            else if (this.type == Race.class)
                inventory.addItem(CharacterCreator.raceItem(new Race(reference)));
            else if (this.type == Subrace.class)
                inventory.addItem(CharacterCreator.subraceItem(new Subrace(reference)));
            else if (this.type == DNDClass.class)
                inventory.addItem(CharacterCreator.classItem(new DNDClass(reference)));
        }

        if (this.specificReference != null)
            for (ItemStack item : inventory.getContents())
                if (item != null)
                    NBTHandler.addString(item, "specificReference", this.specificReference.getUrl());

        player.openInventory(inventory);
    }

    // Helper methods
    private int getInventorySize() {
        return 9 * ((this.choice.getChoices().size() - 1) / 9 + 3);
    }
}
