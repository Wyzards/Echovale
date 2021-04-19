package com.Theeef.me.items;

import org.bukkit.Material;

public class DNDArmor extends DNDItem {

    private ArmorSet set;
    private ArmorSet.ArmorPiece piece;

    public DNDArmor(ArmorSet set, ArmorSet.ArmorPiece piece, String name, Material material, int amount, String description) {
        super(set.getID() + "_" + piece.name(), name, material, amount, description, MoneyAmount.fromGold(set.getGoldCost() * piece.getAdjustedPercentage(set.getPieces())), set.);
    }
}
