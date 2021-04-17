package com.Theeef.me.items;

import com.Theeef.me.combat.damage.DamageType;
import com.google.common.collect.Lists;
import org.bukkit.Material;

public class Weapons {

    public static DNDWeapon CLUB = new DNDWeapon("CLUB", "Club", Material.IRON_SHOVEL, null, MoneyAmount.fromSilver(1), 2, 1, 4, DamageType.BLUDGEONING, Lists.newArrayList(DNDWeapon.ItemProperty.LIGHT));
    public static DNDWeapon DAGGER = new DNDRangedWeapon("DAGGER", "Dagger", Material.SHEARS, null, MoneyAmount.fromGold(2), 1, 1, 4, DamageType.PIERCING, Lists.newArrayList(DNDWeapon.ItemProperty.FINESSE, DNDWeapon.ItemProperty.LIGHT, DNDWeapon.ItemProperty.THROWN), 60, 20);

}
