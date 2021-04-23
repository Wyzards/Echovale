package com.Theeef.me.items.armor;

import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.util.Util;
import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.Set;

public class Armor {

    // Light Armor
    public static final DNDArmor PADDED_LEGGINGS = new DNDArmor(ArmorSet.PADDED, ArmorSet.ArmorPiece.LEGGINGS, Material.LEATHER_LEGGINGS);
    public static final DNDArmor PADDED_CHESTPLATE = new DNDArmor(ArmorSet.PADDED, ArmorSet.ArmorPiece.CHESTPLATE, Material.LEATHER_CHESTPLATE);
    public static final DNDArmor LEATHER_BOOTS = new DNDArmor(ArmorSet.LEATHER, ArmorSet.ArmorPiece.BOOTS, Material.LEATHER_BOOTS);
    public static final DNDArmor LEATHER_LEGGINGS = new DNDArmor(ArmorSet.LEATHER, ArmorSet.ArmorPiece.LEGGINGS, Material.LEATHER_LEGGINGS);
    public static final DNDArmor LEATHER_CHESTPLATE = new DNDArmor(ArmorSet.LEATHER, ArmorSet.ArmorPiece.CHESTPLATE, Material.LEATHER_CHESTPLATE);
    public static final DNDArmor STUDDED_LEATHER_BOOTS = new DNDArmor(ArmorSet.STUDDED_LEATHER, ArmorSet.ArmorPiece.BOOTS, Material.LEATHER_BOOTS);
    public static final DNDArmor STUDDED_LEATHER_LEGGINGS = new DNDArmor(ArmorSet.STUDDED_LEATHER, ArmorSet.ArmorPiece.LEGGINGS, Material.LEATHER_LEGGINGS);
    public static final DNDArmor STUDDED_LEATHER_CHESTPLATE = new DNDArmor(ArmorSet.STUDDED_LEATHER, ArmorSet.ArmorPiece.CHESTPLATE, Material.LEATHER_CHESTPLATE);

    // Medium Armor
    public static final DNDArmor HIDE_CHESTPLATE = new DNDArmor(ArmorSet.HIDE, ArmorSet.ArmorPiece.CHESTPLATE, Material.LEATHER_CHESTPLATE);
    public static final DNDArmor HIDE_LEGGINGS = new DNDArmor(ArmorSet.HIDE, ArmorSet.ArmorPiece.LEGGINGS, Material.LEATHER_LEGGINGS);
    public static final DNDArmor CHAIN_SHIRT = new DNDArmor(ArmorSet.CHAIN_SHIRT, ArmorSet.ArmorPiece.CHESTPLATE, "Chain Shirt", Material.CHAINMAIL_CHESTPLATE);
    public static final DNDArmor SCALE_MAIL_CHESTPLATE = new DNDArmor(ArmorSet.SCALE_MAIL, ArmorSet.ArmorPiece.CHESTPLATE, Material.IRON_CHESTPLATE);
    public static final DNDArmor SCALE_MAIL_LEGGINGS = new DNDArmor(ArmorSet.SCALE_MAIL, ArmorSet.ArmorPiece.LEGGINGS, Material.IRON_LEGGINGS);
    public static final DNDArmor BREASTPLATE = new DNDArmor(ArmorSet.BREASTPLATE, ArmorSet.ArmorPiece.CHESTPLATE, Material.IRON_CHESTPLATE);
    public static final DNDArmor HALF_PLATE_HELMET = new DNDArmor(ArmorSet.HALF_PLATE, ArmorSet.ArmorPiece.HELMET, Material.IRON_HELMET);
    public static final DNDArmor HALF_PLATE_CHESTPLATE = new DNDArmor(ArmorSet.HALF_PLATE, ArmorSet.ArmorPiece.CHESTPLATE, Material.IRON_CHESTPLATE);
    public static final DNDArmor HALF_PLATE_LEGGINGS = new DNDArmor(ArmorSet.HALF_PLATE, ArmorSet.ArmorPiece.LEGGINGS, Material.LEATHER_LEGGINGS);
    public static final DNDArmor HALF_PLATE_BOOTS = new DNDArmor(ArmorSet.HALF_PLATE, ArmorSet.ArmorPiece.BOOTS, Material.IRON_BOOTS);

