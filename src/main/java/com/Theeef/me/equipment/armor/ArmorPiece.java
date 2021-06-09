package com.Theeef.me.equipment.armor;

import java.util.List;

public enum ArmorPiece {

    BOOTS, LEGGINGS, CHESTPLATE, HELMET;

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
