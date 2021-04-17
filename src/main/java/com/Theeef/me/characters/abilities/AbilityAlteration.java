package com.Theeef.me.characters.abilities;

public class AbilityAlteration {

    private AbilityAlterationSource source;
    private Ability ability;
    private int alteration;

    public AbilityAlteration(Ability ability, int alteration, AbilityAlterationSource source) {
        this.ability = ability;
        this.alteration = alteration;
        this.source = source;
    }
}
