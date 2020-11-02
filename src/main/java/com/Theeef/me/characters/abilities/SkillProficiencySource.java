package com.Theeef.me.characters.abilities;

import java.util.HashMap;
import java.util.Set;

public interface SkillProficiencySource {

    /**
     * Get the skills this source gives you proficiency in
     *
     * @return the set of skills or null
     */
    public Set<Skill> sourceSkills();

    /**
     * Get the set of skills you can select from for any optional skills.
     *
     * @return the set of skills you can choose from or null
     */
    public Set<Skill> sourceSkillOptions();

    /**
     * Get the amount of skills you can select from the source skill options set to be proficient in.
     *
     * @return the amount of skills to select
     */
    public int skillChoiceAmount();

}
