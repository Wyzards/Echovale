package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.Indexable;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.interaction.character.CharacterCreator;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class SingleOption extends Option {

    private final CountedReference item;

    public SingleOption(List<Prerequisite> prerequisites, CountedReference item) {
        super(OptionType.SINGLE, prerequisites);

        this.item = item;
    }

    public ItemStack getOptionItem(ChoiceResult parentResult, CharacterCreator creator) {
        APIReference reference = this.item.getReference();

        switch (parentResult.getChoice().getType()) {
            case "trait":
                return creator.subtraitItem(this);
            case "language":
            case "languages":
                return CharacterCreator.languageItem(new Language(reference), parentResult.alreadyChosen(this));
            case "proficiencies":
            case "proficiency":
                return CharacterCreator.proficiencyItem(new Proficiency(reference), parentResult.alreadyChosen(this));
            case "spell":
                return creator.traitSpellItem(parentResult, this);
            case "race":
                return CharacterCreator.raceItem(new Race(reference));
            case "subrace":
                return CharacterCreator.subraceItem(new Subrace(reference));
            case "class":
                return CharacterCreator.classItem(new DNDClass(reference));
            case "equipment":
                ItemStack item = this.getItem().getEquipment();
                if (parentResult.alreadyChosen(this))
                    item.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();

                if (!parentResult.isComplete() || parentResult.alreadyChosen(this)) {
                    lore.add("");
                    lore.add(ChatColor.WHITE + "Click to " + (parentResult.alreadyChosen(this) ? "unselect" : "select"));
                }

                meta.setLore(lore);
                item.setItemMeta(meta);

                return item;
            default:
                throw new IllegalArgumentException("ChoiceResult type " + parentResult.getChoice().getType() + " could not have an item created for it!");
        }
    }

    @Override
    public String getDescription() {
        return getItem().getReference().getName() + (item.getCount() > 1 ? " x" + item.getCount() : "");
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof SingleOption && ((SingleOption) object).getItem().equals(this.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.item);
    }

    // Getter methods
    public CountedReference getItem() {
        return this.item;
    }

}
