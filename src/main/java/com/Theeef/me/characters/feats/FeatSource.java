package com.Theeef.me.characters.feats;

import java.util.Set;

public interface FeatSource {

    Set<Feat> featOptions();

    int featChoiceAmount();

}
