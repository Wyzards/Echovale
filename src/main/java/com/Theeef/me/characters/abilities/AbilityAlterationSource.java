package com.Theeef.me.characters.abilities;

import java.util.Set;

public interface AbilityAlterationSource {

    /**
     * Get a set of AbilityAlterations the source provides
     *
     * @return the set of ability alterations or null
     */
    Set<AbilityAlteration> getAlterations();

    /**
     * The set of abilities you can select to alter because of this source
     *
     * @return the set of abilities to pick from or null
     */
    Set<Ability> abilityOptions();

    /**
     * The maximum amount you can alter an ability you select from the ability options
     *
     * @return the maximum amount
     */
    int maxAbilityAlteration();

    /**
     * Get how many abilities you must select from the options list
     *
     * @return the amount to select
     */
    int abilityChoiceAmount();
}
