package com.ao.model.character.archetype;

/**
 * Generic worker archetype for version 0.13.x and later.
 * 
 * @author imtirabasso
 */
public class WorkerArchetype extends DefaultArchetype {

	/**
	 * Creates a new default archetype.
	 * @param evasionModifier
	 * @param meleeAccuracyModifier
	 * @param rangedAccuracyModifier
	 * @param meleeDamageModifier
	 * @param rangedDamageModifier
	 * @param wrestlingDamageModifier
	 * @param blockPowerModifier
	 */
	public WorkerArchetype(float evasionModifier, float meleeAccuracyModifier,
			float rangedAccuracyModifier, float meleeDamageModifier,
			float rangedDamageModifier, float wrestlingDamageModifier,
			float blockPowerModifier) {
		super(evasionModifier, meleeAccuracyModifier, rangedAccuracyModifier,
				meleeDamageModifier, rangedDamageModifier, wrestlingDamageModifier,
				blockPowerModifier);
	}

	 // TODO : Complete this with the config values for worker which are not default!
}
