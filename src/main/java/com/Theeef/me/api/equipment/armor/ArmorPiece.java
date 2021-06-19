package com.Theeef.me.api.equipment.armor;

import com.Theeef.me.util.NBTHandler;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public enum ArmorPiece {

    BOOTS, LEGGINGS, CHESTPLATE, HELMET, SHIELD;

    public static ArmorPiece getPiece(ItemStack item) {
        return ArmorPiece.valueOf(NBTHandler.getString(item, "armorPiece"));
    }

    public static double weighItem(ItemStack item) {
        return ArmorPiece.getPiece(item).weighItem(Double.parseDouble(NBTHandler.getString(item, "weight")), Armor.getSetPieces(item));
    }

    public double weighItem(double setWeight, List<ArmorPiece> setPieces) {
        return Math.round(getPercentage(setPieces) * setWeight);
    }

    public double getPercentage(List<ArmorPiece> setPieces) {
        double total = 0;

        for (ArmorPiece piece : setPieces)
            total += piece.getRawPercentage();

        return getRawPercentage() / total;
    }

    public double getRawPercentage() {
        switch (this) {
            case BOOTS:
                return 0.1;
            case HELMET:
                return 0.2;
            case CHESTPLATE:
                return 0.4;
            case LEGGINGS:
                return 0.3;
            default:
                return 1.0;
        }
    }
}
