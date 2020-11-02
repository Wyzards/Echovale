package com.Theeef.me.characters.abilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Abilities {

    public HashMap<Ability, Integer> baseAbilities;
    public Set<AbilityAlteration> alterations;

    public Abilities(HashMap<Ability, Integer> baseAbilities, Set<AbilityAlteration> alterations) {
        this.baseAbilities = baseAbilities;
        this.alterations = alterations;
    }

    public boolean addAlterations(Collection<AbilityAlteration> newAlterations) {
        return alterations.addAll(newAlterations);
    }
}
