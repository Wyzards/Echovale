package com.Theeef.me.items.equipment;


import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.items.containers.DNDContainerItem;
import com.google.common.collect.Sets;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Set;


// Ammunition
public class AdventuringGear {
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

    // Other Adventuring Gear
    // Simple Constructor:
    // String ID, Material material, String description, int gold, double weight, ItemType... type
    public static DNDItem ABACUS = new DNDItem("ABACUS", Material.ARMOR_STAND, null, 2, 2, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem VIAL_OF_ACID = new DNDItem("VIAL_OF_ACID", "Vial of Acid", potionItem(Color.LIME), 1, null, MoneyAmount.fromGold(25), 1, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem ALCHEMISTS_FIRE = new DNDItem("ALCHEMISTS_FIRE", "Flask of Alchemist's Fire", potionItem(Color.BLACK), 1, null, MoneyAmount.fromGold(50), 1, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem ANTITOXIN = new DNDItem("ANTITOXIN", "Vial of Antitoxin", potionItem(Color.FUCHSIA), 1, null, MoneyAmount.fromGold(50), 0, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDContainerItem BACKPACK = new DNDContainerItem("BACKPACK", "Backpack", Material.TRAPPED_CHEST, 1, null, MoneyAmount.fromGold(2), 5, 30, 1, null, null, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BALL_BEARINGS = new DNDItem("BALL_BEARINGS", "Bag of Ball bearings", Material.FLOWER_POT, 1, null, MoneyAmount.fromGold(1), 2, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem BARREL = new DNDItem("BARREL", Material.BARREL, null, 2, 70, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDContainerItem BASKET = new DNDContainerItem("BASKET", "Basket", Material.COMPOSTER, 1, null, MoneyAmount.fromSilver(4), 2, 40, 2, null, null, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BEDROLL = new DNDItem("BEDROLL", Material.BROWN_BED, null, 1, 7, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BELL = new DNDItem("BELL", Material.BELL, null, 1, 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BLANKET = new DNDItem("BLANKET", "Blanket", Material.WHITE_CARPET, 1, null, MoneyAmount.fromSilver(5), 3, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BLOCK_AND_TACKLE = new DNDItem("BLOCK_AND_TACKLE", "Block and Tackle", Material.TRIPWIRE_HOOK, 1, null, MoneyAmount.fromGold(1), 5, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BOOK = new DNDItem("BOOK", Material.BOOK, null, 25, 5);
    public static DNDItem GLASS_BOTTLE = new DNDItem("GLASS_BOTTLE", Material.GLASS_BOTTLE, null, 2, 2);
    public static DNDItem BUCKET = new DNDItem("BUCKET", "Bucket", Material.BUCKET, 1, null, MoneyAmount.fromCopper(5), 2, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BAG_OF_CALTROPS = new DNDItem("BAG_OF_CALTROPS", Material.FLOWER_POT, null, 1, 2);
    public static DNDItem CANDLE = new DNDItem("CANDLE", "Candle", Material.TORCH, 1, null, MoneyAmount.fromCopper(1), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem CROSSBOW_BOLT_CASE = new DNDItem("CROSSBOW_BOLT_CASE", Material.CHEST, null, 1, 1);
    public static DNDItem SCROLL_CASE = new DNDItem("SCROLL_CASE", Material.LEATHER, null, 1, 1);

    public static ItemStack potionItem(Color color) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setColor(color);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    public static Set<DNDItem> values() {
        return Sets.newHashSet(ARROW, BLOWGUN_NEEDLE, CROSSBOW_BOLT, SLING_BULLET, CRYSTAL, ORB, ROD, STAFF, WAND, SPRIG_OF_MISTLETOE, TOTEM, WOODEN_STAFF, YEW_WAND, AMULET, EMBLEM, RELIQUARY);
    }
}
