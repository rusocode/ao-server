package com.ao.model.character.archetype;

/**
 * A lumberjack archetype.
 */

public class LumberjackArchetype extends DefaultArchetype {

    private static final int STAMINA_INCREMENT = 35;
    private static final int LUMBER_MIN_AMOUNT = 1;
    private static final int LUMBER_MAX_AMOUNT = 4;
    private static final int LUMBER_STAMINA_COST = 2;

    public LumberjackArchetype(float evasionModifier, float meleeAccuracyModifier,
                               float rangedAccuracyModifier, float meleeDamageModifier,
                               float rangedDamageModifier, float wrestlingDamageModifier,
                               float blockPowerModifier) {
        super(evasionModifier, meleeAccuracyModifier, rangedAccuracyModifier,
                meleeDamageModifier, rangedDamageModifier, wrestlingDamageModifier,
                blockPowerModifier);
    }

    @Override
    public int getStaminaIncrement() {
        return STAMINA_INCREMENT;
    }

    @Override
    public int getLumberedMaxAmount() {
        return LUMBER_MAX_AMOUNT;
    }

    @Override
    public int getLumberedMinAmount() {
        return LUMBER_MIN_AMOUNT;
    }

    @Override
    public int getLumberjackingStaminaCost() {
        return LUMBER_STAMINA_COST;
    }

}
