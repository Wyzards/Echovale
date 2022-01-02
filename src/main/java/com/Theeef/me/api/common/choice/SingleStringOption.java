package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.interaction.character.CharacterCreator;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SingleStringOption extends Option {

    String string;

    public SingleStringOption(List<Prerequisite> prerequisites, String string) {
        super(OptionType.SINGLE, prerequisites);

        this.string = string;
    }

    public ItemStack getOptionItem(ChoiceResult parentResult, CharacterCreator creator) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + Util.fitForLore(this.string).get(0));
        List<String> lore = Util.fitForLore(ChatColor.AQUA + this.string);
        lore.remove(0);
        lore.add("");

        if (parentResult.alreadyChosen(this)) {
            lore.add(ChatColor.WHITE + "Click to unselect this option");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        } else
            lore.add(ChatColor.WHITE + "Click to select this option");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "optionString", this.string);
    }

    @Override
    public String getDescription() {
        return this.string;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof SingleStringOption && ((SingleStringOption) object).getDescription().equals(this.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.string);
    }

}
