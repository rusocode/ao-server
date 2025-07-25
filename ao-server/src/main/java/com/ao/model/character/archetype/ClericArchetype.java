package com.ao.model.character.archetype;

/**
 * A cleric archetype.
 */

public class ClericArchetype extends DefaultArchetype {

    private static final float MANA_MODIFIER = 2.0f;
    private static final int INITIAL_MANA = 50;

    public ClericArchetype(float evasionModifier, float meleeAccuracyModifier,
                           float rangedAccuracyModifier, float meleeDamageModifier,
                           float rangedDamageModifier, float wrestlingDamageModifier,
                           float blockPowerModifier) {
        super(evasionModifier, meleeAccuracyModifier, rangedAccuracyModifier,
                meleeDamageModifier, rangedDamageModifier, wrestlingDamageModifier,
                blockPowerModifier);
    }

    @Override
    public int getManaIncrement(int intelligence, int mana) {
        return Math.round(intelligence * MANA_MODIFIER);
    }

    @Override
    public int getInitialMana(int intelligence) {
        return INITIAL_MANA;
    }

}
