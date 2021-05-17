package com.Theeef.me.characters.races;

import com.Theeef.me.combat.damage.DamageType;

import java.util.Set;

public interface ResistanceSource {

    Set<DamageType> sourceResistances();

}
