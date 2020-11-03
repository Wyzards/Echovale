package com.Theeef.me.characters.abilities;

import java.util.Set;

public interface ProficiencySource {

    Set<Proficiency> sourceProficiencies();

    Set<Proficiency> proficiencyOptions();

    int proficiencyChoiceAmount();

}
