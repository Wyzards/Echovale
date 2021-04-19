package com.Theeef.me.items.equipment;

import com.Theeef.me.items.ArmorSet;
import com.Theeef.me.items.DNDArmor;
import com.Theeef.me.items.MoneyAmount;
import org.bukkit.Material;

public class Armor {

    // String ID, String name, MoneyAmount cost, int baseAC, int dexMax, int strengthRequirement,
    // boolean stealthDisadvantage, double weight, ArmorType armorType, int numPieces
    // new ArmorSet(id, name, desc, cost, ac, dexMax, str, disadvantage, weight, type, pieces...);

    // Light Armor Sets
    public static ArmorSet PADDED = new ArmorSet("PADDED", "Padded", "Padded armor consists of quilted layers of cloth and batting.", 5, 11, Integer.MAX_VALUE, 0, true, 8, ArmorSet.ArmorType.LIGHT, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static ArmorSet LEATHER = new ArmorSet("LEATHER", "Leather", "The breastplate and shoulder protectors of this armor are made of leather that has been stiffened by being boiled in oil. The rest of the armor is made of softer and more flexible materials.", 10, 11, Integer.MAX_VALUE, 0, false, 10, ArmorSet.ArmorType.LIGHT, ArmorSet.ArmorPiece.BOOTS, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static ArmorSet STUDDED_LEATHER = new ArmorSet("STUDDED_LEATHER", "Studded Leather", "Made from tough but flexible leather, studded leather is reinforced with close-set rivets or spikes.", 45, 12, Integer.MAX_VALUE, 0, false, 13, ArmorSet.ArmorType.LIGHT, ArmorSet.ArmorPiece.BOOTS, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);

    // Medium Armor Sets
    public static ArmorSet HIDE = new ArmorSet("HIDE", "Hide", "This crude armor consists of thick furs and pelts. It is commonly worn by barbarian tribes, evil humanoids, and other folk who lack access to the tools and materials needed to create better armor.", 10, 12, 2, 0, false, 12, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static ArmorSet CHAIN_SHIRT = new ArmorSet("CHAIN_SHIRT", "Chain Shirt", "Made of interlocking metal rings, a chain shirt is worn between layers of clothing or leather. This armor offers modest protection to the wearer's upper body and allows the sound of the rings rubbing against one another to be muffled by outer layers.", 50, 13, 2, 0, false, 20, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.CHESTPLATE);
    public static ArmorSet SCALE_MAIL = new ArmorSet("SCALE_MAIL", "Scale Mail", "This armor consists of a coat and leggings (and perhaps a separate skirt) of leather covered with overlapping pieces of metal, much like the scales of a fish. The suit includes gauntlets.", 50, 14, 2, 0, true, 45, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.CHESTPLATE, ArmorSet.ArmorPiece.LEGGINGS);
    public static ArmorSet BREASTPLATE = new ArmorSet("BREASTPLATE", "Breastplate", "This armor consists of a fitted metal chest piece worn with supple leather. Although it leaves the legs and arms relatively unprotected, this armor provides good protection for the wearer's vital organs while leaving the wearer relatively unencumbered.", 400, 14, 2, 0, false, 20, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.CHESTPLATE);
    public static ArmorSet HALF_PLATE = new ArmorSet("HALF_PLATE", "Half Plate", "Half plate consists of shaped metal plates that cover most of the wearer's body. It does not include leg protection beyond simple greaves that are attached with leather straps.", 750, 15, 2, 0, true, 40, ArmorSet.ArmorType.MEDIUM, ArmorSet.ArmorPiece.values());

    // Heavy Armor Sets
    public static ArmorSet RING_MAIL = new ArmorSet("RING_MAIL", "Ring Mail", "This armor is leather armor with heavy rings sewn into it. The rings help reinforce the armor against blows from swords and axes. Ring mail is inferior to chain mail, and it's usually worn only by those who can't afford better armor.", 30, 14, 0, 0, true, 40, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.BOOTS, ArmorSet.ArmorPiece.LEGGINGS, ArmorSet.ArmorPiece.CHESTPLATE);
    public static ArmorSet CHAIN_MAIL = new ArmorSet("CHAIN_MAIL", "Chain Mail", "Made of interlocking metal rings, chain mail includes a layer of quilted fabric worn underneath the mail to prevent chafing and to cushion the impact of blows. The suit includes gauntlets.", 75, 16, 0, 13, true, 55, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.values());
    public static ArmorSet SPLINT = new ArmorSet("SPLINT", "Splint", "This armor is made of narrow vertical strips of metal riveted to a backing of leather that is worn over cloth padding. Flexible chain mail protects the joints.", 200, 17, 0, 15, true, 60, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.values());
    public static ArmorSet PLATE = new ArmorSet("PLATE", "Plate", "Plate consists of shaped, interlocking metal plates to cover the entire body. A suit of plate includes gauntlets, heavy leather boots, a visored helmet, and thick layers of padding underneath the armor. Buckles and straps distribute the weight over the body.", 750, 18, 0, 15, true, 65, ArmorSet.ArmorType.HEAVY, ArmorSet.ArmorPiece.values());
}
