package com.ao.model.character.archetype;

/**
 * Generic worker archetype for version 0.13.x and later.
 */

public class WorkerArchetype extends DefaultArchetype {

    /**
     * Creates a new default archetype.
     */
    public WorkerArchetype(float evasionModifier, float meleeAccuracyModifier, float rangedAccuracyModifier, float meleeDamageModifier,
                           float rangedDamageModifier, float wrestlingDamageModifier, float blockPowerModifier) {
        super(evasionModifier, meleeAccuracyModifier, rangedAccuracyModifier, meleeDamageModifier, rangedDamageModifier, wrestlingDamageModifier, blockPowerModifier);
    }

    // TODO Complete this with the config values for worker which are not default!

}
