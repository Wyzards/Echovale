package com.Theeef.me.items;

import com.Theeef.me.items.equipment.Armor;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDArmor extends DNDItem {

    private ArmorSet set;
    private ArmorSet.ArmorPiece piece;

    public DNDArmor(ArmorSet set, ArmorSet.ArmorPiece piece, String name, Material material, int amount, String description) {
        super(set.getID() + "_" + piece.name(), name == null ? set.getName() + " " + Util.cleanEnumName(piece.name()) : name, material, amount, description, MoneyAmount.fromGold(set.getGoldCost() * piece.getAdjustedPercentage(set.getPieces())), set.getWeight() * piece.getAdjustedPercentage(set.getPieces()), ItemType.ARMOR);

        this.set = set;
        this.piece = piece;
    }

    public DNDArmor(ArmorSet set, ArmorSet.ArmorPiece piece, String name, Material material) {
        this(set, piece, name, material, 1, null);
    }

    public DNDArmor(ArmorSet set, ArmorSet.ArmorPiece piece, Material material) {
        this(set, piece, null, material);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = super.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Cost: " + getCost().amountString(), ChatColor.GRAY + "Weight: " + ChatColor.WHITE + String.format("%.1f", getWeight()) + " pounds");
        lore.add(ChatColor.GRAY + "Strength Requirement: " + ChatColor.WHITE + set.getStrengthRequirement());
        lore.add(ChatColor.GRAY + "Armor Class: " + ChatColor.WHITE + set.getBaseAC() + (set.getDexMax() <= 0 ? "" : " + DEX Mod" + (set.getDexMax() == Integer.MAX_VALUE ? "" : " (Max of " + set.getDexMax() + ")")) + " with full set");
        lore.add("");

        if (set.getStealthDisadvantage()) {
            lore.add(ChatColor.GRAY + "Gives disadvantage on Stealth checks");
            lore.add("");
        }

        lore.add(ChatColor.GRAY + "Full Set:");

        for (ArmorSet.ArmorPiece piece : set.getPieces()) {
            DNDArmor armor = Armor.getArmor(set, piece);

            lore.add(ChatColor.WHITE + "- " + (true ? ChatColor.GREEN + armor.getName() + " [EQUIPPED] " + ChatColor.GRAY + Math.round(armor.getPiece().getAdjustedPercentage(set.getPieces()) * 100) + "%" : ChatColor.RED + armor.getName() + ChatColor.GRAY + Math.round(armor.getPiece().getAdjustedPercentage(set.getPieces()) * 100) + "%"));
        }

        // TODO: EDIT ONCE I CAN DETECT EQUIPPED ARMOR

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ArmorSet.ArmorPiece getPiece() {
        return this.piece;
    }

    public ArmorSet getSet() {
        return this.set;
    }
}
