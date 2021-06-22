package com.Theeef.me.api.equipment.armor;

import com.Theeef.me.util.NBTHandler;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public enum ArmorPiece {

    BOOTS, LEGGINGS, CHESTPLATE, HELMET, SHIELD;

    public double getPercentage(List<ArmorPiece> setPieces) {
        double total = 0;

        for (ArmorPiece piece : setPieces)
            total += piece.getRawPercentage();

        return getRawPercentage() / BigDecimal.valueOf(total).setScale(1, RoundingMode.HALF_UP).doubleValue();
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

    // Static methods
    public static ArmorPiece getPiece(ItemStack item) {
        return ArmorPiece.valueOf(NBTHandler.getString(item, "armorPiece"));
    }
}
