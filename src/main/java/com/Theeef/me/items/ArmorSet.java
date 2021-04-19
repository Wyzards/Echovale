package com.Theeef.me.items;

import com.google.common.collect.Lists;

import java.util.List;

public class ArmorSet {

    private String ID;
    private String name;
    private String description;
    private int goldCost;
    private int baseAC;
    private int dexMax;
    private int strengthRequirement;
    private boolean stealthDisadvantage;
    private double weight;
    private ArmorType armorType;
    private ArmorPiece[] pieces;

    public ArmorSet(String ID, String name, String description, int goldCost, int baseAC, int dexMax, int strengthRequirement, boolean stealthDisadvantage, double weight, ArmorType armorType, ArmorPiece... pieces) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.goldCost = goldCost;
        this.baseAC = baseAC;
        this.dexMax = dexMax;
        this.strengthRequirement = strengthRequirement;
        this.stealthDisadvantage = stealthDisadvantage;
        this.weight = weight;
        this.armorType = armorType;
        this.pieces = pieces;
    }

    public double getWeight() {
        return this.weight;
    }

    public ArmorPiece[] getPieces() {
        return this.pieces;
    }

    public int getGoldCost() {
        return this.goldCost;
    }

    public String getID() {
        return this.ID;
    }

    public enum ArmorPiece {
        BOOTS, LEGGINGS, CHESTPLATE, HELMET;

        public double getAdjustedPercentage(ArmorPiece[] setPieces) {
            List<ArmorPiece> missingPieces = Lists.newArrayList(ArmorPiece.values());
            missingPieces.removeAll(Lists.newArrayList(setPieces));

            double missingPercentage = 0;

            for (ArmorPiece piece : missingPieces)
                missingPercentage += piece.getPercentage();

            return getPercentage() + missingPercentage / setPieces.length;
        }

        public double getPercentage() {
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

    public enum ArmorType {
        LIGHT, MEDIUM, HEAVY;
    }
}
