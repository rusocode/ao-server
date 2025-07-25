package com.ao.model.character.archetype;

/**
 * A pirate archetype.
 */

public class PirateArchetype extends DefaultArchetype {

    private static final int HIT_INCREMENT = 3;
    private static final int SAILING_MIN_LEVEL = 20;
    private static final float SAILING_MODIFIER = 1.0f;

    public PirateArchetype(float evasionModifier, float meleeAccuracyModifier,
                           float rangedAccuracyModifier, float meleeDamageModifier,
                           float rangedDamageModifier, float wrestlingDamageModifier,
                           float blockPowerModifier) {
        super(evasionModifier, meleeAccuracyModifier, rangedAccuracyModifier,
                meleeDamageModifier, rangedDamageModifier, wrestlingDamageModifier,
                blockPowerModifier);
    }

    @Override
    public int getHitIncrement(int level) {
        return HIT_INCREMENT;
    }

    @Override
    public int getSailingMinLevel() {
        return SAILING_MIN_LEVEL;
    }

    @Override
    protected float getSailingModifier() {
        return SAILING_MODIFIER;
    }

}