    // Heavy Armor
    public static final DNDArmor RING_MAIL_BOOTS = new DNDArmor(ArmorSet.RING_MAIL, ArmorSet.ArmorPiece.BOOTS, Material.CHAINMAIL_BOOTS);
    public static final DNDArmor RING_MAIL_LEGGINGS = new DNDArmor(ArmorSet.RING_MAIL, ArmorSet.ArmorPiece.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
    public static final DNDArmor RING_MAIL_CHESTPLATE = new DNDArmor(ArmorSet.RING_MAIL, ArmorSet.ArmorPiece.CHESTPLATE, Material.CHAINMAIL_CHESTPLATE);
    public static final DNDArmor CHAIN_MAIL_BOOTS = new DNDArmor(ArmorSet.CHAIN_MAIL, ArmorSet.ArmorPiece.BOOTS, Material.CHAINMAIL_BOOTS);
    public static final DNDArmor CHAIN_MAIL_LEGGINGS = new DNDArmor(ArmorSet.CHAIN_MAIL, ArmorSet.ArmorPiece.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
    public static final DNDArmor CHAIN_MAIL_CHESTPLATE = new DNDArmor(ArmorSet.CHAIN_MAIL, ArmorSet.ArmorPiece.CHESTPLATE, Material.CHAINMAIL_CHESTPLATE);
    public static final DNDArmor CHAIN_MAIL_HELMET = new DNDArmor(ArmorSet.CHAIN_MAIL, ArmorSet.ArmorPiece.HELMET, Material.CHAINMAIL_HELMET);
    public static final DNDArmor SPLINT_BOOTS = new DNDArmor(ArmorSet.SPLINT, ArmorSet.ArmorPiece.BOOTS, Material.NETHERITE_BOOTS);
    public static final DNDArmor SPLINT_LEGGINGS = new DNDArmor(ArmorSet.SPLINT, ArmorSet.ArmorPiece.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
    public static final DNDArmor SPLINT_CHESTPLATE = new DNDArmor(ArmorSet.SPLINT, ArmorSet.ArmorPiece.CHESTPLATE, Material.NETHERITE_CHESTPLATE);
    public static final DNDArmor SPLINT_HELMET = new DNDArmor(ArmorSet.SPLINT, ArmorSet.ArmorPiece.HELMET, Material.NETHERITE_HELMET);
    public static final DNDArmor PLATE_BOOTS = new DNDArmor(ArmorSet.PLATE, ArmorSet.ArmorPiece.BOOTS, Material.NETHERITE_BOOTS);
    public static final DNDArmor PLATE_LEGGINGS = new DNDArmor(ArmorSet.PLATE, ArmorSet.ArmorPiece.LEGGINGS, Material.NETHERITE_LEGGINGS);
    public static final DNDArmor PLATE_CHESTPLATE = new DNDArmor(ArmorSet.PLATE, ArmorSet.ArmorPiece.CHESTPLATE, Material.NETHERITE_CHESTPLATE);
    public static final DNDArmor PLATE_HELMET = new DNDArmor(ArmorSet.PLATE, ArmorSet.ArmorPiece.HELMET, Material.NETHERITE_HELMET);

    // Misc
    public static final DNDItem SHIELD = new DNDItem("SHIELD", "Shield", Material.SHIELD, 1, null, MoneyAmount.fromGold(10), 6);

    public static DNDArmor getArmor(ArmorSet set, ArmorSet.ArmorPiece piece) {
        for (DNDItem item : values())
            if (item instanceof DNDArmor && ((DNDArmor) item).getSet() == set && ((DNDArmor) item).getPiece() == piece)
                return (DNDArmor) item;

        throw new IllegalArgumentException("The armorset " + set.getName() + " does not have the piece " + Util.cleanEnumName(piece.name()));
    }

    public static Set<DNDItem> values() {
        return Sets.newHashSet(SHIELD, PLATE_HELMET, PLATE_CHESTPLATE, PLATE_LEGGINGS, PLATE_BOOTS, SPLINT_HELMET, SPLINT_CHESTPLATE, SPLINT_LEGGINGS,
                SPLINT_BOOTS, CHAIN_MAIL_HELMET, CHAIN_MAIL_CHESTPLATE, CHAIN_MAIL_LEGGINGS, CHAIN_MAIL_BOOTS, RING_MAIL_CHESTPLATE, RING_MAIL_LEGGINGS, RING_MAIL_BOOTS,
                HALF_PLATE_BOOTS, HALF_PLATE_LEGGINGS, HALF_PLATE_CHESTPLATE, HALF_PLATE_HELMET, BREASTPLATE, SCALE_MAIL_LEGGINGS, SCALE_MAIL_CHESTPLATE, CHAIN_SHIRT,
                HIDE_LEGGINGS, HIDE_CHESTPLATE, STUDDED_LEATHER_CHESTPLATE, STUDDED_LEATHER_LEGGINGS, STUDDED_LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS,
                PADDED_CHESTPLATE, PADDED_LEGGINGS);
    }
}
