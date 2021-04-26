package com.Theeef.me.items.equipment;


import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.items.containers.ContainerType;
import com.Theeef.me.items.containers.DNDContainerItem;
import com.google.common.collect.Sets;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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
    public static DNDItem ABACUS = new DNDItem("ABACUS", Material.ARMOR_STAND, "A standard tool used to make calculations.", 2, 2, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem VIAL_OF_ACID = new DNDItem("VIAL_OF_ACID", "Vial of Acid", potionItem(Color.LIME), 1, "As an action, you can splash the contents of this vial onto a creature within 5 feet of you or throw the vial up to 20 feet, shattering it on impact. In either case, make a ranged attack against a creature or object, treating the acid as an improvised weapon. On a hit, the target takes 2d6 acid damage.", MoneyAmount.fromGold(25), 1, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem ALCHEMISTS_FIRE = new DNDItem("ALCHEMISTS_FIRE", "Flask of Alchemist's Fire", potionItem(Color.BLACK), 1, "This sticky, adhesive fluid ignites when exposed to air. As an action, you can throw this flask up to 20 feet, shattering it on impact. Make a ranged attack against a creature or object, treating the alchemist's fire as an improvised weapon. On a hit, the target takes 1d4 fire damage at the start of each of its turns. A creature can end this damage by using its action to make a DC 10 Dexterity check to extinguish the flames.", MoneyAmount.fromGold(50), 1, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem ANTITOXIN = new DNDItem("ANTITOXIN", "Vial of Antitoxin", potionItem(Color.FUCHSIA), 1, "A creature that drinks this vial of liquid gains advantage on saving throws against poison for 1 hour. It confers no benefit to undead or constructs.", MoneyAmount.fromGold(50), 0, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem ALMS_BOX = new DNDItem("ALMS_BOX", Material.CHEST, "A small box for alms, typically found in a priest's pack.", 0, 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDContainerItem BACKPACK = new DNDContainerItem(ContainerType.BACKPACK, null, "A backpack is a leather pack carried on the back, typically with straps to secure it. A backpack can hold 1 cubic foot/ 30 pounds of gear.");
    public static DNDItem BALL_BEARINGS = new DNDItem("BALL_BEARINGS", "Bag of Ball bearings", Material.FLOWER_POT, 1, "As an action, you can spill these tiny metal balls from their pouch to cover a level, square area that is 10 feet on a side. A creature moving across the covered area must succeed on a DC 10 Dexterity saving throw or fall prone. A creature moving through the area at half speed doesn't need to make the save.", MoneyAmount.fromGold(1), 2, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem BARREL = new DNDItem("BARREL", Material.BARREL, "A barrel can hold 40 gallons of liquid, or 4 cubic feet of solid material.", 2, 70, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDContainerItem BASKET = new DNDContainerItem(ContainerType.BASKET, null, "A basket can hold 2 cubic feet/40 pounds of gear.");
    public static DNDItem BEDROLL = new DNDItem("BEDROLL", Material.BROWN_BED, null, 1, 7, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BELL = new DNDItem("BELL", Material.BELL, "A standard bell that rings, typically used for signaling.", 1, 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BLANKET = new DNDItem("BLANKET", "Blanket", Material.WHITE_CARPET, 1, "A thick, quilted, blanket made to keep you warm in cold weather.", MoneyAmount.fromSilver(5), 3, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem BOOK = new DNDItem("BOOK", Material.BOOK, "A book might contain poetry, historical accounts, information pertaining to a particular field of lore, diagrams and notes on gnomish contraptions, or just about anything else that can be represented using text or pictures. A book of spells is a spellbook.", 25, 5, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem CASE_MAP_SCROLL = new DNDItem("CASE_MAP_SCROLL", "Map/Scroll Case", Material.HOPPER, 1, "This cylindrical leather case can hold up to ten rolled-up sheets of paper or five rolled-up sheets of parchment.", MoneyAmount.fromGold(1), 1, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem CANDLE = new DNDItem("CANDLE", Material.TORCH, MoneyAmount.fromCopper(1), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem CENSER = new DNDItem("CENSER", Material.BREWING_STAND, MoneyAmount.fromCopper(1), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem CROWBAR = new DNDItem("CROWBAR", Material.IRON_SWORD, 2, 5, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem HAMMER = new DNDItem("HAMMER", Material.IRON_AXE, 1, 3, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem HOODED_LANTERN = new DNDItem("HOODED_LANTERN", Material.LANTERN, 5, 2, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem INCENSE_BLOCK = new DNDItem("INCENSE_BLOCK", "Block of Incense", Material.COARSE_DIRT, 1, null, MoneyAmount.fromCopper(1), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem INK_BOTTLE = new DNDItem("INK_BOTTLE", "Bottle of Ink", potionItem(Color.BLACK), 1, null, MoneyAmount.fromGold(10), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem INK_PEN = new DNDItem("INK_PEN", Material.FEATHER, MoneyAmount.fromCopper(2), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem LAMP = new DNDItem("LAMP", Material.LANTERN, MoneyAmount.fromSilver(5), 1, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem LITTLE_BIG_OF_SAND = new DNDItem("LITTLE_BAG_OF_SAND", "Little Bag of Sand", Material.FLOWER_POT, 0, 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem MESS_KIT = new DNDItem("MESS_KIT", Material.IRON_TRAPDOOR, MoneyAmount.fromSilver(2), 1, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem OIL_FLASK = new DNDItem("OIL_FLASK", "Flask of Oil", potionItem(Color.BLACK), 1, null, MoneyAmount.fromSilver(1), 1, DNDItem.ItemType.ADVENTURING_GEAR, DNDItem.ItemType.CONSUMABLE);
    public static DNDItem PAPER = new DNDItem("PAPER", Material.PAPER, MoneyAmount.fromSilver(2), 2, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem PARCHMENT = new DNDItem("PARCHMENT", Material.MAP, MoneyAmount.fromSilver(1), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem PERFUME_VIAL = new DNDItem("PERFUME_VIAL", "Vial of Perfume", potionItem(Color.PURPLE), 1, null, MoneyAmount.fromGold(5), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem PITON = new DNDItem("PITON", Material.TRIPWIRE_HOOK, MoneyAmount.fromCopper(5), 0.25, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem RATIONS = new DNDItem("RATIONS", Material.ROTTEN_FLESH, MoneyAmount.fromSilver(5), 2, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem ROPE = new DNDItem("ROPE", Material.LEAD, 1, 10, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem SOAP = new DNDItem("SOAP", Material.QUARTZ, MoneyAmount.fromCopper(2), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem STRING = new DNDItem("STRING", Material.STRING, 0, 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem SEALING_WAX = new DNDItem("SEALING_WAX", Material.HONEY_BLOCK, MoneyAmount.fromSilver(5), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem SMALL_KNIFE = new DNDItem("SMALL_KNIFE", Material.STONE_SWORD, MoneyAmount.fromCopper(1), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem TINDERBOX = new DNDItem("TINDERBOX", Material.LOOM, MoneyAmount.fromSilver(5), 1, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem TORCH = new DNDItem("TORCH", Material.TORCH, MoneyAmount.fromCopper(1), 1, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem WATERSKIN = new DNDItem("WATERSKIN", "Waterskin", potionItem(Color.BLUE), 1, null, MoneyAmount.fromSilver(2), 5, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem VESTMENTS = new DNDItem("VESTMENTS", whiteChestplate(), MoneyAmount.fromCopper(1), 0, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem FINE_HAT = new DNDItem("FINE_HAT", Material.LEATHER_HELMET, MoneyAmount.fromGold(15 * .2), 6 * .2, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem FINE_SHIRT = new DNDItem("FINE_SHIRT", Material.LEATHER_CHESTPLATE, MoneyAmount.fromGold(15 * .4), 6 * .4, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem FINE_PANTS = new DNDItem("FINE_PANTS", Material.LEATHER_LEGGINGS, MoneyAmount.fromGold(15 * .3), 6 * .3, DNDItem.ItemType.ADVENTURING_GEAR);
    public static DNDItem FINE_SHOES = new DNDItem("FINE_SHOES", Material.LEATHER_BOOTS, MoneyAmount.fromGold(15 * .1), 6 * .1, DNDItem.ItemType.ADVENTURING_GEAR);

    public static ItemStack whiteChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.WHITE);
        item.setItemMeta(meta);

        return item;
    }

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
