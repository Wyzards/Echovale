package com.Theeef.me.items.equipment;

import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.MoneyAmount;
import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.Set;

public class AdventuringGear {

    // Ammunition
    public static DNDItem ARROW = new DNDItem("ARROW", "Arrow", Material.ARROW, 20, null, MoneyAmount.fromGold(1), 1, DNDItem.ItemType.AMMUNITION, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BLOWGUN_NEEDLE = new DNDItem("BLOWGUN_NEEDLE", "Blowgun Needle", Material.END_ROD, 50, null, MoneyAmount.fromGold(1), 1, DNDItem.ItemType.AMMUNITION, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem CROSSBOW_BOLT = new DNDItem("CROSSBOW_BOLT", "Crossbow Bolt", Material.ARROW, 20, null, MoneyAmount.fromGold(1), 1.5, DNDItem.ItemType.AMMUNITION, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem SLING_BULLET = new DNDItem("SLING_BULLET", "Sling Bullet", Material.STONE_BUTTON, 20, null, MoneyAmount.fromCopper(4), 1.5, DNDItem.ItemType.AMMUNITION, DNDItem.ItemType.ADVENTURING_GEAR);

    // Arcane Foci
    public static DNDItem CRYSTAL = new DNDItem("CRYSTAL", "Crystal", Material.QUARTZ, 1, null, MoneyAmount.fromGold(10), 1, DNDItem.ItemType.ARCANE_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem ORB = new DNDItem("ORB", "Orb", Material.HEART_OF_THE_SEA, 1, null, MoneyAmount.fromGold(20), 3, DNDItem.ItemType.ARCANE_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem ROD = new DNDItem("ROD", "Rod", Material.END_ROD, 1, null, MoneyAmount.fromGold(10), 2, DNDItem.ItemType.ARCANE_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem STAFF = new DNDItem("STAFF", "Staff", Material.STICK, 1, null, MoneyAmount.fromGold(5), 4, DNDItem.ItemType.ARCANE_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem WAND = new DNDItem("WAND", "Wand", Material.BLAZE_ROD, 1, null, MoneyAmount.fromGold(10), 1, DNDItem.ItemType.ARCANE_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);

    // Druidic Focus
    public static DNDItem SPRIG_OF_MISTLETOE = new DNDItem("SPRIG_OF_MISTLETOE", "Sprig of mistletoe", Material.FERN, 1, null, MoneyAmount.fromGold(1), 0, DNDItem.ItemType.DRUIDIC_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem TOTEM = new DNDItem("TOTEM", "Totem", Material.TOTEM_OF_UNDYING, 1, null, MoneyAmount.fromGold(1), 0, DNDItem.ItemType.DRUIDIC_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem WOODEN_STAFF = new DNDItem("WOODEN_STAFF", "Wooden Staff", Material.STICK, 1, null, MoneyAmount.fromGold(5), 4, DNDItem.ItemType.DRUIDIC_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem YEW_WAND = new DNDItem("YEW_WAND", "Yew Wand", Material.STICK, 1, null, MoneyAmount.fromGold(10), 1, DNDItem.ItemType.DRUIDIC_FOCUS, DNDItem.ItemType.ADVENTURING_GEAR);

    // Holy Symbols
    public static DNDItem AMULET = new DNDItem("AMULET", "Amulet", Material.SUNFLOWER, 1, null, MoneyAmount.fromGold(5), 1, DNDItem.ItemType.HOLY_SYMBOL, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem EMBLEM = new DNDItem("EMBLEM", "Emblem", Material.OAK_BUTTON, 1, null, MoneyAmount.fromGold(5), 0, DNDItem.ItemType.HOLY_SYMBOL, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem RELIQUARY = new DNDItem("RELIQUARY", "Reliquary", Material.END_PORTAL_FRAME, 1, null, MoneyAmount.fromGold(5), 2, DNDItem.ItemType.HOLY_SYMBOL, DNDItem.ItemType.ADVENTURING_GEAR);

    public Set<DNDItem> values() {
        return Sets.newHashSet(ARROW, BLOWGUN_NEEDLE, CROSSBOW_BOLT, SLING_BULLET, CRYSTAL, ORB, ROD, STAFF, WAND, SPRIG_OF_MISTLETOE, TOTEM, WOODEN_STAFF, YEW_WAND, AMULET, EMBLEM, RELIQUARY;
    }
}
