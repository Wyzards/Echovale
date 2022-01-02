package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.backgrounds.Background;
import com.Theeef.me.api.backgrounds.Ideal;
import com.Theeef.me.api.chardata.Alignment;
import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.interaction.character.CharacterCreator;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class SingleIdealOption extends Option {

    Ideal ideal;

    public SingleIdealOption(List<Prerequisite> prerequisites, Ideal ideal) {
        super(OptionType.SINGLE, prerequisites);

        this.ideal = ideal;
    }

    public ItemStack getOptionItem(ChoiceResult parentResult, CharacterCreator creator) {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        String desc = this.ideal.getDescription();
        meta.setDisplayName(ChatColor.AQUA + desc.substring(0, desc.indexOf(" ") - 1));
        List<String> lore = Util.fitForLore(ChatColor.GRAY + desc);

        lore.add("");
        lore.add(ChatColor.GRAY + "Alignments:");
        for (Alignment alignment : this.ideal.getAlignments())
            lore.add(ChatColor.WHITE + "- " + alignment.getName());

        lore.add("");

        if (parentResult.alreadyChosen(this)) {
            lore.add(ChatColor.WHITE + "Click to unselect this option");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        } else
            lore.add(ChatColor.WHITE + "Click to select this option");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "ideal", desc);
    }

    @Override
    public String getDescription() {
        return this.ideal.getDescription();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof SingleIdealOption && ((SingleIdealOption) object).getIdeal().equals(this.ideal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.ideal);
    }

    // Getter methods
    public Ideal getIdeal() {
        return this.ideal;
    }

}
