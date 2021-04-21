package com.Theeef.me.items;

import com.google.common.collect.Lists;

import java.util.List;

public class ArmorSet {

    // String ID, String name, MoneyAmount cost, int baseAC, int dexMax, int strengthRequirement,
    // boolean stealthDisadvantage, double weight, ArmorType armorType, int numPieces
    // new ArmorSet(id, name, desc, cost, ac, dexMax, str, disadvantage, weight, type, pieces...);

    // Light Armor Sets
    public static final ArmorSet PADDED = new ArmorSet("PADDED", "Padded", "Padded armor consists of quilted layers of cloth and batting.", 5, 11, Integer.MAX_VALUE, 0, true, 8, ArmorSet.ArmorType.LIGHT, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static final ArmorSet LEATHER = new ArmorSet("LEATHER", "Leather", "The breastplate and shoulder protectors of this armor are made of leather that has been stiffened by being boiled in oil. The rest of the armor is made of softer and more flexible materials.", 10, 11, Integer.MAX_VALUE, 0, false, 10, ArmorSet.ArmorType.LIGHT, ArmorSet.ArmorPiece.BOOTS, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static final ArmorSet STUDDED_LEATHER = new ArmorSet("STUDDED_LEATHER", "Studded Leather", "Made from tough but flexible leather, studded leather is reinforced with close-set rivets or spikes.", 45, 12, Integer.MAX_VALUE, 0, false, 13, ArmorSet.ArmorType.LIGHT, ArmorSet.ArmorPiece.BOOTS, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);

    // Medium Armor Sets
    public static final ArmorSet HIDE = new ArmorSet("HIDE", "Hide", "This crude armor consists of thick furs and pelts. It is commonly worn by barbarian tribes, evil humanoids, and other folk who lack access to the tools and materials needed to create better armor.", 10, 12, 2, 0, false, 12, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static final ArmorSet CHAIN_SHIRT = new ArmorSet("CHAIN_SHIRT", "Chain Shirt", "Made of interlocking metal rings, a chain shirt is worn between layers of clothing or leather. This armor offers modest protection to the wearer's upper body and allows the sound of the rings rubbing against one another to be muffled by outer layers.", 50, 13, 2, 0, false, 20, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.CHESTPLATE);
    public static final ArmorSet SCALE_MAIL = new ArmorSet("SCALE_MAIL", "Scale Mail", "This armor consists of a coat and leggings (and perhaps a separate skirt) of leather covered with overlapping pieces of metal, much like the scales of a fish. The suit includes gauntlets.", 50, 14, 2, 0, true, 45, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.CHESTPLATE, ArmorSet.ArmorPiece.LEGGINGS);
    public static final ArmorSet BREASTPLATE = new ArmorSet("BREASTPLATE", "Breastplate", "This armor consists of a fitted metal chest piece worn with supple leather. Although it leaves the legs and arms relatively unprotected, this armor provides good protection for the wearer's vital organs while leaving the wearer relatively unencumbered.", 400, 14, 2, 0, false, 20, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.CHESTPLATE);
    public static final ArmorSet HALF_PLATE = new ArmorSet("HALF_PLATE", "Half Plate", "Half plate consists of shaped metal plates that cover most of the wearer's body. It does not include leg protection beyond simple greaves that are attached with leather straps.", 750, 15, 2, 0, true, 40, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.values());

    // Heavy Armor Sets
    public static final ArmorSet RING_MAIL = new ArmorSet("RING_MAIL", "Ring Mail", "This armor is leather armor with heavy rings sewn into it. The rings help reinforce the armor against blows from swords and axes. Ring mail is inferior to chain mail, and it's usually worn only by those who can't afford better armor.", 30, 14, 0, 0, true, 40, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.BOOTS, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static final ArmorSet CHAIN_MAIL = new ArmorSet("CHAIN_MAIL", "Chain Mail", "Made of interlocking metal rings, chain mail includes a layer of quilted fabric worn underneath the mail to prevent chafing and to cushion the impact of blows. The suit includes gauntlets.", 75, 16, 0, 13, true, 55, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.values());
    public static final ArmorSet SPLINT = new ArmorSet("SPLINT", "Splint", "This armor is made of narrow vertical strips of metal riveted to a backing of leather that is worn over cloth padding. Flexible chain mail protects the joints.", 200, 17, 0, 15, true, 60, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.values());
    public static final ArmorSet PLATE = new ArmorSet("PLATE", "Plate", "Plate consists of shaped, interlocking metal plates to cover the entire body. A suit of plate includes gauntlets, heavy leather boots, a visored helmet, and thick layers of padding underneath the armor. Buckles and straps distribute the weight over the body.", 750, 18, 0, 15, true, 65, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.values());

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

    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public int getGoldCost() {
        return this.goldCost;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getStrengthRequirement() {
        return this.strengthRequirement;
    }

    public int getBaseAC() {
        return this.baseAC;
    }

    public int getDexMax() {
        return this.dexMax;
    }

    public boolean getStealthDisadvantage() {
        return this.stealthDisadvantage;
    }

    public ArmorPiece[] getPieces() {
        return this.pieces;
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
