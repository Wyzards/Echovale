package com.Theeef.me.items.weapons;

import com.Theeef.me.combat.damage.DamageType;
import com.Theeef.me.items.*;
import com.Theeef.me.items.weapons.DNDRangedVersatileWeapon;
import com.Theeef.me.items.weapons.DNDRangedWeapon;
import com.Theeef.me.items.weapons.DNDVersatileWeapon;
import com.Theeef.me.items.weapons.DNDWeapon;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.Set;

public class Weapons {

    // Simple Weapons
    public static DNDWeapon CLUB = new DNDWeapon("CLUB", "Club", Material.WOODEN_SHOVEL, 1, null, MoneyAmount.fromSilver(1), 2, 1, 4, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.LIGHT), DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDRangedWeapon DAGGER = new DNDRangedWeapon("DAGGER", "Dagger", Material.SHEARS, 1, null, MoneyAmount.fromGold(2), 1, 1, 4, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.FINESSE, DNDWeapon.ItemProperty.LIGHT, DNDWeapon.ItemProperty.THROWN), 20, 60, DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDWeapon GREATCLUB = new DNDWeapon("GREATCLUB", "Greatclub", Material.DIAMOND_SHOVEL, 1, null, MoneyAmount.fromSilver(2), 10, 1, 8, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.TWO_HANDED), DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDRangedWeapon HANDAXE = new DNDRangedWeapon("HANDAXE", "Handaxe", Material.IRON_AXE, 1, null, MoneyAmount.fromGold(5), 2, 1, 6, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.LIGHT, DNDWeapon.ItemProperty.THROWN), 20, 60, DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDRangedWeapon JAVELIN = new DNDRangedWeapon("JAVELIN", "Javelin", Material.ARROW, 1, null, MoneyAmount.fromSilver(5), 2, 1, 6, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.THROWN), 30, 120, DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDRangedWeapon LIGHT_HAMMER = new DNDRangedWeapon("LIGHT_HAMMER", "Light Hammer", Material.IRON_AXE, 1, null, MoneyAmount.fromGold(2), 2, 1, 4, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.LIGHT, DNDWeapon.ItemProperty.THROWN), 20, 60, DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDWeapon MACE = new DNDWeapon("MACE", "Mace", Material.IRON_SHOVEL, 1, null, MoneyAmount.fromGold(5), 4, 1, 6, DamageType.BLUDGEONING, Lists.newArrayList(), DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDVersatileWeapon QUARTERSTAFF = new DNDVersatileWeapon("QUARTERSTAFF", "Quarterstaff", Material.STICK, 1, null, MoneyAmount.fromSilver(2), 4, 1, 6, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.VERSATILE), 1, 8, DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDWeapon SICKLE = new DNDWeapon("SICKLE", "Sickle", Material.IRON_HOE, 1, null, MoneyAmount.fromGold(1), 2, 1, 4, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.LIGHT), DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDRangedVersatileWeapon SPEAR = new DNDRangedVersatileWeapon("SPEAR", "Spear", Material.IRON_SHOVEL, 1, null, MoneyAmount.fromGold(1), 3, 1, 6, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.THROWN, DNDWeapon.ItemProperty.VERSATILE), 20, 60, 1, 8, DNDWeapon.WeaponType.SIMPLE_MELEE);
    public static DNDRangedWeapon LIGHT_CROSSBOW = new DNDRangedWeapon("LIGHT_CROSSBOW", "Light Crossbow", Material.CROSSBOW, 1, null, MoneyAmount.fromGold(25), 5, 1, 8, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.AMMUNITION, DNDWeapon.ItemProperty.LOADING, DNDWeapon.ItemProperty.TWO_HANDED), 80, 320, DNDWeapon.WeaponType.SIMPLE_RANGED);
    public static DNDRangedWeapon DART = new DNDRangedWeapon("DART", "Dart", Material.FLINT, 1, null, MoneyAmount.fromCopper(5), 0.25, 1, 4, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.FINESSE, DNDWeapon.ItemProperty.THROWN), 20, 60, DNDWeapon.WeaponType.SIMPLE_RANGED);
    public static DNDRangedWeapon SHORTBOW = new DNDRangedWeapon("SHORTBOW", "Shortbow", Material.BOW, 1, null, MoneyAmount.fromGold(25), 2, 1, 6, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.AMMUNITION, DNDWeapon.ItemProperty.TWO_HANDED), 80, 320, DNDWeapon.WeaponType.SIMPLE_RANGED);
    public static DNDRangedWeapon SLING = new DNDRangedWeapon("SLING", "Sling", Material.STRING, 1, null, MoneyAmount.fromSilver(1), 0, 1, 4, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.AMMUNITION), 30, 120, DNDWeapon.WeaponType.SIMPLE_RANGED);

    // Martial Weapons
    public static DNDVersatileWeapon BATTLEAXE = new DNDVersatileWeapon("BATTLEAXE", "Battleaxe", Material.IRON_AXE, 1, null, MoneyAmount.fromGold(10), 4, 1, 8, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.VERSATILE), 1, 10, DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon FLAIL = new DNDWeapon("FLAIL", "Flail", Material.FISHING_ROD, 1, null, MoneyAmount.fromGold(10), 2, 1, 8, DamageType.BLUDGEONING, Lists.newArrayList(), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon GLAIVE = new DNDWeapon("GLAIVE", "Glaive", Material.IRON_SHOVEL, 1, null, MoneyAmount.fromGold(20), 6, 1, 10, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.REACH, DNDWeapon.ItemProperty.TWO_HANDED), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon GREATAXE = new DNDWeapon("GREATAXE", "Greataxe", Material.IRON_AXE, 1, null, MoneyAmount.fromGold(30), 7, 1, 12, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.TWO_HANDED), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon GREATSWORD = new DNDWeapon("GREATSWORD", "Greatsword", Material.IRON_SWORD, 1, null, MoneyAmount.fromGold(50), 6, 2, 12, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.TWO_HANDED), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon HALBERD = new DNDWeapon("HALBERD", "Halberd", Material.IRON_AXE, 1, null, MoneyAmount.fromGold(20), 6, 1, 10, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.REACH, DNDWeapon.ItemProperty.TWO_HANDED), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon LANCE = new DNDWeapon("LANCE", "Lance", Material.IRON_SWORD, 1, null, MoneyAmount.fromGold(10), 6, 1, 12, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.REACH, DNDWeapon.ItemProperty.SPECIAL), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDVersatileWeapon LONGSWORD = new DNDVersatileWeapon("LONGSWORD", "Longsword", Material.IRON_SWORD, 1, null, MoneyAmount.fromGold(15), 3, 1, 8, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.VERSATILE), 1, 10, DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon MAUL = new DNDWeapon("MAUL", "Maul", Material.IRON_AXE, 1, null, MoneyAmount.fromGold(10), 10, 2, 12, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.TWO_HANDED), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon MORNINGSTAR = new DNDWeapon("MORNINGSTAR", "Morningstar", Material.IRON_SHOVEL, 1, null, MoneyAmount.fromGold(15), 4, 1, 8, DamageType.PIERCING, Lists.newArrayList(), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon PIKE = new DNDWeapon("PIKE", "Pike", Material.IRON_SHOVEL, 1, null, MoneyAmount.fromGold(5), 18, 1, 10, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.REACH, DNDWeapon.ItemProperty.TWO_HANDED), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon RAPIER = new DNDWeapon("RAPIER", "Rapier", Material.IRON_SWORD, 1, null, MoneyAmount.fromGold(25), 2, 1, 8, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.FINESSE), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon SCIMITAR = new DNDWeapon("SCIMITAR", "Scimitar", Material.IRON_SWORD, 1, null, MoneyAmount.fromGold(25), 3, 1, 6, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.FINESSE, DNDWeapon.ItemProperty.LIGHT), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon SHORTSWORD = new DNDWeapon("SHORTSWORD", "Shortsword", Material.IRON_SWORD, 1, null, MoneyAmount.fromGold(10), 2, 1, 6, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.FINESSE, DNDWeapon.ItemProperty.LIGHT), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDRangedVersatileWeapon TRIDENT = new DNDRangedVersatileWeapon("TRIDENT", "Trident", Material.TRIDENT, 1, null, MoneyAmount.fromGold(5), 4, 1, 6, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.THROWN, DNDWeapon.ItemProperty.VERSATILE), 20, 60, 1, 8, DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon WAR_PICK = new DNDWeapon("WAR_PICK", "War Pick", Material.IRON_PICKAXE, 1, null, MoneyAmount.fromGold(5), 2, 1, 8, DamageType.PIERCING, Lists.newArrayList(), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDVersatileWeapon WARHAMMER = new DNDVersatileWeapon("WARHAMMER", "Warhammer", Material.IRON_AXE, 1, null, MoneyAmount.fromGold(15), 2, 1, 8, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.VERSATILE), 1, 10, DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDWeapon WHIP = new DNDWeapon("WHIP", "Whip", Material.STRING, 1, null, MoneyAmount.fromGold(2), 3, 1, 4, DamageType.SLASHING, Lists.newArrayList(DNDWeapon.ItemProperty.FINESSE, DNDWeapon.ItemProperty.REACH), DNDWeapon.WeaponType.MARTIAL_MELEE);
    public static DNDRangedWeapon BLOWGUN = new DNDRangedWeapon("BLOWGUN", "Blowgun", Material.BAMBOO, 1, null, MoneyAmount.fromGold(10), 1, 1, 1, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.AMMUNITION, DNDWeapon.ItemProperty.LOADING), 25, 100, DNDWeapon.WeaponType.MARTIAL_RANGED);
    public static DNDRangedWeapon HAND_CROSSBOW = new DNDRangedWeapon("HAND_CROSSBOW", "Hand Crossbow", Material.CROSSBOW, 1, null, MoneyAmount.fromGold(75), 3, 1, 6, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.AMMUNITION, DNDWeapon.ItemProperty.LIGHT, DNDWeapon.ItemProperty.LOADING), 30, 120, DNDWeapon.WeaponType.MARTIAL_RANGED);
    public static DNDRangedWeapon HEAVY_CROSSBOW = new DNDRangedWeapon("HEAVY_CROSSBOW", "Heavy Crossbow", Material.CROSSBOW, 1, null, MoneyAmount.fromGold(50), 18, 1, 10, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.AMMUNITION, DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.LOADING, DNDWeapon.ItemProperty.TWO_HANDED), 30, 120, DNDWeapon.WeaponType.MARTIAL_RANGED);
    public static DNDRangedWeapon LONGBOW = new DNDRangedWeapon("LONGBOW", "Longbow", Material.BOW, 1, null, MoneyAmount.fromGold(10), 2, 1, 8, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.AMMUNITION, DNDWeapon.ItemProperty.HEAVY, DNDWeapon.ItemProperty.TWO_HANDED), 150, 600, DNDWeapon.WeaponType.MARTIAL_RANGED);
    public static DNDRangedWeapon NET = new DNDRangedWeapon("NET", "Net", Material.COBWEB, 1, null, MoneyAmount.fromGold(1), 3, 0, 0, null, Lists.newArrayList(DNDWeapon.ItemProperty.SPECIAL, DNDWeapon.ItemProperty.THROWN), 5, 15, DNDWeapon.WeaponType.MARTIAL_RANGED);

    public static Set<DNDWeapon> values() {
        return Sets.newHashSet(CLUB, DAGGER, GREATCLUB, HANDAXE, JAVELIN, LIGHT_HAMMER, MACE, QUARTERSTAFF, SICKLE, SPEAR, LIGHT_CROSSBOW, DART, SHORTBOW, SLING, BATTLEAXE, FLAIL, GLAIVE,
                GREATAXE, GREATSWORD, GREATSWORD, HALBERD, LANCE, LONGSWORD, MAUL, MORNINGSTAR, PIKE, RAPIER, SCIMITAR, SHORTSWORD, TRIDENT, WAR_PICK, WARHAMMER, WHIP,
                BLOWGUN, HAND_CROSSBOW, HEAVY_CROSSBOW, LONGBOW, NET);
    }
}
