package com.Theeef.me.characters.features;

import com.Theeef.me.api.equipment.DamageType;
import com.Theeef.me.characters.abilities.Ability;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.UUID;

public class FeatureDraconicAncestry extends ChoiceFeature {

    public FeatureDraconicAncestry() {
        super("Draconic Ancestry", "You have draconic ancestry. Choose one type of dragon from the Draconic Ancestry table. Your breath weapon and damage resistance are determined by the dragon type.");
    }

    @Override
    public Set<? extends FeatureChoice> getChoices() {
        return FeatureChoiceDraconicAncestry.values();
    }

    public static FeatureChoiceDraconicAncestry getChoice(UUID characterOwner, int characterID) {
        return null;
    }

    public static class FeatureChoiceDraconicAncestry extends FeatureChoice {
        public static final FeatureChoiceDraconicAncestry BLACK = new FeatureChoiceDraconicAncestry("Black", DamageType.ACID, "5 by 30 ft. line (Dex. save)", Ability.DEX);
        public static final FeatureChoiceDraconicAncestry BLUE = new FeatureChoiceDraconicAncestry("Blue", DamageType.LIGHTNING, "5 by 30 ft. line (Dex. save)", Ability.DEX);
        public static final FeatureChoiceDraconicAncestry BRASS = new FeatureChoiceDraconicAncestry("Brass", DamageType.FIRE, "5 by 30 ft. line (Dex. save)", Ability.DEX);
        public static final FeatureChoiceDraconicAncestry BRONZE = new FeatureChoiceDraconicAncestry("Bronze", DamageType.LIGHTNING, "5 by 30 ft. line (Dex. save)", Ability.DEX);
        public static final FeatureChoiceDraconicAncestry COPPER = new FeatureChoiceDraconicAncestry("Copper", DamageType.ACID, "5 by 30 ft. line (Dex. save)", Ability.DEX);
        public static final FeatureChoiceDraconicAncestry GOLD = new FeatureChoiceDraconicAncestry("Gold", DamageType.FIRE, "15 ft. cone (Dex. save)", Ability.DEX);
        public static final FeatureChoiceDraconicAncestry GREEN = new FeatureChoiceDraconicAncestry("Green", DamageType.POISON, "15 ft. cone (Con. save)", Ability.CON);
        public static final FeatureChoiceDraconicAncestry RED = new FeatureChoiceDraconicAncestry("Red", DamageType.FIRE, "15 ft. cone (Dex. save)", Ability.DEX);
        public static final FeatureChoiceDraconicAncestry SILVER = new FeatureChoiceDraconicAncestry("Silver", DamageType.COLD, "15 ft. cone (Con. save)", Ability.CON);
        public static final FeatureChoiceDraconicAncestry WHITE = new FeatureChoiceDraconicAncestry("White", DamageType.COLD, "15 ft. cone (Con. save)", Ability.CON);

        private String dragon;
        private DamageType damageType;
        private String breathWeapon;
        private Ability savingThrow;

        public FeatureChoiceDraconicAncestry(String dragon, DamageType damageType, String breathWeapon, Ability savingThrow) {
            this.dragon = dragon;
            this.damageType = damageType;
            this.breathWeapon = breathWeapon;
            this.savingThrow = savingThrow;
        }

        public static Set<FeatureChoiceDraconicAncestry> values() {
            return Sets.newHashSet(BLACK, BLUE, BRASS, BRONZE, COPPER, GOLD, GREEN, RED, SILVER, WHITE);
        }

        @Override
        public String getName() {
            return dragon;
        }

        @Override
        public String getDescription() {
            return "Deals " + damageType.getName().toLowerCase() + " damage in a " + breathWeapon;
        }

        public DamageType getDamageType() {
            return damageType;
        }
    }
}
