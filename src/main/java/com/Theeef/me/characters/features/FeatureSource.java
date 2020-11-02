package com.Theeef.me.characters.features;

import java.util.Set;

public interface FeatureSource {

    /**
     * Get the features granted by this source
     *
     * @return set of features
     */
    Set<? extends Feature> getSourceFeatures();

}
